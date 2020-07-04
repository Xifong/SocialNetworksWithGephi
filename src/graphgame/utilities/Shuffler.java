package graphgame.utilities;

import java.util.ArrayList;
import java.util.List;

public class Shuffler {
    public static <T> List<T> shuffle(List<T> deck){
        for(int i = 0; i < Math.random()*3 + 2; ++i){
            deck = cutDeck(simpleShuffle(deck));
        }
        return deck;
    }

    private static <T> List<T> cutDeck(List<T> deck){
        List<T> cutDeck = new ArrayList<>();
        cutDeck.addAll(new ListSlice<>(deck, deck.size()/2, deck.size()).getSlice());
        cutDeck.addAll(new ListSlice<>(deck, 0, deck.size()/2).getSlice());
        return cutDeck;
    }

    private static <T> List<T> simpleShuffle(List<T> deck){
        List<T> shuffledDeck = new ArrayList<>();
        int leftPileRunner = 0;
        int rightPileRunner = deck.size()/2;

        while(true){
            if(Math.random() >= 0.5)
                shuffledDeck.add(deck.get(leftPileRunner++));
            else
                shuffledDeck.add(deck.get(rightPileRunner++));

            if(leftPileRunner >= deck.size()/2){
                shuffledDeck.addAll(
                        new ListSlice<>(deck, rightPileRunner, deck.size()).getSlice());
                break;
            }
            else if(rightPileRunner >= deck.size()){
                shuffledDeck.addAll(
                        new ListSlice<>(deck, leftPileRunner, deck.size()/2).getSlice());
                break;
            }
        }
        return shuffledDeck;
    }
}
