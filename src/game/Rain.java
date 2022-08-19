package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * The rain is implemented as ground so it can check the tick every turn every 10 turn might
 * rain and add the capability rained on Lake ground and thirsty dinosaur
 *
 */
public class Rain extends Ground {
    private int turns = 0;

    public Rain() {
        super('!');
    }

    /**
     * Checks every 10 turn a chance to rain
     *
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        turns ++;
        if (turns > 9){
            Random r = new Random();
            int result = r.nextInt(101-1) + 1;
            if(result < 21){
                //Go through the whole map checking each ground one by one
                for (int x = 0; x < 79; x++){
                    for (int y = 0; y < 23; y++){
                        if (location.map().at(x,y).getGround().getClass().toString().equals("class game.Lake")){
                            location.map().at(x,y).getGround().addCapability(GameCapabilty.RAINED);
                        }
                    }
                }
            }
            turns = 0;
        }

    }
}
