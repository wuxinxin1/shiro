package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 在容器启动的时候加载这个类，这个类相当于原来的web.xml文件，相当于整合spring和springmvc
 * 1.配置dispatch的servlet在启动的时候需要初始化的容器配置类
 * 2.添加dispatch的servlet的映射路径
 * 3.配置父容器进行初始化spring容器的配置类
 * Created by Administrator on 2019/1/27/027.
 */
public class MyAbstractAnnotationConfigDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        System.out.println("getRootConfigClasses");
        return new Class<?>[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("getServletConfigClasses");
        return new Class<?>[]{ServletConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        System.out.println("getServletMappings");
        return new String[]{"/"};
    }


}
