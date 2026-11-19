import java.util.*;

public class Kitchen {
    List<Recipe> orders;
    Map<Food, Integer> availableFood;

    public Kitchen(List<Recipe> orders, Map<Food, Integer> availableFood) {
        this.orders = orders;
        this.availableFood = availableFood;
    }

    /**
     * Check whether we have the sufficient quantity of food for a recipe.
     *
     * Verifies the availability of sufficient number of available (raw or ready)
     * food as required in the recipe.
     *
     * @return true if there are enough available food to prepare the recipe, and false otherwise.
     */
    public static boolean readyToCook(Recipe recipe, Set<Food> ava) {
        for (Ingredient ingredient : recipe.ingredients.keySet()) {
            int count = 0;
            for (Food food : ava) {
                if (food.ingredient == ingredient && !(food.state instanceof BurntState)) count++;
            }
            if (count < recipe.ingredients.get(ingredient)) return false;
        }
        return true; // 每一种食材数量都够，才能完成整份食谱。
    }

    /**
     * Check whether we have the sufficient food to complete a recipe.
     *
     * Verifies the availability of sufficient number of food in the ReadyState as required in the recipe.
     *
     * @return true if there are enough available food to complete the recipe, and false otherwise.
     */
    public static boolean readyToServe(Recipe recipe, Set<Food> ava) {
        for (Ingredient ingredient : recipe.ingredients.keySet()) {
            int count = 0;
            for (Food food : ava) {
                if (food.ingredient == ingredient && food.state instanceof ReadyState) count++;
            }
            if (count < recipe.ingredients.get(ingredient)) return false;
        }
        return true; // 每一种食材数量都够，才能完成整份食谱。
    }

    /**
     * Determines the additional ingredients required to complete all orders.
     *
     * Note that burnt food in `availableFood` can not be considered available.
     *
     * Example:
     *      If we need to complete a Wagyu Udon (the recipe requires 3 wagyu and 1 udon) and a biscuit
     *      (requiring 1 biscuit) while we only have one unit of biscuit available,
     *      we will need **3 WAGYUs** and **1 UDON** to complete the orders.
     *
     * @return A map where the key is the ingredient item, and the
     *         value is the additional quantity needed to fulfill all orders.
     *         The map shall contain only the ingredient items that are insufficient.
     */
    public Map<Ingredient, Integer> ingredientsRequired() {
        Map<Ingredient, Integer> missing = new HashMap<Ingredient, Integer>();
        for (Recipe recipe : orders) {
            for (Ingredient ingredient : recipe.ingredients.keySet()) {
                int old = 0;
                if (missing.containsKey(ingredient)) old = missing.get(ingredient);
                missing.put(ingredient, old + recipe.ingredients.get(ingredient));
            }
        }
        // 先汇总全部订单需求，再扣掉可用库存；烧焦的食物不能扣。
        for (Food food : availableFood.keySet()) {
            if (!(food.state instanceof BurntState) && missing.containsKey(food.ingredient)) {
                int amount = missing.get(food.ingredient) - availableFood.get(food);
                if (amount <= 0) missing.remove(food.ingredient);
                else missing.put(food.ingredient, amount);
            }
        }
        return missing;
    }

}
