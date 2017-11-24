public class Main {
    public static void main(String[] args) {
//        Deck deck = new Deck();
//        Card[] cards = new Card[5];
//        cards = deck.drawFromDeck(5);
//        for(Card card : cards){
//            System.out.println(card.toString()+" ");
//        }
//
//        int left = deck.cardsLeft();
//        System.out.println(left);
        Server testServer = new Server();
        testServer.initializePlayers(3,0);
        testServer.initializeHand();

    }
}
