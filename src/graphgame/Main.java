package graphgame;

import graphgame.view.TextBasedView;

public class Main {
    public static void main(String[] args){
        new Main().run();
    }

    private void run(){
        TextBasedView view = new TextBasedView();
        view.registerObserver(new Simulation());
        view.display();
    }
}