package redis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2019/4/4/004.
 */
public class RedisSessionDao extends AbstractSessionDAO {
    private static final Logger log = LoggerFactory.getLogger(MemorySessionDAO.class);


    private JedisPool jedisPool;

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    private void closeJedis(Jedis jedis){
        jedis.close();
    }

    public RedisSessionDao() {

    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.storeSession(sessionId, session);
        return sessionId;
    }

    protected Session storeSession(Serializable id, Session session) {
        Jedis jedis = getJedis();
        if(id == null && jedis!=null) {
            throw new NullPointerException("id and jedis argument cannot be null.");
        } else {
            jedis.set(id.toString(),serialize(session));
            //关闭jedis
            closeJedis(jedis);
            return session;
        }
    }

    /**
     * 序列化为字符串
     * @param session
     * @return
     */
    private String  serialize(Session session){
        ByteArrayOutputStream byteArrayOutputStream=null;
        ObjectOutputStream objectOutputStream=null;
        try {
            byteArrayOutputStream=new ByteArrayOutputStream();
            objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(session);
            return  byteArrayOutputStream.toString("iso-8859-1");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(byteArrayOutputStream!=null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 反序列化为session
     * @param sessionStr
     */
    private Session  deSerialize(String sessionStr){
        ByteArrayInputStream byteArrayInputStream=null;
        ObjectInputStream objectInputStream=null;
        try {
            byteArrayInputStream=new ByteArrayInputStream(sessionStr.getBytes("iso-8859-1"));
            objectInputStream=new ObjectInputStream(byteArrayInputStream);
            return (Session) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(byteArrayInputStream!=null){
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    protected Session doReadSession(Serializable sessionId) {
        Jedis jedis = getJedis();

        String s = jedis.get(sessionId.toString());

        closeJedis(jedis);
        return deSerialize(s);
    }

    public void update(Session session) throws UnknownSessionException {
        this.storeSession(session.getId(), session);
    }

    public void delete(Session session) {
        Jedis jedis = getJedis();
        if(session == null) {
            throw new NullPointerException("session argument cannot be null.");
        } else {
            Serializable id = session.getId();
            if(id != null) {
                jedis.del(id.toString());
                jedis.close();
            }

        }
    }

    public Collection<Session> getActiveSessions() {
        return null;
    }

    public static void main(String[] args) {
        RedisSessionDao redisSessionDao = new RedisSessionDao();

        Session session = redisSessionDao.deSerialize("\\xC2\\xAC\\xC3\\xAD\\x00\\x05sr\\x00*org.apache.shiro.session.mgt.SimpleSession\\xC2\\x9D\\x1C\\xC2\\xA1\\xC2\\xB8\\xC3\\x95\\xC2\\x8Cbn\\x03\\x00\\x00xpw\\x02\\x00\\xC3\\x9Bt\\x00$5122420e-304c-4320-a4d1-c8efa28ac03bsr\\x00\\x0Ejava.util.Datehj\\xC2\\x81\\x01KYt\\x19\\x03\\x00\\x00xpw\\x08\\x00\\x00\\x01i\\xC3\\xA6\\xC2\\x86\\xC2\\xAB\\xsq\\x00~\\x00\\x03w\\x08\\x00\\x00\\x01i\\xC3\\xA6\\xC2\\x8A\\xC2\\xB7Xxw\\x19\\x00\\x00\\x00\\x00\\x00\\x1Bw@\\x00\\x0F0:0:0:0:0:0:0:1sr\\x00\\x11java.util.HashMap\\x05\\x07\\xC3\\x9A\\xC3\\x81\\xC3\\x83\\x16`\\xC3\\x91\\x03\\x00\\x02F\\x00\\x0AloadFactorI\\x00\\x09thresholdxp?@\\x00\\x00\\x00\\x00\\x00\\x0Cw\\x08\\x00\\x00\\x00\\x10\\x00\\x00\\x00\\x03t\\x00\\x11shiroSavedRequestsr\\x00&org.apache.shiro.web.util.SavedRequest\\xC2\\xAF\\xC3\\x8E<\\xC2\\xADy\\xC2\\x82\\xC3\\x8A\\xC2\\xBA\\x02\\x00\\x03L\\x00\\x06methodt\\x00\\x12Ljava/lang/String;L\\x00\\x0BqueryStringq\\x00~\\x00\\x0AL\\x00\\x0ArequestURIq\\x00~\\x00\\x0Axpt\\x00\\x03GETpt\\x00\\x07/shiro/t\\x00Porg.apache.shiro.subject.support.DefaultSubjectContext_AUTHENTICATED_SESSION_KEYsr\\x00\\x11java.lang.Boolean\\xC3\\x8D r\\xC2\\x80\\xC3\\x95\\xC2\\x9C\\xC3\\xBA\\xC3\\xAE\\x02\\x00\\x01Z\\x00\\x05valuexp\\x01t\\x00Morg.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEYsr\\x002org.apache.shiro.subject.SimplePrincipalCollection\\xC2\\xA8\\x7FX%\\xC3\\x86\\xC2\\xA3\\x08J\\x03\\x00\\x01L\\x00\\x0FrealmPrincipalst\\x00\\x0FLjava/util/Map;xpsr\\x00\\x17java.util.LinkedHashMap4\\xC3\\x80N\\\\x10l\\xC3\\x80\\xC3\\xBB\\x02\\x00\\x01Z\\x00\\x0BaccessOrderxq\\x00~\\x00\\x06?@\\x00\\x00\\x00\\x00\\x00\\x0Cw\\x08\\x00\\x00\\x00\\x10\\x00\\x00\\x00\\x01t\\x00\\x0Fshiro.MyRealm_2sr\\x00\\x17java.util.LinkedHashSet\\xC3\\x98l\\xC3\\x97Z\\xC2\\x95\\xC3\\x9D*\\x1E\\x02\\x00\\x00xr\\x00\\x11java.util.HashSet\\xC2\\xBAD\\xC2\\x85\\xC2\\x95\\xC2\\x96\\xC2\\xB8\\xC2\\xB74\\x03\\x00\\x00xpw\\x0C\\x00\\x00\\x00\\x02?@\\x00\\x00\\x00\\x00\\x00\\x01t\\x00\\x05adminxx\\x00w\\x01\\x01q\\x00~\\x00\\x16xxx");

        System.out.println(session);
    }
}
