import java.util.List;
import java.util.Random;


public class PenguinCreator extends AnimalCreator {
    private static final List<String> NAMES = List.of("Ace", "Alaska", "Aurora", "Bam-Bam", "Banjo");
    private static int nextNameIndex = 0;

    public String getNextName() {
        String name = NAMES.get(nextNameIndex);
        nextNameIndex++;
        if (nextNameIndex == NAMES.size()) nextNameIndex = 0; // 用完最后一个，从头循环。
        return name;
    }

    public String getRandomName() {
        return NAMES.get(new Random().nextInt(NAMES.size()));
    }

    public PenguinCreator(Integer age, Integer level) {
        super(age, level); // 父类统一检查负数，并保存非 null 的配置。
        if (age == null) ageConfig = 1;
        if (level == null) levelConfig = 2;
    }

    @Override
    public Animal createAnimal(String id, String name) {
        return new Penguin(id, name, ageConfig, levelConfig); // 工厂方法创建具体动物。
    }

}
