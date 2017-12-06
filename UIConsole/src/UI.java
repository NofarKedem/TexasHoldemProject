import java.util.*;

public class UI {
    Server server = new Server();

    public static void main(String[] args) throws Exception {

        UI us = new UI();
        us.menu();
    }

    public void menu() {
        //תחילת המשחק כאילו לחצו 1
        //loadFile();
        //server.initializePlayers(1,3);

        Boolean endGame = false;
        Boolean isGameStarted = false;
        Boolean isLoadingFile = false;
        while(!endGame)
        {
            if(!isGameStarted)
            {
                System.out.println("1. Load file");
                System.out.println("2. Start game");
            }
            if(isGameStarted) {
                System.out.println("3. Display status of the game");
                System.out.println("4. Start hand");
                System.out.println("5. Display statistics");
                System.out.println("6. Buy chips");
                System.out.println("7. Quit from the game");
            }
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
                        isLoadingFile = true;
                        printState();
                    }
                    else
                        System.out.println("Game was started, cant load file");
                    break;

                case "2":
                    if(!isLoadingFile)
                        System.out.println("You need to load file before starting the game");
                    else if(!isGameStarted)
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
                    if(!server.humanPlayerHasNoChips())
                        System.out.println("Human player has no chips, if you want to continued to play please buy another chips");
                    else {
                        StartHand();
                        if (server.getNumberOfHands() == server.getCurrentNumberOfHand()) {
                            System.out.println("You played all your hand, game is over! ");
                        }
                        printStatistics();
                    }
                    break;
                case "5": printStatistics();
                    break;
                case "6" : buyChips();
                    break;
                case "7" :
                    System.out.println("You choose to quit the game, what do you want to do now?");
                    System.out.println("1. Start New Game with loading new file");
                    System.out.println("2. Restart the current game");
                    Scanner ss = new Scanner(System.in);
                    String ress = s.next();
                    if(ress.equals("1")) {
                        isGameStarted = false;
                        isLoadingFile = false;
                        restartGameForNewGame();
                    }
                    if(ress.equals("2")) {
                        restartCurrentGame();
                    }

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
        printTowFirstPlayerState();
        System.out.println();
        printTowLastPlayerState();
    }
    private void printTowFirstPlayerState()
    {
        int numOfStartPlayer =0;
        System.out.println("********************          ********************");
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            System.out.print("* Type: " + server.getTypeOfPlayer(i) + "          *          ");
        System.out.println();
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            System.out.print("* Number: " + server.getNumOfPlayer(i) + "        *          ");
        System.out.println();
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            printStateOfPlayer(i);
        }
        System.out.println();

        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
            printChips(i);
        System.out.println();


        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            printBuys(i);
        }
        System.out.println();

        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            printHandWon(i);
        }
        System.out.println();
        System.out.println("********************          ********************");
    }
    private void printTowLastPlayerState()
    {
        System.out.println("********************          ********************");
        for (int i = 3; i > 1; i--)
            System.out.print("* Type: " + server.getTypeOfPlayer(i) + "          *          ");
        System.out.println();
        for (int i = 3; i > 1; i--)
            System.out.print("* Number: " + server.getNumOfPlayer(i) + "        *          ");
        System.out.println();
        for (int i = 3; i > 1; i--)
        {
            printStateOfPlayer(i);
        }
        System.out.println();

        for (int i = 3; i > 1; i--)
            printChips(i);
        System.out.println();


        for (int i = 3; i > 1; i--)
        {
            printBuys(i);
        }
        System.out.println();

        for (int i = 3; i > 1; i--)
        {
            printHandWon(i);

        }
        System.out.println();
        System.out.println("********************          ********************");
    }
    private void printStateOfPlayer(int i)
    {
        System.out.print("* State: " + server.getStatePlayer(i) );

        if(server.getStatePlayer(i)=="S")
        {
            System.out.print("-" + server.getNumOfChipsForsmall());
            int numOfDigit = countDigit(server.getNumOfChipsForsmall());
            for (int j = 0; j < 8 - numOfDigit; j++) {
                System.out.print(" ");
            }
            System.out.print("*          ");
        }
        else if(server.getStatePlayer(i)=="B")
        {
            System.out.print("-" + server.getNumOfChipsForBig());
            int numOfDigit = countDigit(server.getNumOfChipsForBig());
            for (int j = 0; j < 8 - numOfDigit; j++) {
                System.out.print(" ");
            }
            System.out.print("*          ");
        }
        else
            System.out.print("         *          ");
    }
    private void printBuys(int i)
    {
        int numberOfBuys = server.getBuysPlayer(i);
        System.out.print("* Buys: " + numberOfBuys);
        int numOfDigit = countDigit(numberOfBuys);
        for (int j = 0; j < 11 - numOfDigit; j++) {
            System.out.print(" ");
        }
        System.out.print("*          ");
    }

    private void printHandWon(int i)
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

    private void printDetailsInTheGame()
    {
        printDetailsOfTwoFirstPlayer(0);
        System.out.println(System.lineSeparator());
        displayAllCommunityCards();
        System.out.print("Current bet: " + server.getCurrBet()+ "    ");
        System.out.print("Total cash box: " + server.getCashBox());
        System.out.println(System.lineSeparator());
        printDetailsOfTwoLastPlayer(2);
        System.out.println(System.lineSeparator());
    }
    private void displayAllCommunityCards()
    {
        List<Card> arrOfTempCards = server.getCommunityCards();
        if(arrOfTempCards.size() != 0) {
            for (Card cardTemp : arrOfTempCards)
                System.out.print(cardTemp.toString() + " | ");

            System.out.print("          ");
        }

    }

    private void displayPlayerCard()
    {
        for(int i=0;i<Utils.numOfPlayer;i++)
        {
            System.out.println("Player number: " + (i+1) + " cards are:" + server.getCardsPlayer(i));
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
            System.out.print("* Number: " + server.getNumOfPlayer(i) + "        *          ");
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
        for (int i = numOfStartPlayer; i < numOfStartPlayer+2; i++)
        {
            if(server.getIfPlayerQuit(i))
                System.out.print("* Player quit      *          ");
            else
                System.out.print("*                  *          ");
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
            System.out.print("* Number: " + server.getNumOfPlayer(i) + "        *          ");
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
        for (int i = 3; i > 1; i--)
        {
            if(server.getIfPlayerQuit(i))
                System.out.print("* Player quit      *          ");
            else
                System.out.print("*                  *          ");
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
        int cashBoxReminder = 0;
        Utils.RoundResult resultOfMove = Utils.RoundResult.NOTHINGHAPPEN;
        server.initializeHand();
        server.setPlayHand();
        server.cardDistribusionToPlayer();
        initRound();
        if(server.blindBet()) {
            int i = 0;
            for (i = 0; i < 4; i++) {
                if (resultOfMove != Utils.RoundResult.ENDGAME && resultOfMove != Utils.RoundResult.HUMANFOLD
                        && resultOfMove != Utils.RoundResult.ALLCOMPUTERFOLD) //if the game was over
                {
                    if (i != 0)
                        initRound();
                    resultOfMove = playOneRound();
                    cardDistribusionInRound(i);
                } else
                    break;
            }
            for (int j = i + 1; j < 4; j++) {
                cardDistribusionInRound(i);
            }
            displayPlayerCard();
            System.out.println("The hand was over, to continue to the winner press any key ");
            Scanner s = new Scanner(System.in);
            String res = s.nextLine();

            cashBoxReminder = 0;


            try {
                Map<Integer, String> WinnerMap;
                if (resultOfMove == Utils.RoundResult.HUMANFOLD) {
                    System.out.println("Human fold from choice or he didn't has chips, therefor the computer player has Technical victory");
                    WinnerMap = server.setTechniqWinners(Utils.RoundResult.HUMANFOLD);
                } else if (resultOfMove == Utils.RoundResult.ALLCOMPUTERFOLD) {
                    System.out.println("All the three computer player were quit, therefor the human player has Technical victory");
                    WinnerMap = server.setTechniqWinners(Utils.RoundResult.ALLCOMPUTERFOLD);
                } else {
                    WinnerMap = server.WhoIsTheWinner();
                }
                for (Integer numOfPlayer : WinnerMap.keySet()) {
                    System.out.print("The winner is player number: " + numOfPlayer + " he has " + WinnerMap.get(numOfPlayer)
                            + " the prize money is: ");
                    int cashBoxBeforeDivide = server.getCashBox();
                    int numOfWinners = WinnerMap.size();
                    if (cashBoxBeforeDivide % numOfWinners == 0) {
                        System.out.println(cashBoxBeforeDivide / numOfWinners);
                    } else {
                        cashBoxReminder = cashBoxBeforeDivide % numOfWinners;
                        System.out.println((cashBoxBeforeDivide - cashBoxReminder) / numOfWinners);
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("The current blind bet player\\s do not have enough chips for bet!");
        }

        System.out.println("End of hand, buy buy!");
        server.closeTheHand(cashBoxReminder);

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
            if(server.getTypeOfPlayer(Utils.numOfPlayer) == 'H')
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
                System.out.print("Computer move was: " + server.getLastMove());
                if(server.getLastMove() == "RAISE")
                    System.out.println(" with amount: " + server.getLastGenerateAmount());
                else
                    System.out.println();
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
        if(!server.isPlayerHasEnoughChips()) {
            System.out.println("This player don't have enough chips to play in this hand, therefor the player quit!");
            resultOfMove = server.gameMove("1", 0);
        }
        else {
            while (!isValidMove) {
                System.out.println("Choose which move do you want to do:");
                System.out.println("1. Fold");
                System.out.println("2. Bet");
                System.out.println("3. Call");
                System.out.println("4. Check");
                System.out.println("5. Raise");
                Scanner s = new Scanner(System.in);
                String numOfMove = s.next();
                if (!server.validateMove(numOfMove))
                    System.out.println("Invalid move for this Player, please try again");

                else {
                    isValidMove = true;
                    int amount = 0;
                    String amountStr;
                    switch (numOfMove) {
                        case "1":
                            System.out.println("You choose to Quit, therefor the game was over. buy buy");
                            break;
                        case "2":
                            while (!isValidAmount) {
                                System.out.println("You choose to bet, for how much do you want to bet?");
                                amount = (new Scanner(System.in)).nextInt();
                                if (isValidAmount = server.validAmount(amount)) {
                                    System.out.println("Your bet worked ");
                                    isValidAmount = true;
                                } else
                                    System.out.println("The amount you enter is invalid, please try again");
                            }
                            break;
                        case "3":
                            System.out.println("You choose to call");
                            break;
                        case "4":
                            System.out.println("You choose to check");
                            break;
                        case "5":
                            while (!isValidAmount) {
                                System.out.println("You choose to Raise,your last bet was "+ server.getLastBetOfTheCurrPlayer() +
                                        " for how much do you want to raise your bet?");
                                if(isNumeric(amountStr = new Scanner(System.in).next()) && (isValidAmount = server.validAmount(amount = Integer.parseInt(amountStr))))
                                {
                                        System.out.println("Your raise worked ");
                                        isValidAmount = true;
                                }

                                 else
                                    System.out.println("The amount you enter is invalid, please try again");
                            }

                            break;
                    }
                    if (resultOfMove != Utils.RoundResult.ENDGAME)
                        resultOfMove = server.gameMove(numOfMove, amount);
                }
            }
        }
        return resultOfMove;
    }

    void printStatistics()
    {
        System.out.println("The statistics in the game are: ");
        System.out.println("The time in seconds from starting the game is: " + server.calcTimeFromStartingGame());
        System.out.println(server.getCurrentNumberOfHand() + "/" + server.getNumberOfHands()+ " hand was play");
        System.out.println("The maximum buys for the game is: " + server.getNumberOfBuys());
        printState();
    }

    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public void restartGameForNewGame()
    {
        server.restartGameForNewGame();

    }
    public void restartCurrentGame()
    {
        server.restartCurrentGame();
        server.initializePlayers(1,3);
    }

}

