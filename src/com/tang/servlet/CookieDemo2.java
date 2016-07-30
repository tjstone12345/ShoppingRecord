package com.tang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieDemo2
 */

//显示商品详细信息的servlet


@WebServlet("/CookieDemo2")
public class CookieDemo2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
	PrintWriter out = response.getWriter();	
		// 1. 根据用户带来的ID，像是相应商品的详细信息
		
	String id =	request.getParameter("id");
	Book book = (Book)DB.getAll().get(id);
	out.print(book.getId() + "<br/>");
	out.print(book.getAuthor() + "<br/>");
	out.print(book.getName() + "<br/>");
	out.print(book.getDescription() + "<br/>");
		
    // 2. 构建Cookie,会写给浏览器
	String cookieValue = buildCookie(id, request);
	Cookie cookie = new Cookie("bookHistory",cookieValue);
	cookie.setMaxAge(30*24*3600);
	cookie.setPath("/ShoppingRecord");
	response.addCookie(cookie);
  }
 
	private String buildCookie(String id, HttpServletRequest request) {
		//第一次 bookHistory = null    本次看了书 1，   构建cookie返回 1
		
		//看过书，再次访问 则cookie带了值 bookHistory = 2,5,1   本次看了1   返回1,2,5
		
		//看过书，再次访问 则cookie带了值 bookHistory = 2,5,4   本次看了1   返回1, 2, 5
		
		//看过书，再次访问 则cookie带了值 bookHistory = 2,5     本次看了1   返回 1，2，5
 		String bookHistory = null;
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies!=null && i<cookies.length;i++){
			if(cookies[i].getName().equals("bookHistory")){
				bookHistory = cookies[i].getValue();
			}
		}		
		if(bookHistory==null){
			return id;
		}
		LinkedList<String> list = new LinkedList<String>(Arrays.asList(bookHistory.split("\\,")));	
		if (list.contains(id)){
			list.remove(id);
			list.addFirst(id);
		} else{
			if(list.size()>=3){
				list.removeLast();
				list.addFirst(id);
			} else {
				list.addFirst(id);
			}
		}	
		
		StringBuffer sb = new StringBuffer();
		for (String bid: list){
			sb.append(bid+",");
		}
		
		return sb.deleteCharAt(sb.length()-1).toString();	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
