package game;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.List;
import java.util.Random;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {
		super('.');
	}
	@Override
	public void tick(Location location) {
		super.tick(location);
		Random r = new Random();
		int counter = 0;
		int low = 1;
		int high = 101;
		int result = r.nextInt(high-low) + low;
		for (Exit x:location.getExits()){
			if (x.getDestination().getGround().getClass().toString().equals("class game.Tree")){
				result = 1000;
			}
			else if (x.getDestination().getGround().getClass().toString().equals("class game.Bush")){
				counter += 1;
			}
		}
		if (counter > 1){
			result = result - 9;
		}
		if (result <= 1){
			location.setGround(new Bush());
		}
	}
}
