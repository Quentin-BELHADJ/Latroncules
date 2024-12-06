package model;

import boardifier.control.Decider;
import boardifier.model.*;
import control.MinimaxDecider;
import control.RandoMaxDecider;
import control.RandomDecider;
import boardifier.model.Model;
import boardifier.control.Controller;


public class LatronculesStageModel extends GameStageModel{

    public final static int STATE_SELECTPAWN = 1; // the player must select a pawn
    public final static int STATE_SELECTDEST = 2;
    private TextElement playerName;
    private LatronculesBoard board;

    public LatronculesStageModel(String name, Model model) {
        super(name, model);
        setupCallbacks();
    }

    public void setBoard(LatronculesBoard board) {
        this.board = board;
        for(int c = 0; c < 8; c++){
            for(int r = 0; r < 8; r++){
                addElement(board.get_square(r,c));
            }
        }
        state = STATE_SELECTPAWN;
        addContainer(board);
    }

    public LatronculesBoard getBoard(){
        return this.board;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new LatronculesStageFactory(this);
    }

    public void init_pawns(){

        for(int i = 0; i<8; i+=2){
            Cavalier cavalier = new Cavalier(this,Pawn.PAWN_BLUE);
            add_pawn_to_board(cavalier, 0,i);
        }
        for(int i = 1; i<8; i+=2){
            Spadassin spadassin = new Spadassin(this,Pawn.PAWN_BLUE);
            add_pawn_to_board(spadassin, 0,i);
        }
        for(int i = 1; i<8; i+=2){
            Cavalier cavalier = new Cavalier(this,Pawn.PAWN_BLUE);
            add_pawn_to_board(cavalier, 1,i);
        }
        for(int i = 0; i<8; i+=2){
            Spadassin spadassin = new Spadassin(this,Pawn.PAWN_BLUE);
            add_pawn_to_board(spadassin, 1,i);
        }
        //red pawns
        for(int i = 0; i<8; i+=2){
            Cavalier cavalier = new Cavalier(this,Pawn.PAWN_RED);
            add_pawn_to_board(cavalier, 6,i);
        }
        for(int i = 1; i<8; i+=2){
            Spadassin spadassin = new Spadassin(this,Pawn.PAWN_RED);
            add_pawn_to_board(spadassin, 6,i);
        }
        for(int i = 1; i<8; i+=2){
            Cavalier cavalier = new Cavalier(this,Pawn.PAWN_RED);
            add_pawn_to_board(cavalier, 7,i);
        }
        for(int i = 0; i<8; i+=2){
            Spadassin spadassin = new Spadassin(this,Pawn.PAWN_RED);
            add_pawn_to_board(spadassin, 7,i);
        }
        //board.calcul_moves();
    }

    public TextElement getPlayerName() {
        return playerName;
    }
    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }


    public void add_pawn_to_board(Pawn pawn, int row, int col ){
        board.add_pawn(pawn, row, col);
        addElement(pawn);
    }

    private void setupCallbacks() {
        onSelectionChange( () -> {
            if (selected.size() == 0) {
                board.resetReachableCells(false);
                return;
            }
        });
    }

}
