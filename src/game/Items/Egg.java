package game.Items;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.*;

/**
 *The class for dino eggs all three of the dino eggs  are from this class
 *
 */
public class Egg extends Item {
    private int foodLevel = 10;
    private int price;
    private int age;
    private String type;

    /**
     * Constructor ask for a string of the dino type then set the eggs property to be equal to the dino chosen
     *
     * @param dino
     */
    public Egg (String dino){
        super("Egg",'0',true);
        if (dino.equals("Stegosaur")){
            price = 200;
            type = "1";
            super.name = "Stegosaur egg (200)";
        }
        else if (dino.equals("Brachiosaur")){
            price = 500;
            type = "2";
            super.name = "Brachiosaur egg (500)";
        }
        else if (dino.equals("Allosaur")){
            price = 1000;
            type = "3";
            super.name = "Allosaur egg (1000)";
        }
        else if (dino.equals("Pterodactyls")){
            price = 1000;
            type = "4";
            super.name = "Pterodactyls egg (200)";
        }

    }

    /**
     * if left on the ground for 15 turns and doesn't have an actor on the ground its in will hatch to a baby version of it
     *
     * @param location
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        age ++;
        if (age == 15 && location.containsAnActor() == false){
            location.removeItem(this);
            if (type.equals("1")){
                location.addActor(new Stegosaur(""));
                Ecopoints.addPoints(100);
            }
            else if (type.equals("2")){
                location.addActor(new Brachiosaur(""));
                Ecopoints.addPoints(1000);
            }
            else if (type.equals("3")){
                location.addActor(new Allosaur(""));
                Ecopoints.addPoints(1000);
            }
            else if (type.equals("4")){
                location.addActor(new Pterodactyls("",location));
                Ecopoints.addPoints(100);
            }
        }
    }
}
