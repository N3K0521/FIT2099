package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.*;
import game.Items.Egg;

import java.util.Random;

public class Allosaur extends Actor {
    private int babyAge = 0;
    private int death = 0;
    public static int waterLevel = 60;
    private int maxWaterLevel;
    private Behaviour behaviour;
    public Location AllosaurLocation;

    /**
     * Constructor.
     * All Allosaurs are represented by a 'a' and have 20 initial hit points (food level).
     * That is because allosaurs can only be purchased from the vending machine as an egg
     * Baby allosaurs have a food level of 20
     * @param name Allosaur
     */
    public Allosaur(String name) {
        super(name, 'a', 20);
        behaviour = new WanderBehaviour();
        Random r = new Random();
        int result = r.nextInt(101-1) + 1;
        if (result > 51){
            this.addCapability(GameCapabilty.MALE);
        }
        else{
            this.addCapability(GameCapabilty.FEMALE);
        }
        behaviour = new WanderBehaviour();
        this.maxHitPoints = 100;
        this.maxWaterLevel = 100;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (babyAge == 50){
            map.locationOf(this).addItem(new Egg("Allosaur"));
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
            Allosaur.waterLevel -= 1;
        }

        // baby with food level of 20 is starving
        if (hitPoints < 20) {
            this.addCapability(DinosaurStatus.HUNGRY);
            System.out.println("Allosaur at " + AllosaurLocation + " is hungry!" );
        }

        //being unconscious when food level becomes 0
        if (hitPoints <= 0){
            this.addCapability(DinosaurStatus.UNCONSCIOUS);
            System.out.println("Allosaur at" + AllosaurLocation + "is unconscious");
        }
        // allosaur get thirsty of its water level is below 40
        if (waterLevel < 40) {
            this.hasCapability(DinosaurStatus.THIRSTY);
            System.out.println("Allosaur at " + AllosaurLocation + " is thirsty!");
        }

        //being unconscious when food level and water level becomes 0
        if (hitPoints == 0 || waterLevel == 0){
            this.hasCapability(DinosaurStatus.UNCONSCIOUS);
            System.out.println("Allosaur at" + AllosaurLocation + "is unconscious");
        }

        if (this.hasCapability(DinosaurStatus.UNCONSCIOUS)){
            death ++;
            //dead after being unconscious for 15 turns
            if (death == 15){
                map.locationOf(this).addItem(new Corpse());
                map.removeActor(this);
            }

            //the corpse remains 20 turns for Allosaur
            if (death == 35){
                map.removeActor(this);
            }

        }
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }
}



