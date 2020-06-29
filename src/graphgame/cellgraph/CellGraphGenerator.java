package graphgame.cellgraph;

public interface CellGraphGenerator {
    void generate(int vertexCount);
    CellGraph getCellGraph();
    void iterateRandomConnections(int iterations);
}
