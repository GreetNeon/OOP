import java.util.Objects;
import java.util.Scanner;

public class Baccarat {
  // TODO: Implement your Baccarat simulation program here
    static int playerWins = 0, bankerWins = 0, ties = 0;
    public static void main(String[] args) {
            //Ask Player for number of decks
            Scanner input = new Scanner(System.in);
            String userInput = null;
            if(args.length != 0){
                if (Objects.equals(args[0], "-i") || Objects.equals(args[0], "--interactive")) {
                    System.out.println("Would you like to play with 6 or 8 decks?");
                    userInput = input.nextLine();
                }
                else{
                    System.out.println("Invalid Command line argument!");
                    System.exit(0);
                }
            }
            else{
                userInput = "6";
            }
            //Validate user input
            //while (!Objects.equals(userInput, "6\r\n") || !userInput.equals("8\r\n")) {
                //System.out.println("Please enter 6 or 8: answer: " + userInput);
                //input.nextLine();
            //}
            //Set up important game variables
            String replay = "yes";
            int round = 1, burning;
            Shoe gameShoe = new Shoe(Integer.parseInt(userInput));
            gameShoe.shuffle();
            boolean playing = true;
            while (Objects.equals(replay.toLowerCase().charAt(0), 'y') && gameShoe.size() > 5) {
                playing = true;
                while (playing) {
                    //Defining important variables for the round
                    BaccaratHand playerHand = new BaccaratHand(), bankerHand = new BaccaratHand();
                    System.out.println("Round " + round);
                    //Drawing a card, and burning the value of the card
                    burning = gameShoe.deal().value();
                    if (burning == 0) {
                        burning = 10;
                    }
                    if(gameShoe.size() - burning < 6){
                        System.out.println("Shoe is empty, round ended!");
                        gameShoe.discard();
                        break;
                    }
                    gameShoe.cards.remove(burning);
                    //Drawing cards to banker and player
                    int i;
                    for (i = 0; i < 2; i++) {
                        playerHand.add(gameShoe.deal());
                        bankerHand.add(gameShoe.deal());
                    }
                    //Outputting Hands
                    output(bankerHand, playerHand);
                    //Check for winner
                    gameShoe = checkWinner(bankerHand, playerHand, gameShoe);
                    round += 1;
                    playing = false;

                }
                if(args.length != 0){
                    if(Objects.equals(args[0], "-i") || Objects.equals(args[0], "--interactive")){
                        System.out.println("Another round (y/n): ");
                        replay = input.nextLine();
                    }
                }
                else{
                    replay = "y";
                }
            }
            System.out.println(round - 1 + " rounds played");
            System.out.println(playerWins + " player wins");
            System.out.println(bankerWins + " banker wins");
            System.out.println(ties + " ties");
        }

    public static void output(BaccaratHand bHand, BaccaratHand pHand) {
        System.out.println("Player: " + pHand.toString() + " = " + pHand.value());
        System.out.println("Banker: " + bHand.toString() + " = " + bHand.value());
        if (pHand.isNatural()) {
            System.out.println("Player has a Natural");
        }
        if (bHand.isNatural()) {
            System.out.println("Banker has a Natural");
        }
    }
    public static boolean simpleCheck(BaccaratHand bHand, BaccaratHand pHand, boolean drawn) {
        if (bHand.value() > 7 && pHand.value() > 7) {
            ties += 1;
            System.out.println("Tie");
            return true;
        } else if (bHand.value() > 7) {
            bankerWins += 1;
            System.out.println("Banker wins!");
            return true;
        } else if (pHand.value() > 7) {
            playerWins += 1;
            System.out.println("Player wins!");
            return true;
        } else if(drawn){
            ties += 1;
            System.out.println("Tie");
            return true;
        } else{
            return false;
        }
    }
    public static Shoe checkWinner(BaccaratHand bHand, BaccaratHand pHand, Shoe gShoe){
        if (simpleCheck(bHand, pHand, false)){
            return gShoe;
        } else if (pHand.value() < 6){
            //Player Draws Card
            pHand.add(gShoe.deal());
            System.out.println("Dealing third card to player...");
            output(bHand, pHand);

        } else{
            //Player Stands
            if(bHand.value() < 6) {
                //Banker Draws Card
                bHand.add(gShoe.deal());
                System.out.println("Dealing third card to banker...");
                output(bHand, pHand);
                //check for winner again, banker has drawn
                if(simpleCheck(bHand, pHand, true)){
                    return gShoe;
                }
            }
            else{
                //Banker Stands
                ties += 1;
                System.out.println("Tie");
                return gShoe;
            }
        }
        //Player Doesn't Stand
        if(bHand.value() < 3){
            bHand.add(gShoe.deal());
            if(simpleCheck(bHand, pHand, true)){
                return gShoe;
            }
        }else{
            int thirdValue = pHand.cards.get(2).value();
            switch(bHand.value()) {
                case (3):
                    if (thirdValue != 8) {
                        bHand.add(gShoe.deal());
                        System.out.println("Dealing third card to banker...");
                        output(bHand, pHand);
                    }
                    break;
                case(4):
                    if (thirdValue > 1 && thirdValue < 8) {
                        bHand.add(gShoe.deal());
                        System.out.println("Dealing third card to banker...");
                        output(bHand, pHand);
                    }
                    break;
                case(5):
                    if (thirdValue > 3 && thirdValue < 8) {
                        bHand.add(gShoe.deal());
                        System.out.println("Dealing third card to banker...");
                        output(bHand, pHand);
                    }
                    break;
                case(6):
                    if (thirdValue > 5 && thirdValue < 8) {
                        bHand.add(gShoe.deal());
                        System.out.println("Dealing third card to banker...");
                        output(bHand, pHand);
                    }
                    break;
            }
            if(simpleCheck(bHand, pHand, true)){
                return gShoe;
            }
        }
        return gShoe;
    }
}
