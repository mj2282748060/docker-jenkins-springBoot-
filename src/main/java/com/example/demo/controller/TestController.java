package com.example.demo.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author moujie
 * @className TestController
 * @description TODO 类描述
 * @date 2021/7/7
 **/
@RestController
@RequestMapping("/demo")
public class TestController {

	@RequestMapping("/test/{name}")
	public String test(@PathVariable("name") String name) {
       	return "自动部署测试成功： 你输入了： "+ name;
	}
}
