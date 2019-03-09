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
        /**
         * 为什么这个filter的id要和ioc容器里面的bean的id一样?
         * 1.因为DelegatingFilterProxy是一个filter,在执行生命周期方法时，init(FilterConfig filterConfig)中，可以获取到filter的配置对象filterConfig
         * ，在initFilterBean()方法中，会对DelegatingFilterProxy是否设置targetBeanName进行判断，如果有则采用，如果没有使用filter的名称
         *
         * 2.其实DelegatingFilterProxy是一个代理对象，正真的对象是ioc容器中的对象
         */
        FilterRegistration.Dynamic shiroFilter = servletContext.addFilter("shiroFilter", DelegatingFilterProxy.class);
        shiroFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST),true,"/*");
        //shiroFilter.setInitParameter("targetBeanName","aaa");
    }
}

