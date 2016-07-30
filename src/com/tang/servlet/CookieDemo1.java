package com.tang.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieDemo1
 */
// 代表首页的Servlet

@WebServlet("/CookieDemo1")
public class CookieDemo1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		PrintWriter out = response.getWriter();

		// 1.输出网站所有的商品
		out.write("本网站有如下商品： <br/>");
		Map<String, Book> map = DB.getAll();
		for(Map.Entry<String, Book> entry:map.entrySet()){
			Book book = entry.getValue();
			out.print("<a href='/ShoppingRecord/CookieDemo2?id="+book.getId()+"' target='_blank'>" + book.getName() + "</a><br/>");	
		}
		
		
		// 2. 显示已经游览过的商品

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

class DB {
	private static HashMap<String, Book> map = new LinkedHashMap();
	static {
		map.put("1", new Book("1", "Java Web开发", "老张", "一本好书！！！"));
		map.put("2", new Book("2", "Java SE开发", "唐建", "一本好书！！！"));
		map.put("3", new Book("3", "Java Sping开发", "糖糖", "一本好书！！！"));
		map.put("4", new Book("4", "Java JDBC开发", "唐唐", "一本好书！！！"));
		map.put("5", new Book("5", "Java Struts开发", "宋鸽", "一本好书！！！"));
	}

	public static Map getAll() {
		return map;
	}
}

class Book {
	private String id;
	private String name;
	private String author;
	private String description;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(String id, String name, String author, String description) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
