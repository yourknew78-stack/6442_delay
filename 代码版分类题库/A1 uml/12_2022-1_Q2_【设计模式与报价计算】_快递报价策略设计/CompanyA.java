import java.util.List;

public class CompanyA extends ExpressCompany {

	public CompanyA(Point location, int deliveryRadius, List<ItemType> illegalItems) {
		super(location, deliveryRadius, illegalItems);
	}

	@Override
	public double calculateQuote(Parcel parcel) {
        int weight = parcel.getWeight();
        double quote;
        // 分段累加：23kg 是前20kg按1.3、剩下3kg按1.7。
        if (weight <= 20) quote = weight * 1.3;
        else if (weight <= 40) quote = 20 * 1.3 + (weight - 20) * 1.7;
        else if (weight <= 60) quote = 20 * 1.3 + 20 * 1.7 + (weight - 40) * 2.4;
        else quote = 20 * 1.3 + 20 * 1.7 + 20 * 2.4 + (weight - 60) * 3.2;
        int distance = calculateDistance(parcel);
        if (distance > deliveryRadius) quote += distance; // 超半径后按完整距离加费。
        return quote;
    }
}
