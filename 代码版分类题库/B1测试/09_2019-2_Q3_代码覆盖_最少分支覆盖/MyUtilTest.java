/* 简单读法
 * parseDouble 要求 code complete：输入分别覆盖提前返回、找不到数字、成功解析。
 * 浮点断言第三个参数是允许误差，例如 0.000001，避免浮点表示差异造成误判。
 */
package utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO: write a minimum number of JUnit test cases (assertEquals) for
 * {@code MyUtil.parseDouble} that is code complete.
 * 
 * @author dongwoo
 *
 */
public class MyUtilTest {

	@Test
	public void test() {
        //start your code
        assertEquals(0.0,MyUtil.parseDouble(null),0.000001); // 独立的null返回
        assertEquals(0.0,MyUtil.parseDouble("abc"),0.000001);// 没找到数字的返回
        assertEquals(12.34,MyUtil.parseDouble("abc12.34xyz"),0.000001); // 所有扫描、解析语句

        //end your code
	}

}
