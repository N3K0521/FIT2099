package game.Items;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.PickUpItemAction;
import game.Ecopoints;

import java.util.Random;

/**
 * Class to use ripe fruit food grows on tree and bush can be fed to dinosaur
 *
 */
public class RipeFruit extends Item {
    private int age = 0;
    private int foodLevel = 20;
    private int price = 30;

    /**
     * constructor
     *
     */
    public RipeFruit() {
        super("Ripe Fruit (30)",'r',true);
    }

    /**
     * tick if 15 turns pass on the ground it rots and gets destroyed
     *
     * @param location
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        age ++;
        if (age == 15){
            location.removeItem(this);
        }
    }

    /**
     * override pick up item to get the 60% chance to pick it up
     *
     * @return an action
     */
    @Override
    public PickUpItemAction getPickUpAction() {
        Random r = new Random();
        int result = r.nextInt(101-1) + 1;
        if(result < 41){
            System.out.println("You search the tree or bush for fruit, and found one.");
            Ecopoints.addPoints(10);
            return new PickUpItemAction(this);
        }
        else{
            System.out.println("You search the tree or bush for fruit, but you can’t find any ripe ones.");
            return null;
        }
    }

    /**
     * @return
     */
    public int getFoodLevel(){
        return foodLevel;
    }

    /**
     * @return
     */
    public int getPrice(){
        return price;
    }
}
