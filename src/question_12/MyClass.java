package question_12;

/**
 * Created by Jimmy on 2017/1/2.
 */
public class MyClass
{
    @TestCase(value = "method1")
    public String method1(String s)
    {
        return s;
    }

    public String method2(String s)
    {
        return s;
    }

    @TestCase(value = "method3")
    public String method3(String s)
    {
        return s;
    }
}
