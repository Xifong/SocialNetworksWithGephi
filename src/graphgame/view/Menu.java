package graphgame.view;

public interface Menu {

    void addEntry(String label, String input, String response);
    void display();
    boolean isAllowedInput(String input);
    void displayResponse(String input);
}
