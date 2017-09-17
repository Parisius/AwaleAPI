/**
 * Created by Marie-Parisius D. HOUESSOU
 * Email: parisiushss03@gmail.com
 * Email: parisius.houessou@gmail.com
 * on 16/09/2017.
 */

/**
 * @author  Marie-Parisius D. HOUESSOU
 * @version 1.0
 * Class: Artificial_Player
 * Describe the AI action
 */
public class Artificial_Player extends Player
{
    private String side;
    private int score;
    private String name;
    private int difficulty;


    public Artificial_Player(String side, int depth)
    {
        this.name = "IA-"+side;
        this.side = side;
        this.difficulty = depth;
    }

    public int miniMax(int depthToCover, String playerSide, int callerScore, int opponentScore, Board slate, int nbrHole, int nbrSeed, boolean direction)
    {
        return miniMax(depthToCover, playerSide, callerScore, opponentScore, slate,nbrHole, nbrSeed, true, true);
    }

    private int miniMax(int depthToCover, String playerSide, int callerScore, int opponentScore, Board slate, int nbrHole, int nbrSeed, boolean direction, boolean nodeMax)
    {
        int bestNodeValue = 0, seedBeforePlay, seedAfterPlay, nodeValue;
        int choice = -1;
        int totalNbrSeed = (nbrSeed*nbrHole);
        int totalNbrHole = (nbrHole * 2);
        int miSeed = (totalNbrSeed/2);
        Board slateCopy;


        /*-----------Evaluation function---------------------*/
        if (depthToCover == 0) // si on a atteint la profondeur maximale
            return callerScore - opponentScore; //on retourne la valeur de la feuille
        if (callerScore > miSeed)//Si le score de l'appelant est sup�rieur � 24
            return 1000; //on retourne une valeur maximale
        if (opponentScore > miSeed)//Si le score de l'adversaire est sup�rieur � 24
            return -1000; //on retourne une valeur minimale

        if (nodeMax)
        {
            bestNodeValue = -1000;

            if (playerSide == "J1")
            {
                for (int i = 0; i < nbrHole; i++)
                {
                    if (slate.testValid(playerSide, i))
                    {
                        slateCopy = slate.gridState();

                        seedBeforePlay = slateCopy.nbSeed();

                        slateCopy.play(i,playerSide, direction);
                        seedAfterPlay = slateCopy.nbSeed();

                        callerScore += seedBeforePlay - seedAfterPlay;

                        nodeValue = miniMax(depthToCover - 1, playerSide, callerScore, opponentScore, slateCopy,nbrHole, nbrSeed, false );

                        if (nodeValue > bestNodeValue)
                        {
                            bestNodeValue = nodeValue;
                            choice = i;
                        }

                    }
                }

                if (depthToCover == this.difficulty)
                    return choice;
            }
            else
            {
                for (int i = nbrHole-1; i > 0; i--)
                {
                    if (slate.testValid(playerSide, i))
                    {
                        slateCopy = slate.gridState();

                        seedBeforePlay = slateCopy.nbSeed();

                        slateCopy.play(i,playerSide, direction);
                        seedAfterPlay = slateCopy.nbSeed();

                        callerScore += seedBeforePlay - seedAfterPlay;

                        nodeValue = miniMax(depthToCover - 1, playerSide, callerScore, opponentScore, slateCopy,nbrHole, nbrSeed, false );

                        if (nodeValue > bestNodeValue)
                        {
                            bestNodeValue = nodeValue;
                            choice = i;
                        }

                    }
                }
                if (depthToCover == this.difficulty)
                    return choice;
            }


        }
        else
        {

        }
        return bestNodeValue;
    }
}
