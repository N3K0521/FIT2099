package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.Items.RipeFruit;

import java.util.Random;

/**
 * The bush class will grow ripe fruits
 *
 */
public class Bush extends Ground {
    /**
     * the constructor
     *
     */
    public Bush() {
        super('W');
    }

    /**
     * Every turn has a chance to grow a ripe fruit
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        Random r = new Random();
        int result = r.nextInt(101-1) + 1;
        if (result < 11){
            location.addItem(new RipeFruit());
        }
    }
}
