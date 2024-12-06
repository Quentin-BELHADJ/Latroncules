package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.ControllerMouse;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.*;

import java.awt.event.MouseListener;
import java.lang.annotation.ElementType;
import java.util.List;

public class LatronculesControllerMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public LatronculesControllerMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }


    public void handle(MouseEvent event) {

        if(model.getCurrentPlayer().getType() == Player.COMPUTER) return;

        LatronculesStageModel stageModel = (LatronculesStageModel) model.getGameStage();

        if(stageModel == null) return;

        // get the clic x,y in the whole scene (this includes the menu bar if it exists)
        Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());
        // get elements at that position
        //List<GameElement> list = control.elementsAt(clic);



        LatronculesBoard board  = stageModel.getBoard();

        GridLook lookBoard = (GridLook) control.getElementLook(board);
        int dest[];
        try{
            dest = lookBoard.getCellFromSceneLocation(clic);
        }catch (Exception e){
            return;
        }
        if(dest == null){
            return;
        }



        Vec2D clicPos = new Vec2D(dest[1],dest[0]);
        Pawn pawn = board.get_pawn(clicPos);
        if (pawn != null){
            if (pawn.get_color() == model.getIdPlayer()) {
                if(!pawn.isSelected()){
                    stageModel.unselectAll();
                }
                pawn.toggleSelected();
                board.get_square(clicPos).toggleSelected();
                for(Vec2D move: pawn.get_moves()){
                    BoardSquare bs = board.get_square(move);
                    bs.toggleSelected();
                }
                return;
            }
        }else{
            Pawn selectedPawn = null;
            for(Pawn p : board.get_pawns()){
                if(p.isSelected()){
                    selectedPawn = p;
                }
            }
            if(selectedPawn != null){
                if(selectedPawn.get_moves().contains(clicPos)){
                    ActionList actions = ActionFactory.generateMoveWithinContainer(control, model,selectedPawn, clicPos.getY(), clicPos.getX(), AnimationTypes.MOVE_LINEARPROP, 10);
                    actions.setDoEndOfTurn(true);
                    stageModel.unselectAll();
                    ActionPlayer play = new ActionPlayer(model, control, actions);
                    play.start();
                    return;
                }
            }
        }
        stageModel.unselectAll();
        }



}
