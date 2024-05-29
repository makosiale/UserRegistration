package servlets;

import dtos.PersonDto;
import models.Person;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private PersonDto personDto = new PersonDto();
    private TemplateEngine templateEngine = new TemplateEngine();

    @Override
    public void init()
        throws ServletException{
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        Person person = (Person) request.getSession().getAttribute("person");
        if (person!=null){
            personDto.deletePerson(person);
            response.sendRedirect("/exit");
        }else{
            WebContext webContext = new WebContext(request,response,getServletContext());
            templateEngine.process("access_error",webContext,response.getWriter());
        }
    }
}
