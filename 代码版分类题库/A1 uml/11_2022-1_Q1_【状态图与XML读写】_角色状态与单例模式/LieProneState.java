/** 按 Graph.pdf 实现；单例状态，未定义的按键保持原状态。 */
public final class LieProneState implements State {
    private static final LieProneState INSTANCE = new LieProneState();
    private LieProneState() {}
    public static LieProneState getInstance() { return INSTANCE; }
    @Override public void handleInput(Character character, Key event) {
        check(character,event);
        switch(event) {
            case RESET: character.setState(StandState.getInstance()); break;
            case UP: character.setState(StandState.getInstance()); break;
            case RIGHT: character.setState(CrawlState.getInstance()); break;
            case S: character.setState(ShootState.getInstance()); break;
            default: break;
        }
    }
}
