package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.*;
import game.Items.Egg;

import java.util.Random;

/**
 * A herbivorous dinosaur.
 *
 */

public class Brachiosaur extends Actor {
    // the initial numerical value for Brachiosaur
    private Behaviour behaviour;
    private int babyAge = 0;
    private int death = 0;
    public static int waterLevel = 60;
    private int maxWaterLevel;
    private Location BrachiosaurLocation;

    /**
     * Constructor.
     * All Brachiosaur are represented by a 'b' and have 100 initial hit points (food level).
     *
     * @param name Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'b', 100);
        Random r = new Random();
        int result = r.nextInt(101-1) + 1;
        if (result > 51){
            this.addCapability(GameCapabilty.MALE);
        }
        else{
            this.addCapability(GameCapabilty.FEMALE);
        }
        behaviour = new WanderBehaviour();
        this.maxHitPoints = 160;
        this.maxWaterLevel = 200;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (babyAge == 50){
            map.locationOf(this).addItem(new Egg("Brachiosaur"));
            this.hasCapability(DinosaurStatus.EGG);
        }
        if (this.hasCapability(GameCapabilty.PREGNANT)){
            babyAge ++;
        }

        if (this.isConscious()){
            //food level decrease by 1 each turn
            this.hurt(1);
            waterLevel -= 1;
        }

        // brachiosaur get thirsty of its water level is below 40
        if (waterLevel < 40) {
            this.hasCapability(DinosaurStatus.THIRSTY);
            System.out.println("Brachiosaur at " + map.locationOf(this) + " is thirsty!");
        }

        //being unconscious when food level and water level becomes 0
        if (hitPoints == 0 || waterLevel == 0){
            this.hasCapability(DinosaurStatus.UNCONSCIOUS);
            System.out.println("Brachiosaur at" + map.locationOf(this) + "is unconscious");
        }

        // the brachiosaur get hungry if food level gets lower than 140
        if (hitPoints < 140){
            this.addCapability(DinosaurStatus.HUNGRY);
            System.out.println("Brachiosaur at " + BrachiosaurLocation + " is hungry!" );
        }

        //being unconscious when food level becomes 0
        if (hitPoints <= 0){
            this.addCapability(DinosaurStatus.UNCONSCIOUS);
            System.out.println("Brachiosaur at " + BrachiosaurLocation + " is unconscious");
        }

        if (this.hasCapability(DinosaurStatus.UNCONSCIOUS)){
            death ++;
            //dead after being unconscious for 15 turns
            if (death == 15){
                this.hasCapability(DinosaurStatus.DEAD);
                map.locationOf(this).addItem(new Corpse());
                map.removeActor(this);
            }

            //the corpse remains 40 turns for Brachiosaur
            if (death == 55){
                map.removeActor(this);
            }

        }
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
    }

    /**
     * Constructor.
     * @param name        Brachiosaur
     * @param displayChar the character that will represent the Brachiosaur in the display
     * @param hitPoints   the Brachiosaur's starting hit points
     * @param behaviour   the behaviour
     * @param brachiosaurLocation   brachiosaur's location
     */

    public Brachiosaur(String name, char displayChar, int hitPoints, Behaviour behaviour, Location brachiosaurLocation) {
        super(name, displayChar, hitPoints);
        this.behaviour = behaviour;
        BrachiosaurLocation = brachiosaurLocation;
    }



}
