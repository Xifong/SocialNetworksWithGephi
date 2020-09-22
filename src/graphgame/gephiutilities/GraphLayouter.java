package graphgame.gephiutilities;

import graphgame.GephiAdaptor;
import org.gephi.appearance.api.*;
import org.gephi.appearance.plugin.RankingNodeSizeTransformer;
import org.gephi.appearance.plugin.UniqueElementColorTransformer;
import org.gephi.graph.api.Column;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlas;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.statistics.plugin.EigenvectorCentrality;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GraphLayouter {

    private AutoLayout autoLayout;

    public void setAppearances(){
        calculateCentralities();
        rankSizeByCentrality();
        setEdgesToGrey();
    }

    public void run(int seconds){
        initialiseLayout(seconds);
        autoLayout.execute();
    }

    private void calculateCentralities(){
        EigenvectorCentrality centrality = new EigenvectorCentrality();
        centrality.execute(GephiAdaptor.getGraphModel());
    }

    private void rankSizeByCentrality(){
        Column centralities = GephiAdaptor.getGraphModel().getNodeTable()
                .getColumn(EigenvectorCentrality.EIGENVECTOR);

        Function centralityRanking = GephiAdaptor.getAppearanceController().getModel().getNodeFunction(
                GephiAdaptor.getGraph(), centralities, RankingNodeSizeTransformer.class);

        RankingNodeSizeTransformer sizeTransformer = centralityRanking.getTransformer();
        sizeTransformer.setMinSize(3f);
        sizeTransformer.setMaxSize(10f);

        GephiAdaptor.getAppearanceController().transform(centralityRanking);
    }

    private void setEdgesToGrey(){
        Function setGrey = GephiAdaptor.getAppearanceController().getModel().getEdgeFunction(
                GephiAdaptor.getGraph(), AppearanceModel.GraphFunction.EDGE_WEIGHT, UniqueElementColorTransformer.class);

        for(Function f : GephiAdaptor.getAppearanceController().getModel().getEdgeFunctions(GephiAdaptor.getGraph())){
            System.out.println(f);
        }

        System.out.println(setGrey);
        UniqueElementColorTransformer colorTransformer = setGrey.getTransformer();
        colorTransformer.setColor(Color.GRAY);

        GephiAdaptor.getAppearanceController().transform(setGrey);
    }

    private void initialiseLayout(int seconds){
        autoLayout = new AutoLayout(seconds, TimeUnit.SECONDS);
        autoLayout.setGraphModel(GephiAdaptor.getGraphModel());

        ForceAtlas fA = new ForceAtlas();
        ForceAtlasLayout layout = new ForceAtlasLayout(fA);

        layout.setInertia(0.1);
        layout.setRepulsionStrength(20D);
        layout.setAttractionStrength(1D);
        layout.setMaxDisplacement(0.1D);
        layout.setFreezeBalance(true);
        layout.setFreezeStrength(80D);
        layout.setFreezeInertia(0.2D);
        layout.setGravity(30D);
        layout.setOutboundAttractionDistribution(false);
        layout.setAdjustSizes(true);
        layout.setSpeed(1D);

        autoLayout.addLayout(layout, 1.0f, new AutoLayout.DynamicProperty[]{});
    }
}
