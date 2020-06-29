package graphgame.cellgraphgeneration;

import graphgame.cellgraph.CellGraph;

public interface CellGraphGenerator {
    void generate();
    CellGraph getCellGraph();
    void iterateRandomConnections(int iterations);
}
