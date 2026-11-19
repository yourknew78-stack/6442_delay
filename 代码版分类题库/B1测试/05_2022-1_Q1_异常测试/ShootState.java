/** 按 Graph.pdf 实现；单例状态，未定义的按键保持原状态。 */
public final class ShootState implements State {
    private static final ShootState INSTANCE = new ShootState();
    private ShootState() {}
    public static ShootState getInstance() { return INSTANCE; }
    @Override public void handleInput(Character character, Key event) {
        check(character,event);
        switch(event) {
            case RESET: character.setState(StandState.getInstance()); break;
            case L: character.setState(LieProneState.getInstance()); break;
            default: break;
        }
    }
}
