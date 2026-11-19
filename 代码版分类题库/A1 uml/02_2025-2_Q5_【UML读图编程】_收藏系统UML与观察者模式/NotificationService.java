public class NotificationService implements Observer {
    private String channel;
    public NotificationService(String channel) { this.channel = channel; }
    @Override
    public void update(String event, Item item) {
        System.out.println("[channel " + channel + "] event: " + event
            + ", item #" + item.getId() + " (" + item.getName() + ")");
    }
}
