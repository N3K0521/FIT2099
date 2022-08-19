package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class Corpse extends Item {
    private int age = 0;
    private int foodLevel = 200;
    private int price = 30;

    /**
     * constructor
     *
     */
    public Corpse() {
        super("Corpse",'C',false);
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
