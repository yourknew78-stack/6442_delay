// UML 的 interface 用 Java interface；观察者约定统一的通知入口。
public interface Observer {
    void update(String event, Item item);
}
