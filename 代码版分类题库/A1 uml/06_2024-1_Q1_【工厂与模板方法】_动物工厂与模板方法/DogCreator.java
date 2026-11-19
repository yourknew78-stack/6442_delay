import java.util.List;
import java.util.Random;


public class DogCreator extends AnimalCreator {
    public static final List<String> NAMES = List.of("Rex", "Buddy", "Max", "Rocky");
    private int nextNameIndex = 0;

    /**
     *  See README.
     *  Hint: you may use `nextNameIndex`
     */
    public String getNextName() {
        String name = NAMES.get(nextNameIndex);
        nextNameIndex++;
        if (nextNameIndex == NAMES.size()) nextNameIndex = 0; // 用完最后一个，从头循环。
        return name;
    }

    public String getRandomName() {
        return NAMES.get(new Random().nextInt(NAMES.size()));
    }

    public DogCreator(Integer age, Integer level) {
        super(age, level); // 父类统一检查负数，并保存非 null 的配置。
    }

    @Override
    public Animal createAnimal(String id, String name) {
        return new Dog(id, name, ageConfig, levelConfig); // 工厂方法创建具体动物。
    }

}

