import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author: yuye.huang
 * @since: 2020/7/26
 */
public class CollectionTest {

    /**
     * String 重写了equals方法，所以equals方法比较的是引用对象地址里的内容；不重写则默认比较引用地址。
     */
    @Test
    public void testEquals() {
        String str1 = new String("str1");
        String str2 = new String("str1");
        Assert.assertEquals(str1, str2);

        // 未重写equals方法
        class EqualsTest {
            private String name;
        };
        EqualsTest obj1 = new EqualsTest();
        EqualsTest obj2 = new EqualsTest();
        Assert.assertEquals(obj1, obj2);
    }



}
