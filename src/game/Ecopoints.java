package game;

/**
 * Global ecopoint of the app will be added depending on circumstances
 *
 */
public class Ecopoints {
    private static int points;

    /**
     * constructor start at 0
     *
     */
    public Ecopoints(){
        points = 0;
    }

    /**
     * @return points
     */
    public static int getPoints(){
        return points;
    }

    /**
     * @param add
     */
    public static void addPoints(int add){
        points =  add + points;
    }

    /**
     * @param min
     */
    public static void reducePoints(int min){
        points =  points - min;
    }

}
