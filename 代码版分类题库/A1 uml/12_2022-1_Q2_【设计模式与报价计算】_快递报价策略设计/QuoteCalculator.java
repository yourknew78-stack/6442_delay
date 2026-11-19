public class QuoteCalculator {

	public static Quote bestQuote(Parcel parcel, ExpressCompany a, ExpressCompany b) throws IllegalParcelItemException {
        double quoteA = a.handle(parcel); // handle 先安检，再计算报价。
        double quoteB = b.handle(parcel);
        if (quoteA <= quoteB) return new Quote(a, quoteA); // 相同价格选 A。
        return new Quote(b, quoteB);
    }
}
