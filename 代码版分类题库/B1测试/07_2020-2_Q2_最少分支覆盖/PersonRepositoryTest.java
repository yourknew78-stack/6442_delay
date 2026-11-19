
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PersonRepositoryTest {

    SequenceGenerator sequenceGenerator;
    PersonRepository personRepository;

    @Before
    public void before() {
        sequenceGenerator = new SequenceGenerator();
        personRepository = new PersonRepository();
        personRepository.setSequenceGenerator(sequenceGenerator);
    }

    // Implement your test cases
    // Hint: The java.lang.Integer.intValue() is an inbuilt method in Java that returns the value of an Integer as an int.

    // ########## YOUR CODE STARTS HERE ##########
    @Test(timeout=1000)
    public void saveNewPerson() {
        PersonRepository.personMap.clear(); // 清理 static Map，避免测试互相污染。
        Person p=new Person("Ada",30,"teacher",false,false);
        assertEquals(p,personRepository.save(p)); // save 成功分支：一个断言
    }
    @Test(timeout=1000)
    public void rejectExistingId() {
        Person p=new Person("Ada",30,"teacher",false,false);
        p.setId(99);
        org.junit.Assert.assertThrows(RuntimeException.class,()->personRepository.save(p));
    }
    @Test(timeout=1000)
    public void findAndSetGenerator() {
        PersonRepository.personMap.clear();
        Person p=new Person("Ada",30,"teacher",false,false);
        p.setId(99);
        PersonRepository.personMap.put(99,p); // 直接准备数据，不增加 save 用例
        assertEquals(p,personRepository.findById(99));
        assertEquals(null,personRepository.findById(-1));
        assertEquals(true,personRepository.setSequenceGenerator(new SequenceGenerator()));
    }

    // ########## YOUR CODE ENDS HERE ##########


}
