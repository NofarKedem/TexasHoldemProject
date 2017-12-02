import XMLobject.GameDescriptor;
import javafx.util.Pair;

import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.*;

public class UI {
    Server server = new Server();
    boolean isGameOver = false;

    public static void main(String[] args) throws Exception {
       //WinLogic test = new WinLogic();
        //test.findTheWinner1();
        UI us = new UI();
        us.menu();
    }

    public void menu() {
        //תחילת המשחק כאילו לחצו 1
        loadFile();
        server.initializePlayers(1,3);

        printState(); // הפלט של 1 זה פקודה 3
        Boolean endGame = false;
        Boolean isGameStarted = false;
        while(!endGame)
        {
            if(!isGameStarted)
            {
                System.out.println("1. Load file");
                System.out.println("2. Start game");
            }
            System.out.println("3. Display status of the game");
            System.out.println("4. Start hand");
            System.out.println("5. Display statistics");
            System.out.println("6. Buy chips");
            System.out.println("7. Quit from the game");
            System.out.println("8. Exit");
            Scanner s = new Scanner(System.in);
            String res = s.next();
            switch (res)
            {
                case "1":
                    if(!isGameStarted)
                    {
                        loadFile();
                        server.initializePlayers(1,3);
                        server.initializeHand();
                        printState();
                    }
                    else
                        System.out.println("Game was started, cant load file");
                    break;

                case "2":
                    if(!isGameStarted)
                    {
                        server.setTimeOfGame();
                        printState();
                        isGameStarted = true;
                    }
                    else
                        System.out.println("Game was allready started");
                    break;

                case "3": printState();
                    break;
                case "4":
                    StartHand();
                    if(server.getNumberOfHands() == server.getCurrentNumberOfHand()) {
                        System.out.println("You played all your hand, game is over! ");
                        endGame = true;
                    }
                    printStatistics();
                    break;
                case "5": printStatistics();
                    break;
                case "6" : buyChips();
                    break;
                case "7" :
                    //endGame = true;
                    isGameStarted = false;
                    System.out.println("You choose to quit the game, what do you want to do now?");
                    System.out.println("1. Start New Game");
                    System.out.println("2. Restart the current game");
                    //להמשיך לממש את הבונוס
                    break;
                case "8" :
                    System.out.println("You choose to finish the game, bey bey!");
                    endGame = true;
                    break;
                default: System.out.println("Invalid input, try again");
                    break;
            }
        }



        // printState(); // הפלט של 2 זה פקודה 3

        //אופציה 3
        // printState();

        //אופציה 4
        printDetailsInTheGame();
    }

    private void loadFile() {
        Boolean isLoadingSuccess = false;

        System.out.println("To start play in the game, please type the file's full path");
        while (!isLoadingSuccess) {
            Scanner s = new Scanner(System.in);
            String filePath = s.next();
            try {
                server.loadFile(filePath);
                isLoadingSuccess = true;
            }
            catch (Exception e) {
                System.out.print("File was not loaded successfully: ");
                System.out.println(e.getMessage());
                System.out.println("Please type again the file's full path");
            }

        }
        System.out.println("File loaded successfully");

    }

    private void buyChips()
    {
        server.addChipsToPlayer();
    }

    private int countDigit(int num)
    {
        int numOfDigit = 0;
        if(num==0)
            return numOfDigit=1;
        while (num > 0) {
            num = num / 10;
            numOfDigit++;
        }
        return numOfDigit;
    }
    private void printState()
    {
        printDoubleState(0);
        System.out.println();
        printDoubleState(2);


    }
    private void printDoubleState(int numOfStartPlayer) {
        System.out.println("********************          ********************");
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            System.out.print("* Type: " + server.getTypeOfPlayer(i) + "          *          ");
        System.out.println();
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            System.out.print("* State: " + server.getStatePlayer(i) + "         *          ");
        System.out.println();

        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            printChips(i);
        System.out.println();


        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            int numberOfBuys = server.getBuysPlayer(i);
            System.out.print("* Buys: " + numberOfBuys);
            int numOfDigit = countDigit(numberOfBuys);
            for (int j = 0; j < 11 - numOfDigit; j++) {
                System.out.print(" ");
            }
            System.out.print("*          ");
        }
        System.out.println();

        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            int numberOfHandWon = server.getHandWonPlayer(i);
            int numberOfHands = server.getNumberOfHands();

