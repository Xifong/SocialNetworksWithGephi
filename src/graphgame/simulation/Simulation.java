package graphgame.simulation;

import graphgame.cellgraph.CellGraph;

public interface Simulation {
    void iterate();
    CellGraph getState();
}
