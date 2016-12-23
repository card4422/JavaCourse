package question_4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * Created by Jimmy on 2016/12/23.
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

    private String format ="yyyy年MM月dd日HH时mm分ss秒";

    public Object processArrayValue(Object value, JsonConfig config) {
        return process(value);
    }

    public Object processObjectValue(String key, Object value, JsonConfig config) {
        return process(value);
    }

    private Object process(Object value){

        if(value instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.UK);
            return sdf.format(value);
        }
        return value == null ? "" : value.toString();
    }
}