package Q1;
import java.util.HashSet;
import java.util.Set;

public class GameMaster implements Observer {
    // Set 保存曾经求助、曾经逃脱的参与者，同一个人重复通知也不会重复保存。
    private final Set<Participant> helped = new HashSet<Participant>();
    private final Set<Participant> escaped = new HashSet<Participant>();
    @Override public void updateHelp(Participant p) { helped.add(p); }
    @Override public void updateEscape(Participant p) { escaped.add(p); }
    @Override public boolean hasAskedForHelp(Participant p) { return helped.contains(p); }
    @Override public boolean hasEscaped(Participant p) { return escaped.contains(p); }
}
