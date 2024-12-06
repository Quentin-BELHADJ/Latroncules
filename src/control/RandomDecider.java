package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import model.LatronculesBoard;
import model.LatronculesStageModel;
import model.Pawn;
import model.Vec2D;

import java.util.ArrayList;
import java.util.Random;

public class RandomDecider extends Decider {

    public static final String name = "RandomBot";
    public static long executionTime = 0;
    public static int n_execution = 0;

    public RandomDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        long start = System.currentTimeMillis();
        LatronculesBoard actual_board = ((LatronculesStageModel)  model.getGameStage()).getBoard();
        int color = model.getIdPlayer();

        ArrayList<ActionList> actions = new ArrayList<>();
        for(Pawn pawn: actual_board.get_pawns(color)) {
            for (Vec2D v : pawn.get_moves()) {
                actions.add(ActionFactory.generateMoveWithinContainer(control,model ,pawn,v.getY(),v.getX(), AnimationTypes.MOVE_LINEARPROP, 10));
            }
        }


        Random rand = new Random();
        int random_index = rand.nextInt(actions.size());

        ActionList action = actions.get(random_index);
        action.setDoEndOfTurn(true);
        long end = System.currentTimeMillis();
        executionTime += (end - start);
        n_execution++;
        return action;
    }
}
