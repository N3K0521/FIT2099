package game;

import edu.monash.fit2099.engine.*;
import game.Items.Egg;
import game.Items.LaserGun;
import game.Items.MealKit;
import game.Items.RipeFruit;

import java.util.ArrayList;
import java.util.Random;

/**
 *The vending machine to buy items using eco points
 *
 */
public class VendingMachine extends Ground {
    private ArrayList<Item> fruits = new ArrayList<Item>();

    /**
     * constructor
     *
     */
    public VendingMachine() {
        super('V');
    }

    /**
     * adds the buy action to the menu if player has enough money
     *
     * @param actor     the Actor acting
     * @param location  the current Location
     * @param direction the direction of the Ground from the Actor
     * @return
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction){
        Actions act = new Actions();
        if (Ecopoints.getPoints() > 30){
            act.add(new Buy(new RipeFruit(),30));
        }
        if (Ecopoints.getPoints() > 100){
            act.add(new Buy(new MealKit("vegetarian"),100));

        }
        if (Ecopoints.getPoints() > 200){
            act.add(new Buy(new Egg("Stegosaur"),200));
            act.add(new Buy(new Egg("Pterodactyls"),200));
        }
        if (Ecopoints.getPoints() > 500){
            act.add(new Buy(new MealKit("Carnivore"),500));
            act.add(new Buy(new Egg("Brachiosaur"),500));
            act.add(new Buy(new LaserGun(),500));
        }
        if (Ecopoints.getPoints() > 1000){
            act.add(new Buy(new Egg("Allosaur"),1000));
        }

        return act;
    }
}
