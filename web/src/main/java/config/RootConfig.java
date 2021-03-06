package config;

import factory.ColorFactory;
import factory.FilterChainDefinitionMapFactory;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AllSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import redis.RedisSessionDao;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import shiro.MyRealm;
import shiro.SecondRealm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                //defaultWebSecurityManager.setRealm(realm());
                //配置多个角色
                defaultWebSecurityManager.setAuthenticator(modularRealmAuthenticator());
                defaultWebSecurityManager.setRealms(realms());
                //设置会话管理器
                //defaultWebSecurityManager.setSessionManager(sessionManager());
                //defaultWebSecurityManager.setSessionManager(defaultWebSessionManager());
                return defaultWebSecurityManager;
        }

        //配置sessionManage(session管理器)
        public SessionManager defaultSessionManager(){
                DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
                return defaultSessionManager;
        }

        @Bean
        public SessionManager defaultWebSessionManager(){
                DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
                defaultWebSessionManager.setSessionDAO(sessionDAO());
                return defaultWebSessionManager;
        }

        //配置hibernate的缓存管理器ehcatchmanage
        //public EhC

        @Bean
        //配置校验器来管理realm以及验证策略
        public ModularRealmAuthenticator modularRealmAuthenticator(){
                ModularRealmAuthenticator modularRealmAuthenticator=new ModularRealmAuthenticator();
                //添加realm
                modularRealmAuthenticator.setRealms(realms());

                //添加验证策略,默认是AtLeastOneSuccessfulStrategy
                //modularRealmAuthenticator.setAuthenticationStrategy(allSuccessfulStrategy());
                return modularRealmAuthenticator;
        }

        //配置校验策略,所有自定义realm都要满足;通过一个不满足验证条件的就抛出异常，使得程序终止，导致验证失败
        public AllSuccessfulStrategy allSuccessfulStrategy(){
                return new AllSuccessfulStrategy();
        }

        //配置realm，角色管理

        @Bean
        public Collection<Realm> realms(){
                List<Realm> realms=new ArrayList<>();

                realms.add(realm());
                realms.add(realm2());
                return realms;
        }

        @Bean
        public Realm realm(){
                MyRealm myRealm = new MyRealm();
                //提供密码加密器,否则使用默认的SimpleCredentialsMatcher(使用明文进行比对)
                HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
                //设置算法名称
                hashedCredentialsMatcher.setHashAlgorithmName("MD5");
                myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
                return myRealm;
        }

        @Bean
        public Realm realm2(){
                SecondRealm secondRealm=new SecondRealm();
                //提供密码加密器,否则使用默认的SimpleCredentialsMatcher(使用明文进行比对)
                HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
                //设置算法名称
                //hashedCredentialsMatcher.setHashAlgorithmName("MD5");
                //更换hash算法，达到只有一个realm匹配，来验证allSuccessfulStrategy
                hashedCredentialsMatcher.setHashAlgorithmName("SHA1");
                secondRealm.setCredentialsMatcher(hashedCredentialsMatcher);
                return secondRealm;
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
        public ShiroFilterFactoryBean shiroFilter() throws Exception {
                /**
                 * shiroFilterFactoryBean是一个工厂bean，其实返回的bean是getObject（）方法的一个实例
                 *
                 * DelegatingFilterProxy在初始化的时候，会去ico容器中获取到shiroFilterFactoryBean，因为shiroFilterFactoryBean
                 * 是一个bean工厂，会调用shiroFilterFactoryBean的getObject方法，获取到最终的
                 */
                ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();

                /**
                 * 1.配置登录页面，登录成功页面，未成功页面
                 * 2.配置哪些页面需要受保护，以及哪些页面需要权限
                 * 3.拦截器
                 * 1.验证拦截器:anon表示可以匿名访问，authc表示需要登录成功后才可以访问,logout表示登出拦截器
                 * 2.授权拦截器:roles[par]表示需要有哪个角色
                 */
                shiroFilterFactoryBean.setSecurityManager(securityManager());
                shiroFilterFactoryBean.setLoginUrl("/views/login.jsp");
                shiroFilterFactoryBean.setSuccessUrl("/views/success.jsp");
                shiroFilterFactoryBean.setUnauthorizedUrl("/views/unau.jsp");
                /**
                 * 配置权限以及拦截器的两种方式
                 * 1.setFilterChainDefinitions方式，配置字符串解析为最终所需要的Map,间接调用setFilterChainDefinitionMap方式
                 * 2.setFilterChainDefinitionMap方式，直接注入一个Map
                 * url与拦截器的配置方式 url=拦截器[参数]
                 * 1.url支持Ant匹配，支持的通配符有？，*，**，采用第一次匹配优先，从上往下匹配
                 *   a.?代表单个字符
                 *   b.*代表多个字符，代表一个路径
                 *   c.** 代表0-多个路径
                 */

                //方式一
                /*shiroFilterFactoryBean.setFilterChainDefinitions("" +
                        "" +"/views/login.jsp=anon\n"+
                        "" +"/test/login=anon\n"+
                        "" +"/test/logout=logout\n"+
                        "" +"/views/admin.jsp=roles[admin]\n"+
                        "" +"/views/user.jsp=roles[user]\n"+
                        "" +"/**=authc\n"+
                        "");*/

                //方式二
                //shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap());
                shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMapFactory().getObject());
                return shiroFilterFactoryBean;
        }

        /**
         * 从Map工厂创建一个Map对象
         * @return
         */
       /* @Bean
        public Map<String,String > filterChainDefinitionMap(){
                FilterChainDefinitionMapFactory filterChainDefinitionMapFactory = filterChainDefinitionMapFactory();
                return filterChainDefinitionMapFactory.getInstance();
        }*/

        /**
         * 创建Map工厂
         * @return
         */
        @Bean
        public FilterChainDefinitionMapFactory filterChainDefinitionMapFactory(){
                return new FilterChainDefinitionMapFactory();
        }

        @Bean
        public ColorFactory colorFactory(){
                ColorFactory colorFactory = new ColorFactory();
                return colorFactory;
        }

        @Bean
        public JedisPoolConfig jedisPoolConfig(){
                JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
                jedisPoolConfig.setMaxTotal(20);
                jedisPoolConfig.setTestOnBorrow(true);
                return jedisPoolConfig;
        }

        @Bean(destroyMethod = "close")
        public JedisPool jedisPool(){
                return new JedisPool(jedisPoolConfig(),"127.0.0.1",6379);
        }

        @Bean
        public SessionDAO sessionDAO(){
                RedisSessionDao sessionDAO=new RedisSessionDao();
                sessionDAO.setJedisPool(jedisPool());
                return sessionDAO;
        }
}
