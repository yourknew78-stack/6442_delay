public class ExpectedCalculator {
    public static void main(String[] args) {
        Character character = new Character();

        // 第一种错误：角色有效，但按键为null。
        try {
            character.getState().handle(character, null);
        } catch (exceptions.NullKeyEventException e) {
            System.out.println("按键为null：抛出NullKeyEventException");
        }

        // 第二种错误：角色为null，但按键有效。
        try {
            character.getState().handle(null, Key.UP);
        } catch (exceptions.NullCharacterException e) {
            System.out.println("角色为null：抛出NullCharacterException");
        }

        // 想观察其他按键，就修改这个数组。
        Key[] keys = {Key.DOWN, Key.S, Key.L, Key.UP, Key.RIGHT, Key.LEFT, Key.RESET};
        for (int i = 0; i < keys.length; i++) {
            character.getState().handle(character, keys[i]);
            System.out.println(keys[i] + "之后的状态 = " + character.getState().getStateName());
        }
    }
}
