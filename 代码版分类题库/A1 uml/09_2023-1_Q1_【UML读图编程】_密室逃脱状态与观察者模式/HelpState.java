package Q1;
public class HelpState implements ParticipantState {
    @Override
    public ParticipantState solveRiddle(String input, Riddle[] riddleList, int whichRiddle) throws RiddleException {
        if (riddleList[whichRiddle].isSolved()) {
            throw new RiddleException("Riddle already solved");
        }
        riddleList[whichRiddle].setSolved(); // 求助状态直接解开这一题。
        for (Riddle riddle : riddleList) {
            if (!riddle.isSolved()) return new InProgressState(); // 还有未解题，继续游戏。
        }
        return new EscapedState(); // 全部解开，切换到逃脱状态。
    }
}
