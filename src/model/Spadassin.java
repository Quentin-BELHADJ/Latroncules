package model;

import boardifier.model.Coord2D;
import boardifier.model.GameStageModel;

import java.util.ArrayList;

public class Spadassin extends Pawn{

    public boolean promoted = false;
    public static char SpadassinSymbol = '⚔';

    public Spadassin(GameStageModel gameStageModel, int c) {
        super(gameStageModel, c, SpadassinSymbol);
    }

    public Spadassin(GameStageModel gameStageModel, int c, boolean p) {
        super(gameStageModel, c, SpadassinSymbol);
        if(p){
            promote();
        }
    }

    @Override
    public Pawn copy() {
        return new Spadassin(gameStageModel,get_color(),is_promoted());
    }

    @Override
    public ArrayList<Vec2D> get_playable_squares() {
        if(is_promoted()){
            return Cavalier.get_cavalier_playable_squares(this);
        }else{
            return spadassin_playable_squares(this);
        }
    }

    public static ArrayList<Vec2D> spadassin_playable_squares(Pawn pawn){
        ArrayList<Vec2D> squares = new ArrayList<>();
        Vec2D coordinates = pawn.get_coordinates();
        int y;
        if(pawn.is_red_pawn()){
            y = coordinates.getY() - 1;
        }else {
            y = coordinates.getY() + 1;
        }
        Vec2D target = new Vec2D(coordinates.getX(), y);
        LatronculesBoard board = pawn.get_board();
        if(board.is_valid_coordinates(target) && board.is_empty_square(target)){
            squares.add(target);
        }
        return squares;
    }

    public boolean is_promoted(){
        return promoted;
    }

    public void promote(){
        set_symbol(Cavalier.CavalierSymbol);
        promoted = true;
        this.addChangeFaceEvent();
    }
}
