package question_4;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Created by zhuzheng on 16/12/23.
 */
public class JsonTest {

    public static void main(String[] args){
        JSONObject jo = new JSONObject();

        A a = new A();
        a.init();

        JSONArray ja = JSONArray.fromObject(a);

        System.out.println(ja.toString());

        jo.put("newclass", ja);

        System.out.println(jo.toString());
       // ParseJson(BuildJson());
    }
/*
    public static String BuildJson() {

        JSONObject jo = new JSONObject();

        A a = new A();
        a.init();

        JSONArray ja = JSONArray.fromObject(a);

        System.out.println(ja.toString());

        jo.put("newclass", ja);

        System.out.println(jo.toString());

        return jo.toString();

    }


    public static void ParseJson(String jsonString) {

        JSONObject jb = JSONObject.fromObject(jsonString);
        JSONArray ja = jb.getJSONArray("newclass");

        List<A> aList = new ArrayList<A>();

        for (int i = 0; i < ja.size(); i++) {
            A a = new A();

            a.init();

            aList.add(a);
        }

        for (int i = 0; i < aList.size(); i++) {
            A emp = aList.get(i);
            System.out.println("a: " + emp.getA() + " b: "
                    + emp.getB() + " c: " + emp.getC());
        }

    }
*/
}
