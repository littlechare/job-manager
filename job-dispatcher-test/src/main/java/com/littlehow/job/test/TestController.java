package com.littlehow.job.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/job/test")
public class TestController {
    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public String test() {
        System.out.println("=========>任务已经在调用了:" + new Date());
        return "{\"message\":\"hello job\"}";
    }
}
