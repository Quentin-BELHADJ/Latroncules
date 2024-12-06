package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;

public class LatronculesStageFactory extends StageElementsFactory {

    private final LatronculesStageModel stageModel;

    public LatronculesStageFactory(GameStageModel model) {
        super(model);
        stageModel = (LatronculesStageModel) model;
    }

    @Override
    public void setup() {
        LatronculesBoard board = new LatronculesBoard(10, 40, stageModel);
        stageModel.setBoard(board);
        stageModel.init_pawns();

        // create the text
        TextElement text = new TextElement("Waiting", stageModel);
        text.setLocation(35,20);
        stageModel.setPlayerName(text);
    }
}
