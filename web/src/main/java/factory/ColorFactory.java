package factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by IntelliJ IDEA.
 * 当定义的是工厂的时候，获取到的是getObjectType的类型
 * @author: wuxinxin
 * @date: 2019/3/2
 */
public class ColorFactory implements FactoryBean<Color> , ApplicationContextAware {
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Object colorFactory = applicationContext.getBean("colorFactory");

        if(colorFactory instanceof Color){
            System.out.println("1");
        }

        if(colorFactory instanceof ColorFactory){
            System.out.println("2");
        }

    }
}
