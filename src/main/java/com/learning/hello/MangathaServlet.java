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
	 
//	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
//	        final IWebExchange webExchange = this.application.buildExchange(request, response);
//	        final WebContext ctx = new WebContext(webExchange);
////	        ctx.setVariable("Players", mangatha.getPlayers());
////     		ctx.setVariable("TopCardIn", mangatha.removeTop());
////     		ctx.setVariable("TopCardOut", mangatha.removeTop());
////     		ctx.setVariable("result", result);
//	        templateEngine.process("Mangatha", ctx, response.getWriter());
//	 }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String playerName = request.getParameter("person");
//    	String choosenCardText = request.getParameter("card");
//    	String pos = request.getParameter("position");
//    	boolean isIn = "In".equals(pos);
//    	boolean in =true;
//    	Card choosenCard = new Card(choosenCardText);
//    	mangatha.addPlayer(playerName, choosenCard, isIn);
//        mangatha.shuffle();
//        String result = mangatha.evaluatePlayers(choosenCard);
//        final IWebExchange webExchange = this.application.buildExchange(request, response);
//        final WebContext ctx = new WebContext(webExchange);  
//        
////        System.out.println("Hello");
//        String action = request.getParameter("action");
////        if("Register".equals(action)) {
////        	ctx.setVariable("Players", mangatha.getPlayers());
////        }
////        if("display".equals(action)) {
////        	ctx.setVariable("TopCardIn", mangatha.getinCard());
////     		ctx.setVariable("TopCardOut", mangatha.getOutCard());
////        }
////        ctx.setVariable("TopCard", mangatha.removeTop());
////    	templateEngine.process("Mangatha", ctx, response.getWriter());
////        response.sendRedirect(request.getContextPath()+ "/Mangatha");
////        doGet(request,response);
//    	if("display".equals(action)) {
//    	mangatha.shuffle();
//        mangatha.addCards(); // Set the inCard and outCard
//
//        // Render the Mangatha template with updated cards
////        final IWebExchange webExchange = this.application.buildExchange(request, response);
////        final WebContext ctx = new WebContext(webExchange);
//        ctx.setVariable("TopCardIn", mangatha.getinCard());
//        ctx.setVariable("TopCardOut", mangatha.getOutCard());
//        // Add other necessary variables
//    	}
//        templateEngine.process("Mangatha", ctx, response.getWriter());
//    
//        
//    }

protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final IWebExchange webExchange = this.application.buildExchange(request, response);
        final WebContext ctx = new WebContext(webExchange);
        boolean in = true;
        String action = request.getParameter("action");
        if ("display".equals(action)) {
            mangatha.shuffle();
            mangatha.addCards();
            if(in) {
            	ctx.setVariable("TopCardIn", mangatha.getinCard());
            	in= false;
            }
            if(!in) {
	            ctx.setVariable("TopCardOut", mangatha.getOutCard());
	            in = true;
            }
        }
        Card chosenCard = in ? mangatha.getinCard() : mangatha.getOutCard();
        String result = mangatha.evaluatePlayers(chosenCard);
        ctx.setVariable("Results", result);
        ctx.setVariable("Players", mangatha.getPlayers());
        templateEngine.process("Mangatha", ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("Register".equals(action)) {
            String playerName = request.getParameter("person");
            String choosenCardText = request.getParameter("card");
            String pos = request.getParameter("position");
            boolean isIn = "In".equals(pos);

            Card choosenCard = new Card(choosenCardText);
            mangatha.addPlayer(playerName, choosenCard, isIn);
        }
        doGet(request, response);
   }
}

