/** 按 Graph.pdf 实现；单例状态，未定义的按键保持原状态。 */
public final class StandState implements State {
    private static final StandState INSTANCE = new StandState();
    private StandState() {}
    public static StandState getInstance() { return INSTANCE; }
    @Override public void handleInput(Character character, Key event) {
        check(character,event);
        switch(event) {
            case RESET: character.setState(StandState.getInstance()); break;
            case DOWN: character.setState(LieProneState.getInstance()); break;
            case RIGHT: character.setState(RunState.getInstance()); break;
            default: break;
        }
    }
}
