package com.zc.cris.springMVC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

@RequestMapping("/springMVC")
@Controller
public class SpringMVCTest {

	private static final String SUCCESS = "success";

	
	/*
	 * @RequestParam 注解来映射请求参数
	 * 1. value 值即为请求参数的参数名
	 * 2. required 该参数是否是必须，默认是true
	 * 3. defaultValue 请求参数如果不写，后台自动补上默认值
	 */
	@RequestMapping("testRequestParam")
	public String testRequestParam(@RequestParam(value="name") String name, 
			@RequestParam(value="age",required=false,defaultValue="10") Integer age) {
		System.out.println("name="+name+"----age="+age);
		return SUCCESS;
	}
	
	
	
	
	/*
	 * 如果使用 rest 风格的方法来访问 controller 的特定方法，要记住该方法内部不能直接转发到jsp，
	 * 因为发起的请求是个RESTFul风格的请求，调用了RESTFul风格的PUT方法。
	 * 但是controller里对应的方法返回的success字符串被映射到success.jsp。因此spring认为这应该是个JSP接口，
	 * 且JSP接口仅仅支持GET方法和POST方法，所以报 405 - JSPs only permit GET POST or HEAD 错误
	 * 
	 * 解决方案如下：如果确实要在 restFul 请求的方法里转发到某一个静态资源，就只好围魏救赵了
	 * 	先定义一个转发到静态资源的方法a，然后在 restFul 请求的方法内部进行重定向方法a即可，不清楚的直接看代码即可
	 */
	@RequestMapping("success")
	public String toSuccess() {
		return SUCCESS;
	}

	/*
	 * Rest 风格的 url：
	 * 以 CRUD 为例：
	 * 新增：/order		POST		以前：add?id=1
	 * 修改：/order/1		PUT			以前：update?id=1
	 * 删除：/order/1  	DELETE		以前: delete?id=1
	 * 查询：/order/1	  	GET			以前：get?id=1
	 * 
	 * 如何发送 put 和delete 请求呢？
	 * 1. 配置 HiddenHttpMethodFilter
	 * 2. 需要发送 post 请求
	 * 3. 需要在发送post请求时，携带一个 name="_method" 的隐藏域，值为 put 或者 delete
	 * 
	 * 在 springMVC 中的目标方法中，如何得到id呢？
	 * 使用@PathVariable 注解
	 * 
	 */
	@RequestMapping(value = "testRest/{id}", method = RequestMethod.PUT)
	public ModelAndView testRestPut(@PathVariable("id") Integer id) {
		System.out.println("testRestPut:" + id);
		return new ModelAndView("redirect:/springMVC/success");

	}

	@RequestMapping(value = "testRest/{id}", method = RequestMethod.DELETE)
	public ModelAndView testRestDelete(@PathVariable("id") Integer id) {
		System.out.println("testRestGetDelete:" + id);
		return new ModelAndView("redirect:/springMVC/success");

	}

	@RequestMapping(value = "testRest", method = RequestMethod.POST)
	public String testRestPost() {
		System.out.println("testRestPost:");
		return SUCCESS;

	}

	@RequestMapping(value = "testRestGet/{id}", method = RequestMethod.GET)
	public String testRestGet(@PathVariable("id") Integer id) {
		System.out.println("testRestGet:" + id);
		return SUCCESS;

	}

	/*
	 * @PathVariable 可以映射 url 中的占位符到目标方法的参数中，非常方便快捷
	 */
	@RequestMapping("testPathVariable/{id}")
	public String testPathVariable(@PathVariable("id") Integer id) {
		System.out.println("testPathVariable:" + id);
		return SUCCESS;
	}

	/*
	 * 测试 antPath 格式的url映射
	 */
	@RequestMapping("testAntPath/*/abc")
	public String testAntPath() {
		return SUCCESS;
	}

	/*
	 * 还可以使用params和headers属性来进行更加精确的url映射，并且params和headers都还支持简单的表达式 了解即可
	 */
	@RequestMapping(value = "testParamAndHeader", params = { "name", "age!=10" }, headers = {
			"Accept-Language=en,zh-CN;q=0.9,zh;q=0.8" })
	public String testParamAndHeader() {
		System.out.println("testParamsAndHeader");
		return SUCCESS;
	}

	/*
	 * 根据method进行映射过滤
	 */
	@RequestMapping(value = "/testMethod", method = RequestMethod.POST)
	public String testMethod() {
		System.out.println("testMethod");
		return SUCCESS;
	}

	/*
	 * 1. Spring MVC 使用 @RequestMapping 注解为控制器指定可以处理哪些 URL 请求 2. 在控制器的类定义及方法定义处都可标注
	 * 1. 类定义处：提供初步的请求映射信息。相对于 WEB 应用的根目录 2. 方法处：提供进一步的细分映射信息。相对于类定义处的 URL。
	 * 若类定义处未标注 @RequestMapping，则方法处标记的 URL 相对于WEB 应用的根目录
	 */
	@RequestMapping("/test")
	public String test() {
		System.out.println("test");
		return SUCCESS;
	}

}
