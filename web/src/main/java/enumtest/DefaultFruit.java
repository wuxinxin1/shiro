package enumtest;

import org.apache.shiro.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * 用于测试枚举类型，当你希望某种类型的对象是可枚举的个数的时候，那么放在枚举里面进行创建
 * @author: wuxinxin
 * @date: 2019/3/6
 */
public enum  DefaultFruit {
    apple1(Apple.class),
    banana(Banana.class),
    orange(Orange.class);

    private Class<?extends Fruit> fruitClass;

    private DefaultFruit(Class<?extends Fruit> fruitClass){
        this.fruitClass=fruitClass;
    }

    public Class<? extends Fruit> getFruitClass(){
        return this.fruitClass;
    }

    //创建实例
    public Fruit  createInstance(){
        Object o = ClassUtils.newInstance(this.fruitClass);
        return (Fruit) o;

    }


    public static Map<String,Fruit> getDefaultFruit(){
        Map map=new HashMap();

        DefaultFruit[] values = DefaultFruit.values();

        int len=values.length;

        for (int i=0;i<len;i++){
            DefaultFruit fruit = values[i];

            Fruit instance = fruit.createInstance();

            map.put(fruit.name(),instance);

        }
        return map;
    }
}
