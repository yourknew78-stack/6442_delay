/* 简单读法
 * @Before 在每个测试前新建角色。@Test(expected=...) 表示本次调用必须抛指定异常。
 * 一次只传一个 null：测按键为空时角色正常，测角色为空时按键正常。
 */
import org.junit.Before;
import org.junit.Test;

import exceptions.NullCharacterException;
import exceptions.NullKeyEventException;

public class ExceptionTest {

	private Character character;

	@Before
	public void init() {
		this.character = new Character();
	}

	@Test(expected = NullKeyEventException.class, timeout = 1000)
	public void testNullKeyException() {
		// TODO
        // ########## YOUR CODE STARTS HERE ##########
        // character 必须有效，只有 event=null，隔离本次要测试的错误。
        character.getState().handle(character, null);

        // ########## YOUR CODE ENDS HERE ##########
	}

	@Test(expected = NullCharacterException.class, timeout = 1000)
	public void testNullCharacterException() {
		// TODO
        // ########## YOUR CODE STARTS HERE ##########
        // 接收者是有效状态；传入的 character=null，event 使用有效值。
        character.getState().handle(null, Key.UP);

        // ########## YOUR CODE ENDS HERE ##########
	}
}
