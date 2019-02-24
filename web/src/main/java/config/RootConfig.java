package config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import shiro.MyRealm;

/**
 *
 * 这是一个spring容器启动时候需要加载的配置类，类似原来的spring-context.xml
 * Created by Administrator on 2019/1/27/027.
 */
@ComponentScan(basePackages = {"shiro"},excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = Controller.class)}
        )
public class RootConfig {

        //配置shiro的安全管理器
        @Bean
        public SecurityManager securityManager(){
                DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
                //配置缓存管理器
                //defaultWebSecurityManager.setCacheManager(null);
                //配置角色realm
                defaultWebSecurityManager.setRealm(realm());
                return defaultWebSecurityManager;
        }

        //配置hibernate的缓存管理器ehcatchmanage
        //public EhC

        //配置realm，角色管理
        @Bean
        public Realm realm(){
                return new MyRealm();
        }

        //配置生命周期后置处理bean
        @Bean
        public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
                return new LifecycleBeanPostProcessor();
        }

        //配置在spring中可以使用shiro注解，但是前提是要配置lifecycleBeanPostProcessor
        @Bean
        public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
                DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
                return defaultAdvisorAutoProxyCreator;
        }

        @Bean
        public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
                AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
                authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
                return authorizationAttributeSourceAdvisor;
        }

        //配置shiroFilter，id (bean的名称)必须和web.xml中的filterName一致
        @Bean
        public ShiroFilterFactoryBean shiroFilter(){
                ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

                /**
                 * 1.配置登录页面，登录成功页面，未成功页面
                 * 2.配置哪些页面需要受保护，以及哪些页面需要权限
                 * 3.anon表示可以匿名访问，authc表示需要登录成功后才可以访问
                 */
                shiroFilterFactoryBean.setSecurityManager(securityManager());
                shiroFilterFactoryBean.setLoginUrl("/views/login.jsp");
                shiroFilterFactoryBean.setSuccessUrl("/views/success.jsp");
                shiroFilterFactoryBean.setUnauthorizedUrl("views/unau.jsp");
                shiroFilterFactoryBean.setFilterChainDefinitions("" +
                        "" +"/views/login.jsp=anon\n"+
                        "" +"/**=authc\n"+
                        "");

                return shiroFilterFactoryBean;
        }
}
