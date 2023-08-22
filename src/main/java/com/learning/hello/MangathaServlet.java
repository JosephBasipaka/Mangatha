package com.learning.hello;

import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import com.learning.hello.controller.Card;
import com.learning.hello.controller.MangathaController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/Mangatha")
public class MangathaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private JakartaServletWebApplication application;
	private TemplateEngine templateEngine;
    private MangathaController mangatha;
	
	 @Override
    public void init() throws ServletException {
		application = JakartaServletWebApplication.buildApplication(getServletContext());	
		final WebApplicationTemplateResolver templateResolver = 
		    new WebApplicationTemplateResolver(application);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		mangatha = new MangathaController();
    }
	 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
	        final IWebExchange webExchange = this.application.buildExchange(request, response);
	        final WebContext ctx = new WebContext(webExchange);
	        templateEngine.process("Mangatha", ctx, response.getWriter());
	 }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String playerName = request.getParameter("person");
    	String choosenCardText = request.getParameter("card");
    	String pos = request.getParameter("position");
    	boolean isIn = "Out".equals(pos);
    	System.out.println(isIn);
    	Card choosenCard = new Card(choosenCardText);
    	mangatha.addPlayer(playerName, choosenCard, !isIn);

        mangatha.shuffle();
        mangatha.evaluatePlayers(choosenCard);
        final IWebExchange webExchange = this.application.buildExchange(request, response);
        final WebContext ctx = new WebContext(webExchange);
//        ctx.setVariable("TopCard", mangatha.removeTop());
        //response.sendRedirect(request.getContextPath() + "/mangathaDisplay");
        ctx.setVariable("Players", mangatha.getPlayers());
        templateEngine.process("Mangatha", ctx, response.getWriter());
    }
}

