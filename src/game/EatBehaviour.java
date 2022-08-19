package game;

import com.sun.source.tree.ReturnTree;
import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.Items.RipeFruit;

public class EatBehaviour extends Action implements Behaviour{
    private Actor target;

    public EatBehaviour(Actor subject) {
        this.target = subject;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return actor + " has eaten the " + target + ".";
    }

    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location here = map.locationOf(actor);
        Location there = map.locationOf(target);

        NumberRange xs, ys;
        if (here.x() == there.x() || here.y() == there.y()) {
            xs = new NumberRange(Math.min(here.x(), there.x()), Math.abs(here.x() - there.x()) + 1);
            ys = new NumberRange(Math.min(here.y(), there.y()), Math.abs(here.y() - there.y()) + 1);

            for (int x : xs) {
                for (int y : ys) {
                    if(map.at(x, y).getGround().hasCapability(PlantsStatus.RIPE) && actor.hasCapability(DinosaurStatus.HUNGRY))
                        return this;
                    else if(map.at(x, y).getGround().hasCapability(DinosaurStatus.DEAD) && actor.hasCapability(DinosaurStatus.HUNGRY))
                        return this;
                }
            }
            return null;
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "";
    }

    /**
     * Compute the Manhattan distance between two locations.
     *
     * @param a the first location
     * @param b the first location
     * @return the number of steps between a and b if you only move in the four cardinal directions.
     */
    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}