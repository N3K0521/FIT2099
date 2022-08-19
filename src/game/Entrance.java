package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Item;

import java.util.List;

/**
 * An item that acts as a teleporter to the other park it cant be picked up so it wont dissapear
 *
 */
public class Entrance extends Item {
    /***
     * Constructor portable is false so user cant pick it up
     *
     *
     */
    public Entrance() {
        super("Other park entrance",'=',false);
    }

    /**
     * Allows you to add the move action it's not put here because it's easier to do it from the application menu
     *
     * @param action
     */
    public void addAction(Action action) {
        this.allowableActions.add(action);
    }
}
