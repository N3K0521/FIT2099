package game;

import edu.monash.fit2099.engine.*;
import game.Items.Egg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pterodactyls extends Actor {
    private int babyAge = 0;
    private int death = 0;
    public static int waterLevel = 60;
    private Random rand = new Random();
    public List<Behaviour> actionFactories = new ArrayList<Behaviour>();
    private Behaviour behaviour;
    private Item Tree;

    /**
     * Constructor.
     * All pterodactyls are represented by a 'p' and have 50 initial hit points (food level).
     * Pterodactyls can only be purchased from the vending machine as an egg
     * @param name Pterodactyls
     * @param PterodactylsLocation
     */
    public Pterodactyls(String name, Location PterodactylsLocation) {
        super(name, 'P', 50);
        Random r = new Random();
        int result = r.nextInt(101-1) + 1;
        if (result > 51){
            this.addCapability(GameCapabilty.MALE);
        }
        else{
            this.addCapability(GameCapabilty.FEMALE);
        }
        this.maxHitPoints = 100;
        int maxWaterLevel = 100;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        //Pterodactyls must breed on trees
        if (babyAge == 50 && map.locationOf(this).getItems().contains(Tree)){
            map.locationOf(this).addItem(new Egg("Pterodactyl"));
            this.hasCapability(DinosaurStatus.EGG);
        }
        if (this.hasCapability(GameCapabilty.PREGNANT)){
            babyAge ++;
        }
        //allow the dino a chance to go to breeding behaviour if food level is high
        else if (hitPoints > 50){
            Random r = new Random();
            int result = r.nextInt(101-1) + 1;
            if (result > 51){
                behaviour = new BreedingBehaviour();
            }
            else{
                behaviour = new WanderBehaviour();
            }
        }

        if (this.isConscious()){
            //food level decrease by 1 each turn
            this.hurt(1);
            //water level decrease by 1 each turn
            waterLevel -=1;
        }

        // baby with food level of 90 is starving
        if (hitPoints < 90) {
            this.addCapability(DinosaurStatus.HUNGRY);
            System.out.println("Pterodactyl at " + map.locationOf(this) + " is hungry!" );
        }

        // pterodactyls get thirsty of its water level is below 40
        if (waterLevel < 40) {
            this.addCapability(DinosaurStatus.THIRSTY);
            System.out.println("Pterodactyl at " + map.locationOf(this) + " is thirsty!");
        }

        //being unconscious when food level and water level becomes 0
        if (hitPoints == 0 || waterLevel == 0){
            this.addCapability(DinosaurStatus.UNCONSCIOUS);
            System.out.println("Pterodactyl at" + map.locationOf(this) + "is unconscious");
        }


        if (this.hasCapability(DinosaurStatus.UNCONSCIOUS)){
            death ++;
            //dead after being unconscious for 15 turns
            if (death == 15){
                map.locationOf(this).addItem(new Corpse());
                map.removeActor(this);
            }

            //the corpse remains 20 turns for Pterodactyl
            if (death == 35){
                map.removeActor(this);
            }

        }

        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;
        for (Behaviour factory : actionFactories) {
            Action action = factory.getAction(this, map);
            if(action != null)
                return action;
        }

        return actions.get(rand.nextInt(actions.size()));
    }
}

