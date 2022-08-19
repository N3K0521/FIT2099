package game.Items;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Ecopoints;
import game.Stegosaur;

/**
 *
 * The fish item appears on lakes
 */
public class Fish extends Item {
    private int foodLevel = 10;
    private int price;
    private int age;
    private String type;

    public Fish (){
        super("Fish", 'f',true);
    }
}
