public class ExpectedCalculator {
    public static void main(String[] args) {
        // 参数顺序：年龄、职业、是否糖尿病、是否心脏病。
        printPrice(41, null, true, true);
        printPrice(41, "", false, false);
        printPrice(41, "policeman", false, true);
        printPrice(41, "policeman", true, false);
        printPrice(40, "policeman", false, false);
        printPrice(41, "teacher", false, false);

        // 再看save：第一次成功，再保存同一人会抛异常。
        PersonRepository repository = new PersonRepository();
        repository.setSequenceGenerator(new SequenceGenerator());
        Person person = new Person("Ada", 30, "teacher", false, false);
        Person savedPerson = repository.save(person);
        System.out.println("save返回的是原对象吗？" + (savedPerson == person));
        System.out.println("保存后id = " + person.getId());

        try {
            repository.save(person);
        } catch (RuntimeException e) {
            System.out.println("再次保存：抛出RuntimeException");
        }
    }

    public static void printPrice(int age, String occupation, boolean diabetes, boolean heartDisease) {
        // Person构造器会自动调用calcInsurancePrice。
        Person person = new Person("Ada", age, occupation, diabetes, heartDisease);
        int expected = person.getHealthInsurancePrice();
        System.out.println("年龄=" + age + "，职业=" + occupation
                + "，糖尿病=" + diabetes + "，心脏病=" + heartDisease);
        System.out.println("expected = " + expected);
    }
}
