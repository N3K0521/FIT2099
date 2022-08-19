package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Items.RipeFruit;

import java.util.ArrayList;
import java.util.Random;

/**
 *The tree class has a chance to grow a fruit on it leaves and then drop it to the ground
 *
 */
public class Tree extends Ground {
	private int age = 0;
	private ArrayList<Item> fruits = new ArrayList<Item>();

	/**
	 *Constructor
	 *
	 */
	public Tree() {
		super('+');
	}

	/**
	 * Has a chance to grow a ripe fruit every turn
	 *
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';
		Random r = new Random();
		int result = r.nextInt(101-1) + 1;
		if (result < 51){
			fruits.add(new RipeFruit());
			Ecopoints.addPoints(1);
		}
		for (Item x:fruits){
			if (result < 6){
				location.addItem(new RipeFruit());
			}
		}
	}
}
