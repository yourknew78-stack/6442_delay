import java.util.*;

public abstract class AnimalCreator {
    int ageConfig = 0;  // default `ageConfig` and `levelConfig`
    int levelConfig = 0;

    public AnimalCreator(Integer age, Integer level) {
        if (age != null && age < 0) throw new IllegalArgumentException("年龄不能为负数");
        if (level != null && level < 0) throw new IllegalArgumentException("活动时长不能为负数");
        if (age != null) ageConfig = age;
        if (level != null) levelConfig = level; // null 保留字段原有默认值。
    }

    public abstract Animal createAnimal(String id, String name);

    public abstract String getNextName();

    /**
     * A template method used to create the ID, Name of an animal,
     * then returns the Animal created using these information.
     * <p>
     * 1) Create the ID using the string representation of `randomUUID()` from `UUID`.
     *    (Reference: https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html#randomUUID--)
     * 2) Get the next name for the animal.
     *    (Use the appropriate helper method which should be implemented in the subclasses)
     * 3) Create the animal, passing the ID and Name
     *    (Hint: `createAnimal()` should be implemented in the subclasses)
     */
    public Animal createAnimalTemplate() {
        String id = UUID.randomUUID().toString();
        String name = getNextName();
        return createAnimal(id, name); // 模板固定流程，具体动物由子类决定。
    }

    /**
     * Return a list of `number` animals.
     * @param number: number of animals to be created.
     */
    public List<Animal> createBatch(int number) {
        List<Animal> animals = new ArrayList<Animal>();
        for (int i = 0; i < number; i++) animals.add(createAnimalTemplate());
        return animals;
    }

}
