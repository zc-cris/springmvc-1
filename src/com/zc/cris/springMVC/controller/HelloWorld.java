package com.zc.cris.springMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {
	
	/*
	 * 1. 使用@RequestMapping 注解来映射请求的 url
	 * 2. 返回值会通过视图解析器解析为实际的物理视图，对于 InternalResourceViewResolver 视图解析器，
	 * 将会把 prefix + returnVal + subffix 拼接成实际的物理视图地址，做转发操作
	 */
	@RequestMapping(value="/helloworld")
	public String say() {
		System.out.println("hello world!!!");
		return "success";
	}
	
	
	
	
	
}
