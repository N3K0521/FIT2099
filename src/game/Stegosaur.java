package game;


import edu.monash.fit2099.engine.*;
import game.Items.Egg;

import java.util.Random;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Actor {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
	private Behaviour behaviour;
	private int babyAge = 0;
	private int death = 0;
	public static int waterLevel = 60;
	private int maxWaterLevel;
	public Location StegosaurLocation;


	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 * 
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'd', 50);
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
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new AttackAction(this));
	}

	/**
	 * Figure out what to do next.
	 * 
	 * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
	 * just stands there.  That's boring.
	 * 
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (babyAge == 30){
			map.locationOf(this).addItem(new Egg("Stegosaur"));
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
			this.hurt(1);
			Stegosaur.waterLevel -= 1;
		}

		if (hitPoints < 90){
			this.addCapability(DinosaurStatus.HUNGRY);
			System.out.println("Stegosaur at " + StegosaurLocation + " is hungry!");
		}

		if (hitPoints <= 0 ){
			this.addCapability(DinosaurStatus.UNCONSCIOUS);
			System.out.println("Stegosaur at " + StegosaurLocation + " is hungry!");
		}

		// stegosaurs get thirsty of its water level is below 40
		if (waterLevel < 40) {
			this.hasCapability(DinosaurStatus.THIRSTY);
			System.out.println("Stegosaur at " + map.locationOf(this) + " is thirsty!");
		}

		// being unconscious when food level and water level becomes 0
		if (hitPoints == 0 || waterLevel == 0){
			this.hasCapability(DinosaurStatus.UNCONSCIOUS);
			System.out.println("Stegosaur at" + map.locationOf(this) + "is unconscious");
		}

		if (this.hasCapability(DinosaurStatus.UNCONSCIOUS)){
			death ++;
			//dead after being unconscious for 15 turns
			if (death == 15){
				map.locationOf(this).addItem(new Corpse());
				map.removeActor(this);
			}

			//the corpse remains 20 turns for Stegosaurs
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
