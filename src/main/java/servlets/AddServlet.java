package servlets;

import dtos.PersonDto;
import models.Person;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import validators.PersonValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddServlet extends HttpServlet {

    private PersonDto personDto = new PersonDto();
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
        WebContext webContext = new WebContext(request, response, getServletContext());
        templateEngine.process("add", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        PersonValidator personValidator = new PersonValidator();
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String accessPassword = request.getParameter("accessPassword");
        Person person = new Person(login, email, password);

        if (password.equals(accessPassword)) {
            if (!personValidator.IsValid(person)) {
                message = personValidator.MESSAGE;
            }
        } else {
            message = "Пароли должны совпадать!";
        }

        if (!message.equals("")) {
            WebContext webContext = new WebContext(request, response, getServletContext());
            webContext.setVariable("message", message);
            templateEngine.process("novalid", webContext, response.getWriter());
        } else {
            personDto.addPerson(person);
            HttpSession httpSession = request.getSession();
            if (httpSession.getAttribute("person") == null) {
                httpSession.setAttribute("person", person);
                response.sendRedirect("/info");
            } else {
                response.sendRedirect("/exit");
            }
        }
    }
}
