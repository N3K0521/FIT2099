package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class StegosaurLocation extends Location {
    private boolean read = false;
    private NextTurn action = NextTurn.SAME;
    public int hitPoints;

    /**
     * Constructor.
     * <p>
     * Locations know which map they are part of, and where.
     *
     * @param map the map that contains this location
     * @param x   x coordinate of this location within the map
     * @param y   y coordinate of this location within the map
     */
    public StegosaurLocation(GameMap map, int x, int y) {
        super(map, x, y);
    }

    public void tick() {
        read = !read;
        if (read) {
            int bushFruit = bushFruitCount();
            boolean aliveHere = getGround().hasCapability(DinosaurStatus.ALIVE);
            boolean unconsciousHere = getGround().hasCapability(DinosaurStatus.UNCONSCIOUS);

            // Eat bush fruit
            if (aliveHere && bushFruit > 0)
                action = NextTurn.EAT;
                hitPoints += 10;

            // Drink water
            if (aliveHere && (getGround().hasCapability(GameCapabilty.RAINED)))
                action = StegosaurLocation.NextTurn.DRINK;
                Stegosaur.waterLevel += 30;

            // Become alive when rained
            if (unconsciousHere && getGround().hasCapability(GameCapabilty.RAINED))
                action = StegosaurLocation.NextTurn.DRINK;
                Stegosaur.waterLevel += 10;

            // Being unconscious
            if (unconsciousHere )
                action = NextTurn.UNCONSCIOUS;

            else
                action = NextTurn.SAME;
        }
        else {
            // become corpse when dead
            if(action == NextTurn.DIE)
                setGround(new Corpse());
            else if(action == NextTurn.EAT)
                setGround(new Floor());
        }

        super.tick();
    }

    private int bushFruitCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(Bush -> Bush.hasCapability(PlantsStatus.ALLIVE)).count();
    }

    private void setGround(Corpse corpse) {
    }

    private enum NextTurn {
        DIE, EAT, DRINK, UNCONSCIOUS, SAME
    }
}
