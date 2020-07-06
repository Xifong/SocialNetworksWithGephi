package graphgame.view;

import graphgame.utilities.MyInput;
import graphgame.utilities.Subject;

import java.util.Arrays;
import java.util.Optional;

public class TextBasedView implements Subject, View {

    private SubjectState subjectState = SubjectState.NOTHING;

    public enum SubjectState {
        NOTHING,
        IMPORT,
        GENERATE,
        ITERATE,
        SAVEEXIT,
        EXIT
    }

    @Override
    public SubjectState getState() {
        return subjectState;
    }

    public enum MenuState {
        VOID,
        NEWGRAPH,
        GENERATION,
        SIMULATION,
        SAVE
    }

    private MenuManager menuManager;

    public TextBasedView() {
        menuManager = new MenuManager(Arrays.asList(MenuState.values()), MenuState.NEWGRAPH);
        specifyMenus();
    }

    private void specifyMenus() {
        specifyNewGraphMenu();
        specifyGenerationMenu();
        specifySimulationMenu();
        specifyEntryExitMenu();
    }

    private void specifyNewGraphMenu(){
        MenuEntrySpecification menuOption = new MenuEntrySpecification(MenuState.NEWGRAPH, "1");
        menuOption.setTargetMenu(MenuState.GENERATION);
        menuOption.setLabel("Generate New Graph");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.NEWGRAPH, "2");
        menuOption.setTargetMenu(MenuState.SIMULATION);
        menuOption.setLabel("Import Previous Graph");
        menuOption.setAction(SubjectState.IMPORT);
        menuOption.setResponseMessageString("Loading World.");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.NEWGRAPH, "q");
        menuOption.setTargetMenu(MenuState.SAVE);
        menuOption.setLabel("Exit");
        menuManager.addOption(menuOption);
    }

    private void specifyGenerationMenu(){
        MenuEntrySpecification menuOption = new MenuEntrySpecification(MenuState.GENERATION, "1");
        menuOption.setTargetMenu(MenuState.SIMULATION);
        menuOption.setLabel("Use Association Based Generator");
        menuOption.setAction(SubjectState.GENERATE);
        menuOption.setResponseMessageString("Generating...");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.GENERATION, "b");
        menuOption.setTargetMenu(MenuState.NEWGRAPH);
        menuOption.setLabel("Back");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.GENERATION, "q");
        menuOption.setTargetMenu(MenuState.SAVE);
        menuOption.setLabel("Exit");
        menuManager.addOption(menuOption);
    }

    private void specifySimulationMenu(){
        MenuEntrySpecification menuOption = new MenuEntrySpecification(MenuState.SIMULATION, "1");
        menuOption.setLabel("Iterate");
        menuOption.setAction(SubjectState.ITERATE);
        menuOption.setResponseMessageString("Iterating...");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.SIMULATION, "b");
        menuOption.setTargetMenu(MenuState.GENERATION);
        menuOption.setLabel("Back");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.SIMULATION, "q");
        menuOption.setTargetMenu(MenuState.SAVE);
        menuOption.setLabel("Exit");
        menuManager.addOption(menuOption);
    }

    private void specifyEntryExitMenu(){
        MenuEntrySpecification menuOption = new MenuEntrySpecification(MenuState.SAVE, "1");
        menuOption.setTargetMenu(MenuState.VOID);
        menuOption.setLabel("Save and Exit");
        menuOption.setAction(SubjectState.SAVEEXIT);
        menuOption.setResponseMessageString("Saving and Exiting...");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.SAVE, "2");
        menuOption.setTargetMenu(MenuState.VOID);
        menuOption.setLabel("Exit");
        menuOption.setAction(SubjectState.EXIT);
        menuOption.setResponseMessageString("Exiting...");
        menuManager.addOption(menuOption);

        menuOption = new MenuEntrySpecification(MenuState.SAVE, "b");
        menuOption.setTargetMenu(MenuState.SIMULATION);
        menuOption.setLabel("Back");
        menuManager.addOption(menuOption);
    }

    @Override
    public void display() {
        while (true) {
            menuManager.display();

            String input;
            while(true){
                input = MyInput.takeInput();
                if(menuManager.isAllowedInput(input))
                    break;
                displayInputNotRecognised();
            }
            menuManager.input(input);

            setSubjectState();
            notifyObservers();
        }
    }

    private void displayInputNotRecognised(){
        System.out.println("Input not recognised, please try again.");
    }

    private void setSubjectState() {
        Optional<Enum> possibleAction = menuManager.getAction();
        if (!possibleAction.isPresent())
            subjectState = SubjectState.NOTHING;
        else
            subjectState = (SubjectState) menuManager.getAction().get();
    }
}
