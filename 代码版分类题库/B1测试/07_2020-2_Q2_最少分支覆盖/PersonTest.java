/* 简单读法
 * 每个 assertEquals 检查一组输入的预期输出。
 * 原题按断言执行次数计数，且明确把 &&、|| 的子条件算作分支；不要套用其他年份规则。
 */
import org.junit.Assert;
import org.junit.Test;

public class PersonTest {
     // Implement your test cases
    // Hint: The java.lang.Integer.intValue() is an inbuilt method in Java that returns the value of an Integer as an int.

    // ########## YOUR CODE STARTS HERE ##########
    @Test(timeout=1000)
    public void minimumInsuranceCases() {
        // 4种疾病组合 + age<=40 + 普通职业 = 6 个价格断言。
        Assert.assertEquals(1100,new Person("A",41,null,true,true).getHealthInsurancePrice().intValue());
        Assert.assertEquals(360,new Person("A",41,"",false,false).getHealthInsurancePrice().intValue());
        Assert.assertEquals(500,new Person("A",41,"policeman",false,true).getHealthInsurancePrice().intValue());
        Assert.assertEquals(400,new Person("A",41,"policeman",true,false).getHealthInsurancePrice().intValue());
        Assert.assertEquals(200,new Person("A",40,"policeman",false,false).getHealthInsurancePrice().intValue());
        Assert.assertEquals(350,new Person("A",41,"teacher",false,false).getHealthInsurancePrice().intValue());
    }
    @Test(timeout=1000)
    public void gettersAndSetter() {
        Person p=new Person("Ada",30,"teacher",true,false);
        p.setId(123);
        Assert.assertEquals(Integer.valueOf(123),p.getId());
        Assert.assertEquals("Ada",p.getName());
        Assert.assertEquals(Integer.valueOf(30),p.getAge());
        Assert.assertEquals("teacher",p.getOccupation());
        Assert.assertTrue(p.getDiabetes());
        Assert.assertFalse(p.getHeartDisease());
        // getter 本身没有条件分支；不额外增加价格断言。
    }

    // ########## YOUR CODE ENDS HERE ##########

}
