public class Main {
    public static void main(String[] args) {
        User user1 = new User("1", "User 1");
        User user2 = new User("2", "User 2");
        Observer abc = new NotificationService("ABC");
        Observer def = new NotificationService("DEF");
        user1.getFavourites().attach(abc);
        user2.getFavourites().attach(def);
        Item item1 = new Item("I1", "Item 1");
        Item item2 = new Item("I2", "Item 2A");
        Item item3 = new Item("I2", "Item 2B");
        user1.getFavourites().addItem(item1);
        user2.getFavourites().addItem(item1);
        user2.getFavourites().addItem(item2);
        user2.getFavourites().addItem(item3); // 与 item2 的 id 相同，不重复添加或通知。
        user2.getFavourites().detach(def);
        user1.getFavourites().removeItem(item1); // 仍会通知 ABC。
        user2.getFavourites().removeItem(item1); // 已取消订阅，不再通知 DEF。
    }
}
