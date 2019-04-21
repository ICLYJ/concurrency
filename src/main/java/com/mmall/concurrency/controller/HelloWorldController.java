package com.mmall.concurrency.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：lyj
 * @email: : iclyj@iclyj.cn
 * @date ：2019/4/21 18:33
 */
@RestController
public class HelloWorldController {


    @RequestMapping("/hello")
    public String helloWorld(){
        return "Hello world!";
    }

}
