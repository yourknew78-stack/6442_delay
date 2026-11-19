import java.util.List;

public class CompanyB extends ExpressCompany {

	public CompanyB(Point location, int deliveryRadius, List<ItemType> illegalItems) {
		super(location, deliveryRadius, illegalItems);
	}

	@Override
	public double calculateQuote(Parcel parcel) {
        int weight = parcel.getWeight();
        double quote = 46; // 不超过30kg固定46，不是每公斤46。
        if (weight > 60) quote += 10 * 1.5 + 10 * 2.1 + 10 * 2.9 + (weight - 60) * 3.6;
        else if (weight > 50) quote += 10 * 1.5 + 10 * 2.1 + (weight - 50) * 2.9;
        else if (weight > 40) quote += 10 * 1.5 + (weight - 40) * 2.1;
        else if (weight > 30) quote += (weight - 30) * 1.5;
        int distance = calculateDistance(parcel);
        if (distance > deliveryRadius) quote += distance * 1.1;
        return quote;
    }
}
