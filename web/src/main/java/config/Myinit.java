package config;

import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.*;
import java.util.EnumSet;
import java.util.Set;

/**
 * 在容器启动的时候，会调用ServletContainerInitializer的实现类的onStartup方法
 * 1.servlet3.0利用这个来进行spring和springmvc整合
 * Created by Administrator on 2019/2/24/024.
 */

public class Myinit implements ServletContainerInitializer{
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("test");
        //下面是整合shiro和spring

        //添加shiroFilter
        FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
        shiroFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");

    }
}
