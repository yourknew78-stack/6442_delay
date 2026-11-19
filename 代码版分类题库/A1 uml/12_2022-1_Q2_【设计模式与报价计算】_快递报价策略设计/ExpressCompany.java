import java.util.LinkedList;
import java.util.List;

public abstract class ExpressCompany implements ParcelHandler {

	protected final Point location;
	protected final int deliveryRadius;
	protected final List<ItemType> illegalItems = new LinkedList<>();

	public ExpressCompany(Point location, int deliveryRadius, List<ItemType> illegalItems) {
		this.location = location;
		this.deliveryRadius = deliveryRadius;
		this.illegalItems.addAll(illegalItems);
	}

	public double handle(Parcel parcel) throws IllegalParcelItemException {
		safetyCheckOnItems(parcel);

		return calculateQuote(parcel);
	}

	@Override
	public void safetyCheckOnItems(Parcel parcel) throws IllegalParcelItemException {
        for (ItemType item : parcel.getItems()) {
            if (illegalItems.contains(item)) throw new IllegalParcelItemException();
        }
    }

	public int calculateDistance(Parcel parcel) {
        double dx = (double) location.x - parcel.getDestination().x;
        double dy = (double) location.y - parcel.getDestination().y;
        return (int) Math.ceil(Math.sqrt(dx * dx + dy * dy)); // 3.1 按 4 收费。
    }

	public abstract double calculateQuote(Parcel parcel);
}
