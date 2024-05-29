package servlets;

import dtos.PersonDto;
import models.Person;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Queue;

public class DisplayServlet extends HttpServlet {
    private PersonDto personDto = new PersonDto();
    private TemplateEngine templateEngine = new TemplateEngine();

    @Override
    public void init() throws ServletException{
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        Queue<Person> people = personDto.getPeople();
        WebContext webContext = new WebContext(request,response,request.getServletContext());
        webContext.setVariable("people",people);
        templateEngine.process("display",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException{
        super.doPost(request,response);
    }
}
