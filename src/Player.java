/**
 * Created by Marie-Parisius D. HOUESSOU
 * Email: parisiushss03@gmail.com
 * Email: parisius.houessou@gmail.com
 * on 16/09/2017.
 */

/**
 * @author  Marie-Parisius D. HOUESSOU
 * @version 1.0
 * Class: Player
 * Describe the Player action
 */
public class Player
{
    private String name;
    private int score;
    private String side;

    /**
     *Default Constructor
     */
    public Player()
    {
        this.name =  "Unknown";
        this.score = 0;
        this.side = "Unknown";
    }

    /**
     *Default Constructor
     *
     * @param  name
     *              Player name
     * @param side
     *              Side where the player at on the board
     */
    public Player(String name, String side)
    {
        this.name = name;
        this.score = 0;
        this.side = side;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String nom)
    {
        this.name = name;
    }

    public int getScore()
    {
        return this.score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    /**
     * Add a value to the player's actual score
     *
     * @param score
     *            value to add
     */
    public void addScore(int score)
    {
        setScore(this.score + score);
    }

    /**
     * Display information about a player (name, score and side)
     */
    @Override
    public String toString()
    {
        return "Player [mane : " + name + " , score = " + score + " , side : "
                + side + " ]";
    }
}
