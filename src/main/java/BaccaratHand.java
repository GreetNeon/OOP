// TODO: Implement the BaccaratHand class in the file
public class BaccaratHand extends CardCollection{

    public BaccaratHand(){
        super();
    }
    public void add(Card card){
        if (size() < 5){
            cards.add(card);
        }
        else{
            throw new CardException("Hand is full");
        }
    }
    public boolean isNatural(){
        if(size() == 2){
            return value() == 8 || value() == 9;
            }
        return false;
        }

    public String toString() {
        String finalString = "";
        int i;
        for (i = 0; i < size(); i++) {
            if (i == 0) {
                finalString = cards.getFirst().toString();
            } else {
                finalString = finalString + " " + cards.get(i).toString();
            }
        }
        return finalString;
    }
}
