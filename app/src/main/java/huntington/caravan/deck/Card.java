package huntington.caravan.deck;

import android.graphics.Bitmap;

public class Card {

    private int id;
    private int suit;
    private int rank;
    private int deck;
    protected Bitmap bmp;
    private int scoreValue;

    public Card(int newId) {
        id = newId;
        suit = Math.round((id/100) * 100);
        rank = id - suit;

         if (rank == 14) {
            scoreValue = 1;
        } else if (rank > 10 && rank < 15) {
            scoreValue = 0;
        } else if (rank ==10) {
            scoreValue = 10;
        } else {
            scoreValue = rank;
        }
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void setBitmap(Bitmap newBitmap) {
        bmp = newBitmap;
    }

    public Bitmap getBitmap() {
        return bmp;
    }

    public int getId() {
        return id;
    }

    public int getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
    public String getName() {
        String name;
        String suits;
        if (suit == 100){
            suits = "Diamonds";
        }
        else if(suit == 200){
            suits = "Clubs";
        }
        else if(suit == 300){
            suits = "Hearts";
        }
        else {
            suits = "Spades";
        }
        name =rank+" of "+suits;
        return name;
    }

}



