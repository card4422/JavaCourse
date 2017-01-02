package question_12;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Jimmy on 2017/1/2.
 */

public class TestMyClass
{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        Class cl = Class.forName("question_12.MyClass");
        Method[] methods = cl.getDeclaredMethods();

        for(Method method : methods)
        {
            if(method.isAnnotationPresent(TestCase.class))
            {
                TestCase tc = method.getAnnotation(TestCase.class);
                String s = (String) method.invoke(cl.newInstance(), tc.value());
                String temp = (String) method.invoke(cl.newInstance(),"method1");
                System.out.println(s+":");
                if(temp.equals(s)){
                    System.out.println("true");
                }
                else{
                    System.out.println("false");
                }
            }
        }
    }
}