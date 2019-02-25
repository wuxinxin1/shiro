package shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;

/**
 * Created by Administrator on 2019/2/24/024.
 */
public class MyRealm extends AuthenticatingRealm{

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //先取出传递过来需要认证的数据
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)authenticationToken;
        //取出需要验证的username
        String username = usernamePasswordToken.getUsername();
        //取数据库查找username对应的记录
        System.out.println("查询"+username+"相关的数据");

        //查到unknow则认为查找失败
        if("unknow".equals(username)){
            throw new UnknownAccountException("用户不存在");
        }

        //下面是数据库中查找到的信息
        Object principal=username;
        Object credentials="123456";

        //非unknow的用户，则认为查找成功，构造认证后的信息返回
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(
                 principal,  credentials,  getName());

        return simpleAuthenticationInfo;
    }
}
