import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavouriteList {
    private List<Item> items = new ArrayList<Item>();
    private List<Observer> observers = new ArrayList<Observer>();

    public void addItem(Item item) {
        if (item == null) return;
        for (Item existing : items) {
            if (Objects.equals(existing.getId(), item.getId())) return; // 添加只比较 id。
        }
        items.add(item);
        notify("added", item); // 真正发生变化才通知。
    }
    public void removeItem(Item item) {
        if (item == null) return;
        for (int i = 0; i < items.size(); i++) {
            Item existing = items.get(i);
            if (Objects.equals(existing.getId(), item.getId())
                    && Objects.equals(existing.getName(), item.getName())) {
                items.remove(i); // 删除必须同时匹配 id 和 name。
                notify("removed", item);
                return;
            }
        }
    }
    public void attach(Observer o) {
        if (o != null && !observers.contains(o)) observers.add(o);
    }
    public void detach(Observer o) { observers.remove(o); }
    public void notify(String event, Item item) {
        for (Observer observer : observers) observer.update(event, item);
    }
}
