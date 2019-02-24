package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/2/24/024.
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("正在访问hello...");
        return "hello";
    }

}
