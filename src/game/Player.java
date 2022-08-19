package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
	private int counter = 0;
	private int end;
	private int target;
	private Menu menu = new Menu();
	private boolean challenge = false;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		//count if challenge is chosen
		if (challenge == true){
			counter ++;
			//If the eco point reaches target you win and the game ends
			if (target <= Ecopoints.getPoints()){
				System.out.println("You win");
				map.removeActor(this);
			}
			//else if the turn timer reaches the limit you lose
			else if(counter >= end){
				System.out.println("Too many turns! You lose!");
				map.removeActor(this);
			}
		}
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		actions.add(new Quit());
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Set the challenge target and turn limit if the player chose challenge mode
	 *
	 * @param turn
	 * @param goal
	 */
	public void setChallenge(int turn, int goal){
		end = turn;
		target = goal;
		challenge = true;
	}
}
