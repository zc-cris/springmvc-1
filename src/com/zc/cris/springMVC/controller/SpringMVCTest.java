package com.zc.cris.springMVC.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.zc.cris.springMVC.entities.User;


@SessionAttributes(value= {"user"},types= {String.class})
@RequestMapping("/springMVC")
@Controller
public class SpringMVCTest {

	private static final String SUCCESS = "success";

	@RequestMapping("testRedirect")
	public String testRedirect() {
		System.out.println("testRedirect");
		return "redirect:/index.jsp";
	}
	
	
	@RequestMapping("testMyView")
	public String testMyView() {
		System.out.println("testMyView");
		return "myView";
	}
	
	
	/*
	 * 有 @ModelAttribute 标记的方法，会在每个目标方法执行之前被springMVC 调用
	 * 1. @ModelAttribute 注解也可以用来修饰目标方法的入参，其value值有以下作用：
	 * 	- springMVC 会使用value 属性值在 implicitModel 中查找对应的对象，若存在则会直接传入到目标方法的入参中
	 * 	- springMVC 会以 value 作为key，pojo类型的对象为 value，存入到request 域中
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value="id",required=false) Integer id,
			Map<String, Object> map) {
		System.out.println("modelAttribute method");
		//说明前台发来的请求是修改用户信息的请求
		if(id != null) {
			//模拟从数据库取出对应的数据
			User user = new User(1, "古天绿", "000", "9");
			System.out.println("从数据库取出来还没有修改的数据："+user);
			map.put("user", user);
		}
	}
	
	/*
	 * 运行流程解析：
	 * 1. 执行@ModelAttribute 注解修饰的方法：从数据库取出对象，再把对象放入到了 Map中，键为 user
	 * 2. springMVC 从Map中取出user对象，并把表单的请求参数赋给user对象对应的属性
	 * 3. springMVC 把修改后的user对象传入到目标方法的参数中
	 * 
	 * 注意：在 @ModelAttribute 修饰的方法中，放入到Map中的键的值需要和目标方法的参数类型的第一个字母小写
	 * 后的值相一致
	 *
	 * SpringMVC 确定目标方法 POJO 类型入参的过程
	 * 1. 确定一个 key:
	 * 1). 若目标方法的 POJO 类型的参数木有使用 @ModelAttribute 作为修饰, 则 key 为 POJO 类名第一个字母的小写
	 * 2). 若使用了  @ModelAttribute 来修饰, 则 key 为 @ModelAttribute 注解的 value 属性值. 
	 * 2. 在 implicitModel 中查找 key 对应的对象, 若存在, 则作为入参传入
	 * 1). 若在 @ModelAttribute 标记的方法中在 Map 中保存过, 且 key 和 1 确定的 key 一致, 则会获取到. 
	 * 3. 若 implicitModel 中不存在 key 对应的对象, 则检查当前的 Handler 是否使用 @SessionAttributes 注解修饰, 
	 * 若使用了该注解, 且 @SessionAttributes 注解的 value 属性值中包含了 key, 则会从 HttpSession 中来获取 key 所
	 * 对应的 value 值, 若存在则直接传入到目标方法的入参中. 若不存在则将抛出异常. 
	 * 4. 若 Handler 没有标识 @SessionAttributes 注解或 @SessionAttributes 注解的 value 值中不包含 key, 则
	 * 会通过反射来创建 POJO 类型的参数, 传入为目标方法的参数
	 * 5. SpringMVC 会把 key 和 POJO 类型的对象保存到 implicitModel 中, 进而会保存到 request 中. 
	 * 
	 * 源代码分析的流程
	 * 1. 调用 @ModelAttribute 注解修饰的方法. 实际上把 @ModelAttribute 方法中 Map 中的数据放在了 implicitModel 中.
	 * 2. 解析请求处理器的目标参数, 实际上该目标参数来自于 WebDataBinder 对象的 target 属性
	 * 1). 创建 WebDataBinder 对象:
	 * ①. 确定 objectName 属性: 若传入的 attrName 属性值为 "", 则 objectName 为类名第一个字母小写. 
	 * *注意: attrName. 若目标方法的 POJO 属性使用了 @ModelAttribute 来修饰, 则 attrName 值即为 @ModelAttribute 
	 * 的 value 属性值 
	 * 
	 * ②. 确定 target 属性:
	 * 	> 在 implicitModel 中查找 attrName 对应的属性值. 若存在, ok
	 * 	> *若不存在: 则验证当前 Handler 是否使用了 @SessionAttributes 进行修饰, 若使用了, 则尝试从 Session 中
	 * 获取 attrName 所对应的属性值. 若 session 中没有对应的属性值, 则抛出了异常. 
	 * 	> 若 Handler 没有使用 @SessionAttributes 进行修饰, 或 @SessionAttributes 中没有使用 value 值指定的 key
	 * 和 attrName 相匹配, 则通过反射创建了 POJO 对象
	 * 
	 * 2). SpringMVC 把表单的请求参数赋给了 WebDataBinder 的 target 对应的属性. 
	 * 3). *SpringMVC 会把 WebDataBinder 的 attrName 和 target 给到 implicitModel. 
	 * 近而传到 request 域对象中. 
	 * 4). 把 WebDataBinder 的 target 作为参数传递给目标方法的入参. 
	 */
	@RequestMapping("testModelAttribute")
	public String testModelAttribute(User user) {
	
		System.out.println("修改后的user："+user);
		
		return SUCCESS;
	}
	
	
	
	
	/*
	 * @SessionAttributes 注解除了可以通过属性名指定需要放到会话中的属性外（实际上使用的是 value 属性值）
	 * 还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中（实际上使用的是 type 属性值）
	 * 
	 * 注意：该注解只能放在类的上面，而不能修饰方法
	 */
	@RequestMapping("testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map) {
		map.put("user", new User("zc-cris", "123", "23"));
		map.put("school", "重庆南开中学");
		return SUCCESS;
	}
	
	
	
	
	/*
	 * 目标方法可以添加Map 类型，实际上也可以是 Model 类型或者ModelMap 类型的参数，
	 * 但是推荐使用 Map 类型，spring 会自动装饰这个 Map 类型的参数
	 */
	@RequestMapping("testMap")
	public String testMap(Map<String, Object> map) {
		map.put("cars", Arrays.asList("法拉利","玛莎拉蒂","兰博基尼","保时捷"));
		return SUCCESS;
	}
	
	
	/*
	 * 目标方法的返回值可以是 ModelAndView 类型的
	 * 其中可以包含视图信息和模型信息
	 * springMVC 会把 ModelAndView 的model中的数据放入到request的域对象中
	 */
	@RequestMapping("testModelAndView")
	public ModelAndView testModelAndView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("date", new Date());
		mv.setViewName(SUCCESS);
		return mv;
	}
	
	
	/*
	 * 可以使用原生的 servlet 的api 作为目标方法的参数，具体支持以下类型
	 * 
	 * HttpServletRequst
	 * HttpServletResponse
	 * HttpSession
	 * java.security.Principal
	 * Locale
	 * InputStream
	 * OutputStream
	 * Reader
	 * Writer
	 */
	@RequestMapping("testServletAPI")
	public void testServletAPI(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("request:"+ request+"\n"+",response"+response);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("你好，我屎渣渣飞");
	}
	
	
	
	/*
	 * springMVC 会先按照请求参数名和pojo 的属性名自动匹配
	 * 并且自动为该对象填充属性值，而且支持级联属性，十分方便
	 */
	@RequestMapping("testPOJO")
	public String testPOJO(User user) {
		System.out.println("user:"+user);
		return SUCCESS;
	}
	
	
	
	/*
	 * @CookieValue 注解主要是用来映射用户的 cookie 值，了解即可，属性同 @ParamValue
	 */
	@RequestMapping("testCookieValue")
	public String testCookieValue(@CookieValue("JSESSIONID") String sessionId) {
		System.out.println("sessionID========"+sessionId);
		return SUCCESS;
	}
	
	
	/*
	 * 映射http 请求头信息，属性同 @RequestParam
	 * 用的不多，了解即可
	 */
	@RequestMapping("testRequestHeader")
	public String testRequestHeader(@RequestHeader(value="Accept-Language") String language) {
		System.out.println("accept-language-----------"+language);
		return SUCCESS;
	}
	
	
	
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
