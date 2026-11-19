/** 按 Graph.pdf 实现；单例状态，未定义的按键保持原状态。 */
public final class RunState implements State {
    private static final RunState INSTANCE = new RunState();
    private RunState() {}
    public static RunState getInstance() { return INSTANCE; }
    @Override public void handleInput(Character character, Key event) {
        check(character,event);
        switch(event) {
            case RESET: character.setState(StandState.getInstance()); break;
            case LEFT: character.setState(StandState.getInstance()); break;
            default: break;
        }
    }
}
