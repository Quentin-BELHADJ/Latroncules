package view;

import boardifier.control.Controller;
import boardifier.model.GameException;
import boardifier.model.GameStageModel;

import boardifier.model.TextElement;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import control.LatronculesController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.BoardSquare;
import model.LatronculesBoard;
import model.LatronculesStageModel;
import model.Pawn;

import java.awt.event.ActionListener;

public class LatronculesStageView extends GameStageView {

    Controller control;
    ButtonLook buttonLook;

    public LatronculesStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    public void setButtonListener(EventHandler<ActionEvent> eventHandler){
        buttonLook.setListener(eventHandler);
    }

    @Override
    public void createLooks(){

        LatronculesStageModel model = (LatronculesStageModel) gameStageModel;

        addLook(new ColorTextLook(24, "0x000000", model.getPlayerName()));

        TextElement textElement = new TextElement("Draw", gameStageModel);
        textElement.setLocation(660, 600);
        buttonLook = new ButtonLook(textElement);
        addLook(buttonLook);

        LatronculesBoard board = model.getBoard();
        LatronculesBoardLook board_look = new LatronculesBoardLook(600+5, board);
        addLook(board_look);

        for(Pawn pawn: board.get_pawns()){
            addLook(new PawnLook(pawn, board_look.get_cell_size()));
        }

        for(int x=0; x<8; x++) {
            for (int y = 0; y < 8; y++) {
                BoardSquare square = board.get_square(x, y);
                BoardSquareLook squareLook = new BoardSquareLook(square);
                board_look.addInnerLook(squareLook,y,x);
                addLook(squareLook);
            }
        }

    }


}
