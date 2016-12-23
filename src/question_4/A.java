package question_4;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuzheng on 16/12/23.
 */
public class A {
    int a;
    String b;
    Date c;

    public Date getC() {
        return c;
    }

    public void setC(Date c) {
        this.c = c;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void init(){
        a = 110;
        b = "jizhi";
        c = new Date();
    }
}