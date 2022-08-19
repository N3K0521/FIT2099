package game;

import edu.monash.fit2099.engine.*;
import game.Items.Fish;

import java.util.ArrayList;
import java.util.Random;

/**
 * The lake class where dinos can drink water and eat fish ground dinos can't enter
 *
 */
public class Lake extends Ground {
    private int water;
    private ArrayList<Item> fishs = new ArrayList<Item>();

    /**
     * A constructor adds five fish and 25 water to start with
     *
     */
    public Lake() {
        super('~');
        water = 25;
        for (int i = 0; i < 5; i++){
            fishs.add(new Fish());
        }
    }

    /**
     * overrides tick might add a fish every turn if it has capability rained from the Rain class adds water
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        Random r = new Random();
        int result = r.nextInt(101-1) + 1;
        if (result > 40 && fishs.size() < 25){
            fishs.add(new Fish());
        }
        if (location.getGround().hasCapability(GameCapabilty.RAINED)){
            Random random = new Random();
            float a = (random.nextInt(6 - 1) + 1)/10;
            water = (int) (water + a*20);
            if (water > 25){
                water = 25;
            location.getGround().removeCapability(GameCapabilty.RAINED);
            }
        }
    }

    /**
     * @param actor the Actor to check
     * @return false if its a ground dinosaur and true if its a flying ptero or the player
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        if (actor.getClass().toString().equals("class game.Stegosaur") || actor.getClass().toString().equals("class game.Brachiosaur") || actor.getClass().toString().equals("class game.Allosaur")){
            return false;
        }
        return true;
    }
}

