public class Item {
    protected String id; // UML 中 # 表示 protected。
    private String name; // UML 中 - 表示 private。
    public Item(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() { return id; }
    public String getName() { return name; }
}
