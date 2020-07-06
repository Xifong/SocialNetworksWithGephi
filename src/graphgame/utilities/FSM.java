package graphgame.utilities;

import org.openide.util.Pair;

import java.util.HashMap;
import java.util.Optional;

public class FSM {
    private HashMap<Enum,
            HashMap<String,
                    Pair<Enum, Optional<Enum>>>> representation = new HashMap<>();
    private Enum startState;
    private Pair<Enum, Optional<Enum>> currentState;

    public FSM(Enum startState) {
        this.startState = startState;
        reset();
    }

    private void reset() {
        this.currentState = Pair.of(startState, Optional.empty());
    }

    public void addState(Enum state) {
        representation.put(state, new HashMap<>());
    }

    public void addTransition(Enum startState, String input, Enum endState) {
        representation.get(startState).put(input, Pair.of(endState, Optional.empty()));
    }

    public void addTransition(Enum startState, String input, Enum endState, Enum action) {
        representation.get(startState).put(input, Pair.of(endState, Optional.of(action)));
    }

    public void transition(String input) {
        currentState = representation.get(getState()).get(input);
    }

    public Enum getState() {
        return currentState.first();
    }

    public Optional<Enum> getAction() {
        return currentState.second();
    }
}
