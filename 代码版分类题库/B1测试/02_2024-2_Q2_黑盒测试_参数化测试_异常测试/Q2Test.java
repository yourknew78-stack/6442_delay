/* 简单读法
 * 同一组测试会自动轮流测试13个 HashingBox。正确实现应全部通过，错误实现应至少失败一次。
 * expected 由题目公式和固定数值算出，而不是照抄 hashingBox.hash 的返回值。
 * size=0 的规范与全部原实现冲突，详见同目录说明；没有偷偷修改被测实现。
 */
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


/**
 * A parameterized test class for HashingBoxes.
 */
@RunWith(Parameterized.class)
public class Q2Test {
	/**
	 * Return a list of parameters which are different implementation of
	 * abstract {@linkplain HashingBox}.
	 *
	 * Do NOT Modify this part.
	 */
	@Parameters
	public static Iterable<HashingBox> getImplementations() {
		return Arrays.asList(
				new HashingBox0(),
				new HashingBox1(),
				new HashingBox2(),
				new HashingBox3(),
				new HashingBox4(),
				new HashingBox5(),
				new HashingBox6(),
				new HashingBox7(),
				new HashingBox8(),
				new HashingBox9(),
				new HashingBox10(),
				new HashingBox11(),
				new HashingBox12()
		);
	}

	@Parameter
	public HashingBox hashingBox;


    // TODO: ########## YOUR CODE STARTS HERE ##########
    @Test(timeout = 1000)
    public void allValidSizes() throws ComponentOutOfRangeException {
        // A 的 ASCII 是65，长度1；STEEL*CIRCLE=1018。质数表按题意填写。
        int[] primes = {2,2,3,3,5,5,7,7,7,7,11,11,13,13,13,13,
                17,17,19,19,19,19,23,23,23,23,23,23,29,29,31};
        for (int size=1; size<=31; size++) {
            assertEquals("size="+size, 65*primes[size-1]+1+1018,
                    hashingBox.hash(new Thing(Material.STEEL,size,Shape.CIRCLE,"A")));
        }
    }
    @Test(timeout = 1000)
    public void namesAndAsciiBoundaries() throws ComponentOutOfRangeException {
        // 全部 size=3、SILVER/SQUARE；hash = ASCII和*3 + 长度 + 3700。
        String[] names = {"", "Comp2100", "AZaz", "abcdefZ", "1aaaa1aaaa",
                "123456abc", "@[\\]^_`{}", "AbCdEf"};
        int[] expected = {3700,4905,4826,5498,4874,3709,3709,5209};
        for (int i=0;i<names.length;i++) {
            assertEquals("name="+names[i], expected[i],
                    hashingBox.hash(new Thing(Material.SILVER,3,Shape.SQUARE,names[i])));
        }
    }
    @Test(timeout = 1000)
    public void everyMaterialAndShape() throws ComponentOutOfRangeException {
        Material[] materials={Material.STEEL,Material.SILVER,Material.GOLD};
        int[] values={1018,925,999};
        Shape[] shapes={Shape.CIRCLE,Shape.SEMICIRCLE,Shape.TRIANGLE,Shape.SQUARE,Shape.PENTAGON};
        for (int m=0;m<materials.length;m++) {
            for (int s=0;s<shapes.length;s++) {
                // 空名字的 nameHash=0，直接检查材质值*形状值。
                assertEquals(values[m]*(s+1),
                        hashingBox.hash(new Thing(materials[m],3,shapes[s],"")));
            }
        }
    }
    @Test(expected = ComponentOutOfRangeException.class, timeout = 1000)
    public void negativeSize() throws ComponentOutOfRangeException {
        hashingBox.hash(new Thing(Material.STEEL,-1,Shape.CIRCLE,"A"));
    }
    @Test(expected = ComponentOutOfRangeException.class, timeout = 1000)
    public void sizeAbove31() throws ComponentOutOfRangeException {
        hashingBox.hash(new Thing(Material.STEEL,32,Shape.CIRCLE,"A"));
    }
    // size=0：README 与所有实现共用的基类有冲突，见 01-答案与辅助说明.md。
    // 不擅自修改被测类；独立辅助类会明确报告此规范冲突。

    // TODO ########## YOUR CODE ENDS HERE ##########

	/**
	 * This is an example test case for references about the SYNTAX of tests only.
	 * If you believe that the assertion written is WRONG, leave the test commented.
	 * Otherwise, you may uncomment the test to have it as a part of your solutions.
	 *
	 * -  This test is for reference only. i.e., when writing your own tests,
	 *    you do not need to follow the syntax from this example.
	 */
//	@Test(timeout = 1000)
//	public void exampleTestCase() throws ComponentOutOfRangeException {
//		// This is a test that uses the example from the README.
//		Thing thing = new Thing(Material.SILVER, 3, Shape.SQUARE, "Comp2100");
//		int expectedHash = 4905;
//		int actualHash = hashingBox.hash(thing);
//		assertEquals(expectedHash, actualHash);
//	}

}
