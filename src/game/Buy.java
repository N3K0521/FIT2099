package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * Action to buy an item from vending machine
 *
 */
public class Buy extends Action {
    private Item item;
    private int price;

    /**
     * Constructor
     *
     * @param item
     * @param price
     */
    public Buy(Item item, int price){
        this.item = item;
        this.price = price;
    }

    /**
     * executes the action
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return the menu desc
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Ecopoints.reducePoints(price);
        actor.addItemToInventory(item);
        return menuDescription(actor);
    }

    /**
     * the desc in the menu
     *
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " Buy up the " + item.toString();
    }


}
