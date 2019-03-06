package enumtest;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/6
 */
public class Test {
    public static void main(String[] args) {
       /* DefaultFruit apple = DefaultFruit.apple;

        Class<? extends Fruit> fruitClass = apple.getFruitClass();


        System.out.println(fruitClass);*/


       /* DefaultFruit[] values = DefaultFruit.values();*/


       // String name = DefaultFruit.apple1.name();
        /*Fruit instance = DefaultFruit.apple1.createInstance();

        System.out.println(instance.getClass());*/


        Map<String, Fruit> defaultFruit = DefaultFruit.getDefaultFruit();

        System.out.println(defaultFruit);
    }
}
