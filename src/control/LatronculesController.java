package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.TextLook;
import boardifier.view.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import model.Cavalier;
import model.LatronculesBoard;
import model.LatronculesStageModel;
import model.Pawn;
import view.ColorTextLook;
import view.LatronculesStageView;
import view.LatronculesView;
import java.io.BufferedReader;
import java.util.Optional;


public class LatronculesController extends Controller {

    BufferedReader consoleIn;
    int limit = -1;
    private Decider[] bots = new Decider[2];


    public LatronculesController(Model model, View view) {
        super(model, view);
        setControlKey(new LatronculesControllerKey(model, view, this));
        setControlMouse(new LatronculesControllerMouse(model, view, this));
        setControlAction (new LatronculesControllerAction(model, view, this));
    }

    public void endOfTurn() {
        LatronculesStageModel stageModel = (LatronculesStageModel) model.getGameStage();
        LatronculesBoard board = stageModel.getBoard();
        if(board.game_finished()){
            model.stopStage();
            model.setIdWinner(board.get_winner());
            endGame();
        }else{
            model.setNextPlayer();
            Player p = model.getCurrentPlayer();
            stageModel.getPlayerName().setText(p.getName() + "'s turn !");
            ColorTextLook textLook = (ColorTextLook) getElementLook(stageModel.getPlayerName());
            if(model.getIdPlayer() == Pawn.PAWN_BLUE){
                textLook.set_color(Color.BLUE);
            }else if(model.getIdPlayer() == Pawn.PAWN_RED){
                textLook.set_color(Color.RED);
            }

            //stageModel.getPlayerName().set
            if (p.getType() == Player.COMPUTER) {
                Logger.debug("COMPUTER PLAYS");

                Decider decider = get_decider(model.getIdPlayer());

                ActionPlayer play = new ActionPlayer(model, this, decider, null);
                play.start();
            }
            else {
                Logger.debug("PLAYER PLAYS");
            }
        }
    }

    public void startGame() throws GameException {
        if (firstStageName.isEmpty()) throw new GameException("The name of the first stage have not been set. Abort");
        Logger.trace("START THE GAME");
        startStage(firstStageName);
        LatronculesView latronculesView = (LatronculesView) view;
        LatronculesStageView latronculesStageView = (LatronculesStageView) view.getGameStageView();
        latronculesStageView.setButtonListener(e -> {
            model.stopStage();
            LatronculesBoard board =( (LatronculesStageModel) model.getGameStage()).getBoard() ;
            model.setIdWinner(board.get_winner());
            endGame();
        });
        int idFirstPlayer = latronculesView.create_first_player_dialog();
        if(idFirstPlayer == Pawn.PAWN_BLUE) {
            idFirstPlayer = Pawn.PAWN_RED;
        }else{
            idFirstPlayer = Pawn.PAWN_BLUE;
        }
        model.setIdPlayer(idFirstPlayer);
        endOfTurn();
    }
    public void set_decider(String botModel, int idPlayer){
        Decider decider;
        if(botModel.equals("Minimax")){
            decider = new MinimaxDecider(model, this);
        }else if(botModel.equals("Randomax")){
            decider = new RandoMaxDecider(model, this);
        }else if(botModel.equals("Random")){
            decider = new RandomDecider(model,this );
        }else{
            decider = null;
        }
        bots[idPlayer] = decider;
    }

    public Decider get_decider(int idPlayer){
        return bots[idPlayer];
    }

}
