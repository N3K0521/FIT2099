package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action to quit the game manually by player
 *
 *
 */
public class Quit extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return "Game ended by player";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "End game";
    }
}
