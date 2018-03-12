package com.zc.cris.springMVC.views;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
public class MyView implements View{

	@Override
	public String getContentType() {
		return "text/html";
	}
	
	@Override
	public void render(Map<String, ?> arg0, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		arg2.getWriter().write("hello,this is my view :"+new Date());
	}
}
