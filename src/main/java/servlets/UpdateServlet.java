package servlets;

import dtos.PersonDto;
import dtos.ValidatorDto;
import models.Person;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateServlet extends HttpServlet {

    private TemplateEngine templateEngine = new TemplateEngine();

    @Override
    public void init() throws ServletException {
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if(httpSession.getAttribute("person") != null){
            Person person = (Person) httpSession.getAttribute("person");
            httpSession.setAttribute("person1", person);
            WebContext webContext = new WebContext(request, response, getServletContext());
            webContext.setVariable("person", person);
            templateEngine.process("update", webContext, response.getWriter());
        }else{
            WebContext webContext = new WebContext(request, response, getServletContext());
            templateEngine.process("access_error", webContext, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Person person1 = (Person) request.getSession().getAttribute("person1");
        request.getSession().removeAttribute("person1");
        Person person2 = new Person(
                request.getParameter("login"),
                request.getParameter("email"),
                request.getParameter("password")
        );
        String accessPassword = request.getParameter("accessPassword");
        if(person2.getPassword().equals(accessPassword)){
            ValidatorDto validatorDto = new ValidatorDto();
            if(((validatorDto.getPersonByLogin(person2.getLogin()) != null) && (!person1.getLogin().equals(person2.getLogin())))
                    || ((validatorDto.getPersonByEmail(person2.getEmail()) != null) && (!person1.getEmail().equals(person2.getEmail())))){
                String message = "Логин и почта уже зарегистрированы";
                WebContext webContext = new WebContext(request, response, getServletContext());
                webContext.setVariable("message", message);
                templateEngine.process("update_error", webContext, response.getWriter());
            }else{
                PersonDto personDto = new PersonDto();
                personDto.updatePerson(person1, person2);
                request.getSession().removeAttribute("person");
                request.getSession().setAttribute("person", person2);
                response.sendRedirect("/info");
            }
        }else{
            String message = "Пароли должны совпадать";
            WebContext webContext = new WebContext(request, response, getServletContext());
            webContext.setVariable("message", message);
            templateEngine.process("update_error", webContext, response.getWriter());
        }

    }
}