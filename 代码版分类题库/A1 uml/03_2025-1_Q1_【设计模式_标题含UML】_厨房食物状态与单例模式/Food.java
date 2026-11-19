/** A class that represent ingredients that have been prepared (cooked) or are to be prepared. */
public class Food {
    Ingredient ingredient;
    FoodState state;
    int elapsedPrepTime;

    public Food(Ingredient ingredient, FoodState state, int elapsedPrepTime) {
        this.ingredient = ingredient;
        this.state = state;
        this.elapsedPrepTime = elapsedPrepTime;
    }

    public Food(Ingredient ingredient) {
        this(ingredient, RawState.getState(), 0);
    }


    /**
     * Check and fix the state of the food, then
     * prepare the food.
     *
     * Update the food by increasing the prep-time done,
     * and update state if there is a change in the state.
     */
    public void prepare() {
        checkState(); // 时间是真实依据，先纠正可能不一致的旧状态。
        state.prepare(this); // this 就是当前这份 Food，由状态对象处理。
    }


    /** check and update state if needed */
    public void checkState() {
        int required = Ingredient.getPrepTime(ingredient);
        if (elapsedPrepTime < required) state = RawState.getState();
        else if (elapsedPrepTime <= required + 1) state = ReadyState.getState();
        else state = BurntState.getState(); // 多准备两单位时间才烧焦。
    }


    public void setState(FoodState state) {
        this.state = state;
    }

    public void setPrepTimeDone(int elapsedPrepTime) {
        this.elapsedPrepTime = elapsedPrepTime;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

}
