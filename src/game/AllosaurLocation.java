package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class AllosaurLocation extends Location {
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
    public AllosaurLocation(GameMap map, int x, int y) {
        super(map, x, y);
    }

    public void tick() {
        read = !read;
        if (read) {
            int Corpse = CorpseCount(); // in this case, only referring to dead allosaurs or stegosaurs
            int deadBrachiosaur = deadBrachiosaurCount();
            int aliveStegosaur = aliveStegosaurCount();
            int egg = eggCount();
            boolean aliveHere = getGround().hasCapability(DinosaurStatus.ALIVE);
            boolean unconsciousHere = getGround().hasCapability(DinosaurStatus.UNCONSCIOUS);

            // eat dead dinosaurs increase food level by 50
            if (aliveHere && (Corpse > 0))
                action = NextTurn.EAT;
                hitPoints += 50;

            // eat egg increase food level by 10
            if (aliveHere && (egg > 0))
                action = NextTurn.EAT;
                hitPoints += 10;

            // attack alive stegosaur increase food level by 20
            if (aliveHere && (aliveStegosaur > 0))
                action = NextTurn.ATTACK;
                hitPoints += 20;

            // eat dead Brachiosaur will fill its maximum food level
            if (aliveHere && (deadBrachiosaur > 0))
                action = NextTurn.EAT;
                hitPoints = 100;

            // Drink water
            if (aliveHere && (getGround().hasCapability(GameCapabilty.RAINED)))
                action = AllosaurLocation.NextTurn.DRINK;
                Allosaur.waterLevel += 30;

            // Become alive when rained
            if (unconsciousHere && getGround().hasCapability(GameCapabilty.RAINED))
                action = AllosaurLocation.NextTurn.DRINK;
                Allosaur.waterLevel += 10;

            // unconscious
            if (unconsciousHere )
                action = NextTurn.UNCONSCIOUS;

            else
                action = NextTurn.SAME;
        }
        else {
            if(action == NextTurn.DIE)
                setGround(new Corpse());
            else if(action == NextTurn.EAT)
                setGround(new Floor());
        }

        super.tick();
    }

    private int deadBrachiosaurCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(Brachiosaur -> Brachiosaur.hasCapability(DinosaurStatus.DEAD)).count();
    }

    private int eggCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasCapability(DinosaurStatus.EGG)).count();
    }

    private int CorpseCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasCapability(DinosaurStatus.DEAD)).count();
    }

    private int aliveStegosaurCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(Allosaur -> Allosaur.hasCapability(DinosaurStatus.ALIVE)).count();
    }

    private void setGround(Corpse corpse) {
    }

    private enum NextTurn {
        DIE, EAT, DRINK, UNCONSCIOUS, ATTACK, SAME
    }
}
