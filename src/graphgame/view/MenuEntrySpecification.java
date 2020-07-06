package graphgame.view;

public class MenuEntrySpecification {
    TextBasedView.MenuState initialMenu;
    String input;
    TextBasedView.MenuState targetMenu;
    String label = "Unlabelled option";
    TextBasedView.SubjectState action = TextBasedView.SubjectState.NOTHING;
    String responseMessageString = "";

    MenuEntrySpecification(TextBasedView.MenuState initialMenu, String input){
        this.initialMenu = initialMenu;
        this.input = input;
        this.targetMenu = initialMenu;
    }

    void setTargetMenu(TextBasedView.MenuState targetMenu) {
        this.targetMenu = targetMenu;
    }

    void setLabel(String label) {
        this.label = label;
    }

    void setAction(TextBasedView.SubjectState action) {
        this.action = action;
    }

    void setResponseMessageString(String responseMessageString) {
        this.responseMessageString = responseMessageString;
    }
}
