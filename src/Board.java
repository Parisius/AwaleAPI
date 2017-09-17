import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Marie-Parisius D. HOUESSOU
 * Email: parisiushss03@gmail.com
 * Email: parisius.houessou@gmail.com
 * on 16/09/2017.
 */

/**
 * @author  Marie-Parisius D. HOUESSOU
 * @version 1.0
 * Class Board
 * Define the board and some action associated to it
 */
public class Board
{
    public int[][] cells;
    private int nbrHole;
    private int nbrSeed;
    static int interval;
    static Timer timer;

    /**
     *Construtor that creates a new board and all the seed are initialized at nbrSeed
     *@param nbrHole
     *                  number of hole per player
     *@param nbrSeed
     *                  number of seed per hole
     */
    public Board(int nbrHole, int nbrSeed)
    {
       // this.direction = direction;
        this.nbrHole= nbrHole;
        //this.mihole  = nbrHole/2;
        cells = new int[2][nbrHole];
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < nbrHole; j++)
                cells[i][j] = nbrSeed;
        }
    }

    /**
     * Return the number of seed in a choosen hole
     * @param i
     *            row's number
     * @param j
     *            column's number
     * @return cells[i][j]
     */
    public int getCellule(int i, int j)
    {
        return cells[i][j];
    }

    /**
     *
     * Assign a value to a hole on the board
     *
     * @param i
     *            row's number
     * @param j
     *            column's number
     * @param value
     *            value to assign
     */
    public void setCellule(int i, int j, int value)
    {
        cells[i][j] = value;
    }

    /**
     * Add one seed to a hole
     *
     * @param i
     *            row's number
     * @param j
     *            column's number
     */
    public void addSeed(int i, int j)
    {
        cells[i][j]++;
    }

    /**
     * Return number of seed on the board
     *
     * @return result
     */
    public int nbSeed()
    {
        int result = 0;
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < nbrHole; j++)
                result += cells[i][j];
        }
        return result;
    }

    /**
     *  Return number of seed on a player side
     *
     * @param i
     *            line that represent a player side(0 or 1)
     * @return result
     */
    public int nbSeeds(int i)
    {
        int result = 0;
        for (int j = 0; j < nbrHole; j++)
            result += cells[i][j];
        return result;
    }

    /**
         * Display the whole baord information
     */
    public void MyString()  /// My board
    {
        System.out.println("Actual Board : ");
        System.out.print("  ");
        for (int z = 0; z < nbrHole; z++) {
            System.out.print(z + " ");
        }
        System.out.print("\n");
        for (int i = 0; i < 2; i++)
        {
            System.out.print("[");
            for (int j = 0; j < nbrHole; j++)
                System.out.print(" " + cells[i][j]);
            System.out.println(" ] J" + (i + 1));
        }
        System.out.print("  ");
        for (int d = 0; d < nbrHole; d++)
        {
            System.out.print(d + " ");
        }
        System.out.print("\n");

    }

    /**
     *  Move the seed from a choosen hole to the next hole.
     *  If the move end ond the opponent side it try catch some seed
     *
     * @param j
     *            player's cell to share
     * @param side
     *            side of the play who plays
     * @param direction
     *             direction of the move
     */
    public int play(int j, String side, boolean direction)
    {
        if (direction) /*direction is the contrary of clock's needle*/
        {
            if (side == "P1")
            {

                int nbSeed = cells[0][j];

                /* If the choosen cell is empty return -1 and ask another cell*/
                if (nbSeed == 0)
                    return -1;

                /* Check if this play is possible*/
                int verif = checkBoard(1, j);
                if (verif != 0)
                    return verif;

                setCellule(0, j, 0);
                /* Distribute from this cell until we don't have any seed to distribute or until cell 0 of P1*/
                for (int i = j - 1; i >= 0; i--) {
                    if (nbSeed != 0) {
                        addSeed(0, i);
                        nbSeed--;
                    }

                }
                /* If it still some seed to distribute, do a loop */
                while (nbSeed != 0) {
                    // Distribute on the opponent side
                    //If the distribution ends on opponent side try to catch some seed
                    for (int k = 0; k < nbrHole; k++) {
                        if (nbSeed != 0) {
                            addSeed(1, k);
                            nbSeed--;
                            if (nbSeed == 0) {
                                return catchSeed(1, k);
                            }
                        }
                    }
                    // Distribute on your side if it still some seed to distribute
                    if (nbSeed != 0) {
                        for (int i = nbrHole - 1; i > 0; i--) {
                            if (nbSeed != 0 && i != j) {
                                addSeed(0, i--);
                                nbSeed--;
                            }
                        }
                    }

                }

            }
            // Same as the play of P1 but here we start distribution from P2 side
            else {

                int nbSeed = cells[1][j];

                if (nbSeed == 0)
                    return -1;
                /* Check if this play is possible*/
                int verif = checkBoard(0, j);
                if (verif != 0)
                    return verif;

                setCellule(1, j, 0);

                for (int i = j + 1; i < nbrHole; i++) {
                    if (nbSeed != 0) {
                        addSeed(1, i);
                        nbSeed--;
                    }
                }

                while (nbSeed != 0) {
                    for (int k = nbrHole - 1; k >= 0; k--) {
                        if (nbSeed != 0) {
                            addSeed(0, k);
                            nbSeed--;
                            if (nbSeed == 0) {
                                return catchSeed(0, k);
                            }
                        }
                    }

                    for (int i = 0; i < nbrHole; i++) {
                        if (nbSeed != 0 && i != j) {
                            addSeed(1, i);
                            nbSeed--;
                        }
                    }

                }
            }
            return 0;
        }
        else  /*direction is the one of clock's needle
             The play is the same as when direction is the contrary of clock's needle but here were
               ensure that the move direction is different*/
        {
            if (side == "P2")
            {
                int nbSeed = cells[1][j];
                if (nbSeed == 0)
                    return -1;

                int verif = checkBoard(0, j);
                if (verif != 0)
                    return verif;

                setCellule(1, j, 0);

                for (int i = j - 1; i >= 0; i--)
                {
                    if (nbSeed != 0)
                    {
                        addSeed(1, i);
                        nbSeed--;
                    }

                }

                while (nbSeed != 0)
                {
                    for (int k = 0; k < nbrHole; k++)
                    {
                        if (nbSeed != 0)
                        {
                            addSeed(0, k);
                            nbSeed--;
                            if (nbSeed == 0)
                            {
                                return catchSeed(0, k);
                            }
                        }
                    }

                    if (nbSeed != 0)
                    {
                        for (int i = nbrHole-1; i > 0; i--)
                        {
                            if (nbSeed != 0 && i != j)
                            {
                                addSeed(1, i--);
                                nbSeed--;
                            }
                        }
                    }

                }

            }
            else
            {

                int nbSeed = cells[0][j];

                if (nbSeed == 0)
                    return -1;

                int verif = checkBoard(1, j);
                if (verif != 0)
                    return verif;

                setCellule(0, j, 0);

                for (int i = j + 1; i < nbrHole; i++)
                {
                    if (nbSeed != 0)
                    {
                        addSeed(0, i);
                        nbSeed--;
                    }
                }

                while (nbSeed != 0)
                {
                    for (int k = nbrHole-1; k >= 0; k--)
                    {
                        if (nbSeed != 0)
                        {
                            addSeed(1, k);
                            nbSeed--;
                            if (nbSeed == 0)
                            {
                                return catchSeed(1, k);
                            }
                        }
                    }

                    for (int i = 0; i < nbrHole; i++)
                    {
                        if (nbSeed != 0 && i != j)
                        {
                            addSeed(0, i);
                            nbSeed--;
                        }
                    }

                }
            }
            return 0;
        }

    }



    /**
     * Catch opponent seed when it's possible
     *
     * @param i
     *            side where we catch the seed
     * @param finalCell
     *            cell where the seed's distribution stops
     */
    public int catchSeed(int i, int finalCell)
    {
        int score = 0;

        // Player 2 end on player 1 side
        if (i == 0)
        {
            for (int j = finalCell; j < nbrHole; j++)
            {
                if (cells[0][j] == 3 || cells[0][j] == 2)
                {
                    score += cells[0][j];
                    cells[0][j] = 0;
                }
                else
                    break;
            }
        }
        // Player 1 end on player 2 side
        else
        {
            for (int j = finalCell; j >= 0; j--)
            {
                if (cells[1][j] == 3 || cells[1][j] == 2)
                {
                    score += cells[1][j];
                    cells[1][j] = 0;
                }
                else
                    break;
            }
        }

        return score;

    }

    /**
     * Check if the board on opponent side is empty and if a hole can be played
     *
     * @param i
     *            side to check
     *
     * @param j
     *            hole you want to play
     *
     * @return 0 if the play is possible,
     *         -2 if the board on opponent side is empty and it exists
     *         a least one possible play to feed him but not the one that was previously choose,
     *         -3 if the board on opponent side is empty and it can't be feed
     */

    public int checkBoard(int i, int j)
    {
        // On vérifie si le plateau du J1 est vide, signifique que c'est le tour
        // de J2
        if (i == 0)
        {
            int grainesJoueur = 0;
            for (int k = 0; k < nbrHole; k++)
                grainesJoueur += cells[0][k];
            // Le plateau adverse n'est pas vide, on joue ce que l'on veut
            if (grainesJoueur != 0)
                return 0;

            // Plateau adverse vide, on vérifie que sur la case demandée on a
            // assez de graines pour le nourrir
            if (cells[1][j] + j > nbrHole)
                return 0;

            // Sinon le plateau adverse est vide et case impossible à jouer. On
            // vérifie donc si une case est possible
            for (int k = 0; k < nbrHole; k++)
            {
                if (cells[1][k] + k > nbrHole)
                    return -2;
            }

            // Si rien de tout cela ne marche, cela signifie que le plateau
            // adverse est affamé et qu'on ne peut pas le nourrir,fin de la
            // partie
            return -3;

        }

        // On vérifie si le plateau du J2 est vide, signifique que c'est le tour
        // de J1
        // Même procédé que pour le tour de J2
        else
        {
            int grainesJoueur = 0;
            for (int k = 0; k < nbrHole; k++)
                grainesJoueur += cells[1][k];
            // Le plateau adverse n'est pas vide, on joue ce que l'on veut
            if (grainesJoueur != 0)
                return 0;

            // Plateau adverse vide, on vérifie que sur la case demandée on a
            // assez de graines pour le nourrir
            if (cells[0][j] - j < 0)
                return 0;

            // Sinon le plateau adverse est vide et case impossible à jouer. On
            // vérifie donc si une case est possible
            for (int k = 0; k < nbrHole; k++)
            {
                if (cells[0][k] - k < 0)
                    return -2;
            }

            // Si rien de tout cela ne marche, cela signifie que le plateau
            // adverse est affamé et qu'on ne peut pas le nourrir,fin de la
            // partie
            return -3;
        }
    }

    /**
     * Inform about the possible play for each player:
     * 0: impossible play for this player and this cell
     * 1: possible play for this player and this cell
     *
     * @return in an bi-dimension array the possible play for each player with the value 0 and 1
     */
    public int[][] possiblePlay()
    {
        int[][] play;

        play = new int[2][nbrHole];

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < nbrHole; j++)
                play[i][j] = 0;

        int nbSeedsP1 = 0;

        for (int j = 0; j < nbrHole; j++)
            nbSeedsP1 += cells[0][j];

        int nbSeedsP2 = 0;

        for (int j = 0; j < nbrHole; j++)
            nbSeedsP2 += cells[1][j];

        // If player 2 have seeds, player 1 can play any hole with a seed

        if (nbSeedsP2 != 0)
        {
            for (int j = 0; j < nbrHole; j++)
                if (cells[0][j] != 0)
                    play[0][j] = 1;
        }
        // Unless player 1 ought to feed his opponent
        else
        {
            for (int j = 0; j < nbrHole; j++)
                if (cells[0][j] - j < 0)
                    play[0][j] = 1;
        }

        // If player 1 have seeds, player 2 can play any hole with a seed
        if (nbSeedsP1 != 0)
        {
            for (int j = 0; j < nbrHole; j++)
                if (cells[1][j] != 0)
                    play[1][j] = 1;
        }
        // Unless player 1 ought to feed his opponent
        else
        {
            for (int j = 0; j < nbrHole; j++)
                if (cells[1][j] + j > 6)
                    play[1][j] = 1;
        }

        return play;

    }



    /**
     * Return in a String the array of possible play
     * Renvoie en string le tableau des coups possibles
     * @param  play Array of possible play
     * @return A string
     */
    public String possiblePlayToString(int[][] play)
    {
        String plays = "";

        for (int i = 0; i < 2; i++)
        {
            plays += "P" + (i + 1) + " can play the hole :";
            for (int j = 0; j < nbrHole; j++)
                if (play[i][j] == 1)
                    plays += " " + j;
            plays += "\n";
        }

        return plays;
    }


    /**
     * Return a copy of the actual State of the board
     *
     * @return copy
     */
    public Board gridState()
    {
        Board copy;
        copy = new Board(nbrHole, nbrSeed/*, direction*/);
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < nbrHole; j++)
            {
                cells[i][j] = this.cells[i][j];
            }
        }
        return copy;

    }

    /**
     * Show the default time for each play
     * @param playerGameTime
     */
    public void clock(int playerGameTime)
    {

        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = playerGameTime;
        System.out.print(playerGameTime+ " ");
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.print(setInterval()+" ");

            }
        }, delay, period);
    }

    /**
     * Define the interval for the timer
     * @return interval
     */
    private static final int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }

    /** Use to know if a hole choose by a player can be played
     *
     * @param side Player side
     * @param holeChoose value need to be between 0 and nbrHole-1.
	 *
	 * @return true if the hole can be played ie if number of seed in the hole > 0.
	 */
    public boolean testValid(String side,int holeChoose)
    { if (side == "P1")
    {
        return this.cells[0][holeChoose] > 0;
    }
    else
    {
        return this.cells[1][holeChoose] > 0;
    }

    }

    /**
     * method run(): To run a whole game
     */
    public static void run()
    {
        int nbrHole, nbrHoleInG ,value =0 ,choice, miSeed, nbrSeed, allSeed, type;
        String nomP1, nomP2;
        boolean direction;
        int playerGameTime;

        Scanner sc = new Scanner(System.in);

        System.out.println("Which kind of Game do you want to play:\n 1- Human vs Human \n 2- Computer vs Human \n 3- Computer vs Computer");
        type = Integer.parseInt(sc.nextLine());

        System.out.println("How many hole per player");
        nbrHole = Integer.parseInt(sc.nextLine());

        System.out.println("How many seed per player");
        nbrSeed = Integer.parseInt(sc.nextLine());

        System.out.println("How many seconds per play");
        playerGameTime = Integer.parseInt(sc.nextLine());

        System.out.println("Game direction.\n If you want to play in the usual direction of awale press 0.\n If not press 1");
        choice = Integer.parseInt(sc.nextLine());

        if (choice == 0)
        {
            direction = true;
        }
        else
        {
            direction = false;
        }

        nbrHoleInG = nbrHole-1;
        allSeed = (nbrSeed * nbrHole)*2;
        miSeed = (allSeed / 2) + 1;



        if (type == 1) {

            System.out.println("Player P1 name");
            nomP1 = sc.nextLine();
            Human_Player p1 = new Human_Player(nomP1, "P1");

            System.out.println("Player P2 name");
            nomP2 = sc.nextLine();
            Human_Player p2 = new Human_Player(nomP2, "P2");

            System.out.println("\nBoard Initialization");
            Board game = new Board(nbrHole, nbrSeed);
            game.MyString();

            int nbTotalSeeds = game.nbSeed();
            System.out.println("\nNumber of seed on the board : " + nbTotalSeeds + "\n");

            System.out.println(p1.toString());
            System.out.println(p2.toString());


            // Loop to play turn by turn
            while (nbTotalSeeds > nbrHole && p1.getScore() < miSeed && p2.getScore() < miSeed )
            {

                // ////////////////////////////////////////
                // ------------ Turn P1 --------------- //
                // ////////////////////////////////////////
                System.out.println("\nP1 choose the hole you want to play\n");

                turn1(nbrHole, sc, nbrHoleInG,game, p1,p2,nbTotalSeeds, miSeed, direction);

                if (nbTotalSeeds <= nbrHole || p1.getScore() >= miSeed /*|| game.nbSeeds(0) < 0*/)
                    break;

                // ////////////////////////////////////////
                // ------------ Turn P2 --------------- //
                // ////////////////////////////////////////
                System.out.println("P2 choose the hole you want to play\n");

                turn2(value ,nbrHole, sc, nbrHoleInG,game, p1,p2 , nbTotalSeeds, miSeed , direction);

                // If we lack of seed or P1 win we quit the loop

                if (nbTotalSeeds <= nbrHole || p2.getScore() >= miSeed )
                    break;
            }

            if (p1.getScore() > p2.getScore())
                System.out.println("P1 win!!!!");
            else if (p1.getScore() < p2.getScore())
                System.out.println("P2 win!!!!");
            else
                System.out.println("Draw!!!");
        }
    }

    /**
     * Describe how the turn of Player 1 works
     * @param nbrHole
     * @param sc
     * @param nbrHoleInG
     * @param game
     * @param p1
     * @param p2
     * @param nbTotalSeeds
     * @param miSeed
     * @param direction
     */
    public static void turn1(int nbrHole, Scanner sc, int nbrHoleInG, Board game, Player p1, Player p2, int nbTotalSeeds, int miSeed, boolean direction)
    {
        int cell = 0;
        cell= sc.nextInt();
        while (cell> nbrHole - 1 || cell< 0)
        {
            System.out.println("Choose a number between 0 and "+nbrHoleInG+"\n");
            cell= sc.nextInt();
        }

        int value = game.play(cell, "P1", direction);

        while (value < 0) {
            if (value == -1)
                System.out.println("P1 : choose a non empty hole\n");
            if (value == -2)
                System.out.println("P1 : you need to feed your opponent");
            if (value == -3) {
                p1.addScore(game.nbSeeds(0));
                p2.addScore(game.nbSeeds(1));

                break;
            }
            cell= sc.nextInt();
            while (cell> nbrHole - 1 || cell< 0)
            {
                System.out.println("Choose a number between 0 and"+nbrHoleInG+"\n");
                cell= sc.nextInt();
            }
            value = game.play(cell, "P1", direction);
        }

        p1.addScore(value);

        // ------------ Display board --------------- //
        game.MyString();

        // ------------ Check how many seed are on the board
        // --------------- //
        nbTotalSeeds = game.nbSeed();
        System.out.println("\nNumber of seed on the board : " + nbTotalSeeds + "\n");

        // ------------ Display Players information --------------- //
        System.out.println(p1.toString());
        System.out.println(p2.toString());

        // ------------ Show possible play ------- //
        System.out.println(game.possiblePlayToString(game.possiblePlay()));

    }

    /**
     * Describe how the turn of Player 2 works
     * @param value
     * @param nbrHole
     * @param sc
     * @param nbrHoleInG
     * @param game
     * @param p1
     * @param p2
     * @param nbTotalSeeds
     * @param miSeed
     * @param direction
     */
    public  static void turn2(int value,int nbrHole, Scanner sc, int nbrHoleInG, Board game,Player p1, Player p2, int nbTotalSeeds, int miSeed, boolean direction)
    {
        int cell= 0;
        cell= sc.nextInt();
        while (cell> nbrHole - 1 || cell< 0) {
            System.out.println("Choose a number between 0 and  "+nbrHoleInG+"\n");
            cell= sc.nextInt();
        }
        value = game.play(cell, "P2", direction);

        while (value < 0) {
            if (value == -1)
                System.out.println("P2 : choose a non empty hole\n");
            if (value == -2)
                System.out.println("P2 : you need to feed your opponent");
            if (value == -3) {
                p1.addScore(game.nbSeeds(0));
                p2.addScore(game.nbSeeds(1));
                nbTotalSeeds = 0;
                break;
            }
            cell= sc.nextInt();
            while (cell> nbrHole - 1 || cell< 0) {
                System.out.println("Choose a number between 0 and  "+nbrHoleInG+"\n");
                cell= sc.nextInt();
            }
            value = game.play(cell, "P2", direction);
        }

       p2.addScore(value);


        // ------------ Display board --------------- //
        game.MyString();

        // ------------ Check how many seed are on the board
        // --------------- //
        nbTotalSeeds = game.nbSeed();
        System.out.println("\nValeur du plateau : " + nbTotalSeeds + "\n");

        // ------------ Display Players information --------------- //
        System.out.println(p1.toString());
        System.out.println(p2.toString());

        // ------------ Show possible play ------- //
        System.out.println("\n"+game.possiblePlayToString(game.possiblePlay()));
    }



}
