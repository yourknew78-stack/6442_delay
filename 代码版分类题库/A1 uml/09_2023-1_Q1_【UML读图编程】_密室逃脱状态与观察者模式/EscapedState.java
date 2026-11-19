package Q1;
public class EscapedState implements ParticipantState {
    @Override public ParticipantState solveRiddle(String input,Riddle[] riddles,int which) throws RiddleException {
        throw new RiddleException("Participant has already escaped");
    }
}
