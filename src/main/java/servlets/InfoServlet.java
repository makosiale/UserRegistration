package servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import models.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class InfoServlet extends HttpServlet {
    private TemplateEngine templateEngine = new TemplateEngine();

    @Override
    public void init() throws ServletException{
        ServletContextTemplateResolver templateResolver  =
                new ServletContextTemplateResolver(getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        HttpSession httpSession = request.getSession();
        Person person = (Person) httpSession.getAttribute("person");
        WebContext webContext = new WebContext(request,response,getServletContext());
        if(person == null){
            templateEngine.process("access_error",webContext,response.getWriter());
        }else{
            webContext.setVariable("person",person);
            templateEngine.process("info",webContext,response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
        throws ServletException,IOException{
        super.doPost(request,response);
    }
}
