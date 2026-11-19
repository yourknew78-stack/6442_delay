/** 开放题参考答案；保留英文作答，中文注释解释思路。UID 和姓名由本人填写。 */
public class Q3Answers {
    public static final String UID = "<Your UID>";
    public static final String NAME = "<Your Name>";
    // A：同一个方法用多段条件分支选择不同付款行为。
    public static final String CODE_SMELL_NAME = "Repeated Conditional Logic";
    // B：必须指出具体方法和分支，而不只说“代码里有 if”。
    public static final String SMELL_LOCATION = "The method-based if/else chain inside PaymentProcessor.processPayment, selecting CREDIT_CARD, PAYPAL, and BANK_TRANSFER.";
    // C：新增支付方式必须修改原方法，不利于独立测试和扩展。
    public static final String SMELL_IMPACT = "PaymentProcessor knows every payment mechanism. Adding a payment method requires changing its conditional chain, increasing the chance of breaking existing methods. Gateway-specific logic and orchestration are mixed in one method, making individual payment behaviours harder to test and maintain. This structure violates the Open/Closed Principle because extensions require modifying existing processing code.";
    public static final String DESIGN_PATTERN = "Strategy";
    // E：策略接口统一入口，各支付类实现自己的算法，处理器只负责调用。
    public static final String REFACTORING_PLAN = "Create a PaymentStrategy interface with pay(double amount). Add CreditCardPayment, PayPalPayment, and BankTransferPayment classes implementing it, and move each branch's gateway operations into the corresponding pay method. Give PaymentProcessor a PaymentStrategy field supplied through its constructor. Its processPayment method prints the common messages and delegates payment to strategy.pay(amount). Select the strategy in client configuration rather than inside PaymentProcessor. Add new strategies without modifying the processor; reject unknown method names when selecting a strategy.";
}
