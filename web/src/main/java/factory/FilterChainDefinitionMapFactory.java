package factory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/2
 */
public class FilterChainDefinitionMapFactory {

    public Map<String,String >  getInstance(){
        Map<String,String >  map=new LinkedHashMap<>();
        map.put("/views/login.jsp","anon");
        map.put("/test/login","anon");
        map.put("/test/logout","logout");
        map.put("/views/admin.jsp","roles[admin]");
        map.put("/views/user.jsp","roles[user]");
        map.put("/**","authc");
        return map;
    }
}
