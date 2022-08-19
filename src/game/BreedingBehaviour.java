package game;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

public class BreedingBehaviour implements Behaviour{
    private Random random = new Random();
    /**
     * Allows dino to breed
     * Returns a MoveAction to wander to get nearer to the other dino
     * If no movement is possible, returns null.
     *
     * @param actor the Actor enacting the behaviour
     * @param map the map that actor is currently on
     * @return an Action, or null if no MoveAction is possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        for (Exit x :map.locationOf(actor).getExits()){
            if (x.getDestination().containsAnActor()){
                if (x.getDestination().getActor().getClass().toString().equals(actor.getClass().toString())){
                    if (actor.hasCapability(GameCapabilty.MALE) && x.getDestination().getActor().hasCapability(GameCapabilty.FEMALE)){
                        x.getDestination().getActor().addCapability(GameCapabilty.PREGNANT);
                        System.out.println("a dinosaur Succesfully mated");
                        return null;
                    }
                    else if (actor.hasCapability(GameCapabilty.FEMALE) && x.getDestination().getActor().hasCapability(GameCapabilty.MALE)){
                        actor.addCapability(GameCapabilty.PREGNANT);
                        System.out.println("a dinosaur Succesfully mated");
                        return null;
                    }
                }
            }
        }


        Location here = map.locationOf(actor);
        Location there = search(actor,map);
        int currentDistance = distance(here, there);
        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
                int newDistance = distance(destination, there);
                if (newDistance < currentDistance) {
                    return new MoveActorAction(destination, exit.getName());
                }
            }
        }
        return null;
    }


    private int distance(Location a, Location b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * return the location of the nearest target
     * @param actor
     * @param map
     * @return location of nearest actor with the same type as specified
     */
    public static Location search(Actor actor,GameMap map){
        Location there = map.at(map.locationOf(actor).x(), map.locationOf(actor).y() + 1);
        for (int i = - 3; i < 3; i++) {
            for (Exit x :map.at(map.locationOf(actor).x() + i,map.locationOf(actor).y()).getExits()){
                if (x.getDestination().containsAnActor()){
                    if (x.getDestination().getActor().getClass().toString().equals(actor.getClass().toString())){
                        if (actor.hasCapability(GameCapabilty.MALE) && x.getDestination().getActor().hasCapability(GameCapabilty.FEMALE)){
                            there = map.locationOf(x.getDestination().getActor());
                        }
                        else if (actor.hasCapability(GameCapabilty.FEMALE) && x.getDestination().getActor().hasCapability(GameCapabilty.MALE)){
                            there = map.locationOf(x.getDestination().getActor());
                        }
                    }
                }
            }
            for (Exit x :map.at(map.locationOf(actor).x(),map.locationOf(actor).y() + i).getExits()){
                if (x.getDestination().containsAnActor()){
                    if (x.getDestination().getActor().getClass().toString().equals(actor.getClass().toString())){
                        if (actor.hasCapability(GameCapabilty.MALE) && x.getDestination().getActor().hasCapability(GameCapabilty.FEMALE)){
                            there = map.locationOf(x.getDestination().getActor());
                        }
                        else if (actor.hasCapability(GameCapabilty.FEMALE) && x.getDestination().getActor().hasCapability(GameCapabilty.MALE)){
                            there = map.locationOf(x.getDestination().getActor());
                        }
                    }
                }
            }
        }
        return there;
    }
}
