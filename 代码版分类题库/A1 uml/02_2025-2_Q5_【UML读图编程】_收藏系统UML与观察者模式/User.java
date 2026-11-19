public class User {
    protected String id;
    private String name;
    private FavouriteList favourites;
    public User(String id, String name) {
        this.id = id;
        this.name = name;
        favourites = new FavouriteList(); // 每个用户拥有自己的收藏列表。
    }
    public FavouriteList getFavourites() { return favourites; }
    public void setFavourites(FavouriteList f) { favourites = f; }
    public String getName() { return name; }
}
