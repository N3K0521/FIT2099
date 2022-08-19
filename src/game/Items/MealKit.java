package game.Items;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * The meal kit for dinos will fully heal the dinosaur
 *
 */
public class MealKit extends Item {
    private int foodLevel;
    private int price;

    /**
     * Set the meal kit to be either a carnivore meal kit or a vegetarian meal kit
     *
     * @param type
     */
    public MealKit(String type) {
        super("MealKit",'m',true);
        if (type.equals("Carnivore")){
            price = 500;
            foodLevel = 100;
            this.name = "carnivore meal kit (500)";
        }
        else if (type.equals("vegetarian")){
            price = 100;
            foodLevel = 200;
            this.name = "vegetarian meal kit (100)";
        }
    }

    /**
     * get the food level how many it will feed the dino with
     *
     * @return
     */
    public int getFoodLevel(){
        return foodLevel;
    }

    /**
     * get the price of the meal kit
     *
     * @return
     */
    public int getPrice(){
        return price;
    }

}
