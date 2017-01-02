package question_11;

import java.lang.reflect.InvocationHandler;

import java.lang.reflect.Method;

import java.lang.reflect.Proxy;

/**
 * Created by Jimmy on 2017/1/2.
 */
public class PersonProxy {

    public static void main(String[] args) {

        PersonImpl person = new PersonImpl();    //1.创建委托对象

        ProxyHandler handler = new ProxyHandler(person);    //2.创建调用处理器对象

        Person personProxy = (Person)Proxy.newProxyInstance(PersonImpl.class.getClassLoader(),

                PersonImpl.class.getInterfaces(), handler);    //3.动态生成代理对象

        System.out.println("we only call the eat method with the proxy object...");

        personProxy.eat();    //4.通过代理对象调用方法

    }

}



/**

 * 代理类的调用处理器

 */

class ProxyHandler implements InvocationHandler{

    private Person person;

    public ProxyHandler(Person person){

        this.person = person;

    }



    //此函数在代理对象调用任何一个方法时都会被调用。

    @Override

    public Object invoke(Object proxy, Method method, Object[] args)

            throws Throwable {

        System.out.println("====before method====");//定义预处理的工作，当然你也可以根据 method 的不同进行不同的预处理工作

        if(method.getName().equals("eat"))

        {

            //System.out.println("do wash()...");

            Method bm = person.getClass().getMethod("wash");

            bm.invoke(person);

        }

        System.out.println("====do method====");

        Object result = method.invoke(person, args);

        System.out.println("====after method====");

        if(method.getName().equals("eat"))

        {

            //System.out.println("do gargle()...");

            Method am = person.getClass().getMethod("gargle");

            am.invoke(person);

        }

        return result;

    }

}