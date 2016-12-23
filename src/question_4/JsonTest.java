package question_4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


/**
 * Created by zhuzheng on 16/12/23.
 */
public class JsonTest {

    public static void main(String[] args){
        A a = new A();
        a.init();
        printJson(a);
    }

    private static void printJson(A a){

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());


        JSONObject jo = JSONObject.fromObject(a, jsonConfig);

        System.out.println(jo.toString());
    }
}
