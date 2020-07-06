package graphgame.view;

import org.openide.util.Pair;

import java.util.LinkedHashMap;

public class BasicMenu implements Menu {
    private LinkedHashMap<String, Pair<String, String>> entries = new LinkedHashMap<>();

    @Override
    public void addEntry(String label, String input, String response) {
        entries.put(input, Pair.of(label, response));
    }

    @Override
    public void display() {
        System.out.println("\n");
        for(String key : entries.keySet()){
            displayEntry(key, entries.get(key).first());
        }
    }

    private void displayEntry(String input, String label){
        System.out.println(input + ". " + label);
    }

    @Override
    public boolean isAllowedInput(String input) {
        return entries.containsKey(input);
    }

    @Override
    public void displayResponse(String input) {
        String response = entries.get(input).second();
        if(!response.equals(""))
            System.out.println(entries.get(input).second());
    }
}
