package servlets;

import dtos.ValidatorDto;
import interfaces.Validator;
import models.Person;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.messageresolver.IMessageResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ExitServlet extends HttpServlet {

    private ValidatorDto validatorDto = new ValidatorDto();
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
        if(httpSession.getAttribute("person") != null)
            httpSession.removeAttribute("person");
        WebContext webContext = new WebContext(request, response, getServletContext());
        templateEngine.process("exit", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Person person = validatorDto.getPersonByLogin(login);
        boolean hasWrong = false;
        String wrongMessage = "";
        if(person == null){
            hasWrong = true;
            wrongMessage = "Пользователь с таким логином не найден.";
        }else{
            if(!password.equals(person.getPassword())){
                hasWrong = true;
                wrongMessage = "Пароль не подходит";
            }else{
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("person", person);
            }
        }

        if(hasWrong){
            WebContext webContext = new WebContext(request, response, getServletContext());
            webContext.setVariable("message", wrongMessage);
            templateEngine.process("error", webContext, response.getWriter());
        }else{
            response.sendRedirect("/info");
        }
    }
}