            System.out.print("* Hand won: " + numberOfHandWon);
            System.out.print("/" + numberOfHands);

            int numOfDigit = countDigit(numberOfHandWon) + countDigit(numberOfHands) + 1;
            for (int j = 0; j < 7 - numOfDigit; j++) {
                System.out.print(" ");
            }
            System.out.print("*          ");

        }
        System.out.println();
        System.out.println("********************          ********************");
    }

    private void printDetailsInTheGame()
    {
        printDetailsOfTwoFirstPlayer(0);
        System.out.println(System.lineSeparator());
        displayAllCard();
        System.out.print("POT: " + server.getCurrBet());
        System.out.println(System.lineSeparator());
        printDetailsOfTwoLastPlayer(2);
        System.out.println(System.lineSeparator());
    }
    private void displayAllCard()
    {
        List<Card> arrOfTempCards = server.getCommunityCards();
        if(arrOfTempCards.size() != 0) {
            for (Card cardTemp : arrOfTempCards)
                System.out.print(cardTemp.toString() + " | ");

            System.out.print("          ");
        }

    }
    private void printChips(int numOfPlayer)
    {
        int numberOfChips = server.getChipsPlayer(numOfPlayer);
        System.out.print("* Chips: " + numberOfChips);
        int numOfDigit = countDigit(numberOfChips);
        for (int j = 0; j < 10 - numOfDigit; j++) {
            System.out.print(" ");
        }
        System.out.print("*          ");
    }
    private void printDetailsOfTwoFirstPlayer(int numOfStartPlayer)
    {
        System.out.println("********************          ********************");
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            System.out.print("* Type: " + server.getTypeOfPlayer(i) + "          *          ");
        System.out.println();
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            System.out.print("* State: " + server.getStatePlayer(i) + "         *          ");
        System.out.println();

        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            printChips(i);
        System.out.println();

        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            printBet(i);

        }
        System.out.println();
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            printCards(i);
        }

        System.out.println();
        System.out.println("********************          ********************");
    }

    private void printDetailsOfTwoLastPlayer(int numOfStartPlayer)
    {
        System.out.println("********************          ********************");
        for (int i = 3; i > 1; i--)
            System.out.print("* Type: " + server.getTypeOfPlayer(i) + "          *          ");
        System.out.println();
        for (int i = 3; i > 1; i--)
            System.out.print("* State: " + server.getStatePlayer(i) + "         *          ");
        System.out.println();

        for (int i = 3; i > 1; i--)
         printChips(i);
        System.out.println();

        for (int i = 3; i > 1; i--)
        {
            printBet(i);
        }
        System.out.println();
        for (int i = 3; i > 1; i--)
        {
            printCards(i);
        }

        System.out.println();
        System.out.println("********************          ********************");
    }

    private void printBet(int numOfPlayer)
    {
        int numberOfBets = server.getLastBetOfSpecificPlayer(numOfPlayer);
        if(numberOfBets ==0)
        {
            System.out.print("* Bet: ??          ");
            System.out.print("*          ");
        }
        else
        {
            System.out.print("* Bet: " + numberOfBets);
            int numOfDigit = countDigit(numberOfBets);
            for (int j = 0; j < 12 - numOfDigit; j++) {
                System.out.print(" ");
            }
            System.out.print("*          ");

        }
    }
    private void printCards(int numOfPlayer)
    {
        if(server.getTypeOfPlayer(numOfPlayer) == 'H')
        {
            String CardsOfPlayer = server.getCardsPlayer(numOfPlayer);
            System.out.print("* Cards: " + CardsOfPlayer);
            System.out.print("     *          ");
        }
        else
            System.out.print("*                  *          ");
    }
    private void StartHand() //פקודה מספר 4
    {
        Utils.RoundResult resultOfMove = Utils.RoundResult.NOTHINGHAPPEN;
        server.initializeHand();
        server.setPlayHand();
        server.cardDistribusionToPlayer();
        initRound();
        server.blindBet();
        for(int i=0; i< 4;i++) {
            if ((resultOfMove = playOneRound()) != Utils.RoundResult.ENDGAME) //if the game was over
            {
                cardDistribusionInRound(i);
                initRound();
            }
            else
                break;
        }
        //if (resultOfMove != Utils.RoundResult.ENDGAME) {
        try {
            server.getLastMove();
            Map<Integer, String> WinnerMap = server.WhoIsTheWinner();
            for (Integer numOfPlayer : WinnerMap.keySet()) {
                System.out.println("The winner is player number: " + numOfPlayer + " he has " + WinnerMap.get(numOfPlayer));
            }
            server.getLastMove();

        }
     /*
        WinnerMap.get(1);
        Iterator<Map.Entry<Integer, String>> itr=  WinnerMap.entrySet().iterator();
        while (itr.hasNext()) {
            System.out.println("The winner is player number: " + itr.next().getKey() + " he has " + itr.next().getValue());
        }

        }
        */
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("End of hand, buy buy!");



    }
    private void initRound()
    {
        server.initRound();
        System.out.println("Round number " + server.getCurrNumOfRound() + " is starting");
    }
    private void cardDistribusionInRound(int numOfRound)
    {
        switch (numOfRound)
        {
            case 0: server.callFlop();
                break;
            case 1: server.callTurn();
                break;
            case 2: server.callRiver();
                break;
            default: break;


        }
    }



    private Utils.RoundResult playOneRound()
    {
        Utils.RoundResult resultOfMove = Utils.RoundResult.NOTHINGHAPPEN;
        while(resultOfMove == Utils.RoundResult.NOTHINGHAPPEN)
        {
            if(server.getTypeOfPlayer(4) == 'H')
            {
                System.out.println("Before Humen player will play");
                printDetailsInTheGame();
                resultOfMove = playWithHumen(); //if the game was over
                System.out.println("after Humen player play");
                printDetailsInTheGame();

            }
            else
            {
                resultOfMove = server.playWithComputer();
                System.out.println("Computer move was: " + server.getLastMove());
                System.out.println("after computer player play");
                printDetailsInTheGame();
            }
        }
        return resultOfMove;
    }

    private Utils.RoundResult playWithHumen()
    {
        Utils.RoundResult resultOfMove = Utils.RoundResult.NOTHINGHAPPEN;
        boolean isValidMove = false;
        boolean isValidAmount = false;
        while(!isValidMove) {
            System.out.println("Choose which move do you want to do:");
            System.out.println("1. Fold");
            System.out.println("2. Bet");
            System.out.println("3. Call");
            System.out.println("4. Check");
            System.out.println("5. Raise");
            Scanner s = new Scanner(System.in);
            int numOfMove = s.nextInt();
            if (!server.validateMove(numOfMove))
                System.out.println("Invalid move for this Player, please try again");

            else
            {
                isValidMove = true;
                int amount = 0;
                switch (numOfMove) {
                    case 1:
                        System.out.println("You choose to Quit, therefor the game was over. buy buy");
                        break;
                    case 2:
                        while(!isValidAmount) {
                            System.out.println("You choose to bet, for how much do you want to bet?");
                            amount = (new Scanner(System.in)).nextInt();
                            if(isValidAmount = server.validAmount(amount)) {
                                System.out.println("Your bet worked ");
                                isValidAmount = true;
                            }
                            else
                                System.out.println("The amount you enter is invalid, please try again");
                        }
                        break;
                    case 3:
                        System.out.println("You choose to call");
                        break;
                    case 4:
                        System.out.println("You choose to check");
                        break;
                    case 5:
                        while(!isValidAmount) {
                            System.out.println("You choose to Raise,your last bet was" + "" + server.getLastBetOfTheCurrPlayer() +
                                    "for how much do you want to raise your bet?");
                            amount = (new Scanner(System.in)).nextInt();
                            if (isValidAmount = server.validAmount(amount)) {
                                System.out.println("Your raise worked ");
                                isValidAmount = true;
                            } else
                                System.out.println("The amount you enter is invalid, please try again");
                        }
                        
                        break;
                }
                if(resultOfMove != Utils.RoundResult.ENDGAME)
                    resultOfMove = server.gameMove(numOfMove, amount);
            }
        }
        return resultOfMove; //לשנות אותו לenum שיש לו 3 מצבים, ריק, נגמר סיבוב, נגמר משחק
    }

    void printStatistics()
    {
        System.out.println("The time in seconds from starting the game is: " + server.calcTimeFromStartingGame());
        System.out.println(server.getCurrentNumberOfHand() + "/" + server.getNumberOfHands()+ " hand was play");
        System.out.println("The maximum buys for the game is: " + server.getNumberOfBuys());
        printState();
    }

}

