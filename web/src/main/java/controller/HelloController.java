package controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2019/2/24/024.
 */
@Controller
@RequestMapping("/test")
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("正在访问hello...");
        return "hello";
    }


    @RequestMapping("/login")
    public String login(String username,String password){
        //获得需要认证的subject
        Subject subject = SecurityUtils.getSubject();
        //判断是否认证过
        if(!subject.isAuthenticated()){
            //没有认证,那么把用户名和密码封装成认证对象
            UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
            //记住我
            usernamePasswordToken.setRememberMe(true);
            //进行登录
            try {
                subject.login(usernamePasswordToken);
            }catch (AuthenticationException e){
                System.out.println("认证失败:"+e.getMessage());
                return "login";
            }

        }
        return "redirect:/views/success.jsp";
    }


}
