package graphgame.view;

import graphgame.utilities.FSM;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

class MenuManager {
    private FSM menuMachine;
    private HashMap<Enum, Menu> menus;

    MenuManager(List<Enum> menuStates, Enum startMenu){
        menuMachine = new FSM(startMenu);
        menus = new HashMap<>();

        prepAllMenuStates(menuStates);
    }

    private void prepAllMenuStates(List<Enum> menuStates){
        for (Enum state : menuStates){
            menus.put(state, new BasicMenu());
            menuMachine.addState(state);
        }
    }

    void addOption(MenuEntrySpecification menuEntrySpec){
        TextBasedView.MenuState source = menuEntrySpec.initialMenu;
        String input = menuEntrySpec.input;
        TextBasedView.MenuState dest = menuEntrySpec.targetMenu;
        String label = menuEntrySpec.label;
        TextBasedView.SubjectState action = menuEntrySpec.action;
        String responseString = menuEntrySpec.responseMessageString;

        menus.get(source).addEntry(label, input, responseString);
        menuMachine.addTransition(source, input, dest, action);
    }

    void display(){
        getCurrentMenu().display();
    }

    boolean isAllowedInput(String input){
        return getCurrentMenu().isAllowedInput(input);
    }

    private Menu getCurrentMenu(){
        return menus.get(menuMachine.getState());
    }

    void input(String input){
        getCurrentMenu().displayResponse(input);
        menuMachine.transition(input);
    }

    Optional<Enum> getAction(){
        return menuMachine.getAction();
    }
}
