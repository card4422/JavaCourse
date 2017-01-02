package question_12;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jimmy on 2017/1/2.
 */


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase
{
    String value();
}
