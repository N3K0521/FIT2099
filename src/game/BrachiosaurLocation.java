package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

import java.util.Random;


public class BrachiosaurLocation extends Location {
    private boolean read = false;
    private NextTurn action = NextTurn.SAME;
    public int hitPoints;

    /**
     * Constructor.
     * <p>
     * Locations know which map they are part of, and where.
     * @param map the map that contains this location
     * @param x   x coordinate of this location within the map
     * @param y   y coordinate of this location within the map
     * @param hitPoints foodlevel of Brachiosaur
     */
    public BrachiosaurLocation(GameMap map, int x, int y, int hitPoints) {
        super(map, x, y);
        this.hitPoints = hitPoints;
    }

    public void tick() {
        read = !read;
        if (read) {
            int treeFruit = treeFruitCount();
            int bush = bushCount();
            boolean aliveHere = getGround().hasCapability(DinosaurStatus.ALIVE);
            boolean unconsciousHere = getGround().hasCapability(DinosaurStatus.UNCONSCIOUS);

            // eat tree fruit will gain 5 food level
            if (aliveHere && treeFruit > 0) {
                action = NextTurn.EAT;
                hitPoints += 5;
            }

            // 50% to kill a bush
            if (aliveHere && bush > 0){
                Random rand = new Random();
                if (rand.nextInt(50) == 0) {
                    action = NextTurn.ATTACK;
                }
            }

            // Drink water
            if (aliveHere && (getGround().hasCapability(GameCapabilty.RAINED)))
                action = BrachiosaurLocation.NextTurn.DRINK;
                Brachiosaur.waterLevel += 30;

            // Become alive when rained
            if (unconsciousHere && getGround().hasCapability(GameCapabilty.RAINED))
                action = BrachiosaurLocation.NextTurn.DRINK;
                Brachiosaur.waterLevel += 10;

                // being unconscious
            if (unconsciousHere) {
                action = NextTurn.UNCONSCIOUS;
            }

            else
                action = BrachiosaurLocation.NextTurn.SAME;
        }
        else {
            // become corpse after died
            if(action == BrachiosaurLocation.NextTurn.DIE)
                setGround(new Corpse());
            // new floor after the fruit on the floor was eaten
            else if(action == BrachiosaurLocation.NextTurn.EAT)
                setGround(new Floor());
        }

        super.tick();
    }

    private int bushCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(Bush -> Bush.hasCapability(PlantsStatus.RIPE)).count();
    }

    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private int treeFruitCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(RipeFruit -> RipeFruit.hasCapability(PlantsStatus.RIPE)).count();
    }

    private void setGround(Corpse corpse) {
    }

    private enum NextTurn {
        DIE, EAT, DRINK, UNCONSCIOUS, ATTACK, SAME
    }
}
