package model;

import boardifier.model.GameStageModel;

import java.util.ArrayList;

public class Cavalier extends Pawn{

    public static char CavalierSymbol = '♞';

    public Cavalier(GameStageModel gameStageModel, int c) {
        super(gameStageModel, c, CavalierSymbol);
    }

    @Override
    public Pawn copy() {
        return  new Cavalier(gameStageModel, get_color());
    }

    @Override
    public ArrayList<Vec2D> get_playable_squares() {
        return get_cavalier_playable_squares(this);
    }

    public static ArrayList<Vec2D> get_cavalier_playable_squares(Pawn pawn){
        ArrayList<Vec2D> squares = new ArrayList<>();
        LatronculesBoard board = pawn.get_board();
        Vec2D coordinates = pawn.get_coordinates();
        BoardSquare square = board.get_square(coordinates);
        for(Vec2D v: square.getArrows()){
            Vec2D target = v.add(coordinates);
            if(board.is_valid_coordinates(target)){
                if(board.is_empty_square(target)){
                    squares.add(target);
                }else if(board.get_pawn(target).get_color() != pawn.get_color()){
                    Vec2D v2 = v.multiply(2);
                    target = v2.add(coordinates);
                    if(board.is_valid_coordinates(target) && board.is_empty_square(target)){
                        squares.add(target);
                    }
                }
            }
        }
        return squares;
    }

}
