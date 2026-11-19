import java.util.*;


public class Pricing {
    int unitPrice;
    Integer quantity;
    String memberId;

    public Pricing(int unitPrice, Integer quantity, String memberId) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.memberId = memberId;
    }


    /**
     * Calculate the price of a mysterious object under a mysterious pricing scheme.
     */
    public static Integer calculatePrice(int unitPrice, Integer quantity, String memberId) {
        double discount = 0.0;

        if (quantity == null) {
            discount -= 0.2;
            quantity = 1;

            if ((memberId == null || memberId.trim().isEmpty())) {
                discount -= 0.1;
            }
        }

        if (quantity <= 3) {
            return unitPrice * 4;
        }
        else if (quantity >= 5) {
            if (List.of("id1", "id2", "id3").contains(memberId) && unitPrice >= 3) {
                return 10;
            } else if (quantity / 6 >= 1) {
                quantity -= quantity / 6;
            }
        }
        
        return (int) (unitPrice * quantity * (1 - Math.min(discount, 0)));
    }

public static void main(String[] args) {
    // 参数顺序：单价 unitPrice、数量 quantity、会员编号 memberId
    System.out.println("第1组 expected = " + calculatePrice(1, null, null));
    System.out.println("第2组 expected = " + calculatePrice(1, null, "a"));
    System.out.println("第3组 expected = " + calculatePrice(4, 6, "id1"));
    System.out.println("第4组 expected = " + calculatePrice(1, 7, "a"));
    System.out.println("第5组 expected = " + calculatePrice(1, 5, "a"));
    System.out.println("第6组 expected = " + calculatePrice(1, 4, "a"));
}
}

/*
 
if                                                                       true                false

if (quantity == null)                                                     12                   3

if ((memberId == null || memberId.trim().isEmpty()))                      1                    2

if (quantity <= 3)                                                        12                   123                  

else if (quantity >= 5)                                                   34                   6

if (List.of("id1", "id2", "id3").contains(memberId) && unitPrice >= 3)    3                    45

else if (quantity / 6 >= 1)                                               4                    5
                                                                         


                                                                expected
1.quantity = null    memberid =null         unitprice=1         4
2.quantity = null    memberid ='a'          unitprice=1         4 
3.quantity=6         memberid= 'id1'        unitPrice=4         10
4.quantity=7         memberid='a'           unitprice=1         6
5.quantity=5.        memberid='a'           unitprice=1         5
6.quantity =4.       memberid='a'           unitprice=1         4



1.先找最深的 分别满足t 和f 与此同时看看满足了其他的什么
2.根据写了的用例 判断其他的
2.根据还没填到的 看看能不能通过增加之前的用例属性 不行的话就加、
3.消除所有 x
4.注意 elif会被短路掉
*/
