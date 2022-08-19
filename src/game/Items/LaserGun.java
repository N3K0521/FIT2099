package game.Items;

import edu.monash.fit2099.engine.Weapon;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * The laser gun weapon
 *
 */
public class LaserGun extends WeaponItem {
    private int price = 500;


    /**
     * its constructor will kill stegosaur in one hit
     *
     */
    public LaserGun() {
        super("Laser gun (500)", 'l', 100, "Zap");
    }

    /**
     * return the price of it
     *
     * @return price
     */
    public int getPrice(){
        return price;
    }
}
