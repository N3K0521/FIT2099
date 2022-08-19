package game;

import edu.monash.fit2099.demo.mars.KickAction;
import edu.monash.fit2099.engine.*;

import javax.lang.model.type.ExecutableType;

public class PterodactylsLocation extends Location {
    private boolean read = false;
    private NextTurn action = NextTurn.SAME;
    public int hitPoints;
    public Item Fish;

    /**
     * Constructor.
     * <p>
     * Locations know which map they are part of, and where.
     *
     * @param map the map that contains this location
     * @param x   x coordinate of this location within the map
     * @param y   y coordinate of this location within the map
     */

    public PterodactylsLocation(GameMap map, int x, int y) {
        super(map, x, y);
    }

    public void tick() {
        read = !read;
        if (read) {
            boolean fishAvailable = getItems().contains(Fish);
            boolean CorpseHere = getGround().hasCapability(DinosaurStatus.DEAD);
            boolean aliveHere = getGround().hasCapability(DinosaurStatus.ALIVE);
            boolean unconsciousHere = getGround().hasCapability(DinosaurStatus.UNCONSCIOUS);

            // Eat fish and corpse
            if (aliveHere && (fishAvailable ||CorpseHere))
                action = PterodactylsLocation.NextTurn.EAT;
                hitPoints += 10;

            // Drink water
            if (aliveHere && (getGround().hasCapability(GameCapabilty.RAINED)))
                action = PterodactylsLocation.NextTurn.DRINK;
                Pterodactyls.waterLevel += 30;

            // Become alive when rained
            if (unconsciousHere && getGround().hasCapability(GameCapabilty.RAINED))
                action = PterodactylsLocation.NextTurn.DRINK;
                Pterodactyls.waterLevel += 10;

            // Being unconscious
            if (unconsciousHere)
                action = PterodactylsLocation.NextTurn.UNCONSCIOUS;

            else
                action = PterodactylsLocation.NextTurn.SAME;
        }
        else {
            // become corpse when dead
            if(action == PterodactylsLocation.NextTurn.DIE)
                setGround(new Corpse());
            else if(action == PterodactylsLocation.NextTurn.EAT)
                setGround(new Floor());
        }

        super.tick();
    }

    private void setGround(Corpse corpse) {
    }

    private enum NextTurn {
        DIE, EAT, DRINK, UNCONSCIOUS, SAME
    }
}
