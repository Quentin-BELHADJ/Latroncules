package model;

import boardifier.control.Logger;
import boardifier.model.Coord2D;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.Animation;
import boardifier.model.animation.AnimationStep;

import java.util.ArrayList;

public abstract class Pawn extends GameElement{

    public static int PAWN_BLUE = 0;
    public static int PAWN_RED = 1;
    private final int color;
    protected ArrayList<Vec2D> moves;

    private char symbol;
    private LatronculesBoard board;

    public Pawn(GameStageModel gameStageModel ,int c,char s) {
        super(gameStageModel);
        color = c;
        symbol = s;
    }

    public abstract Pawn copy();

    public int get_color(){
        return color;
    }

    public char get_symbol(){
        return symbol;
    }

    public Vec2D get_coordinates(){
        LatronculesBoard board = get_board();
        int[] c = board.getElementCell(this);
        return new Vec2D(c[1], c[0]);
    }

    public boolean is_red_pawn(){
        return color == PAWN_RED;
    }

    public boolean is_blue_pawn(){
        return color == PAWN_BLUE;
    }

    public LatronculesBoard get_board(){
        return board;
    }

    public void set_board(LatronculesBoard board){
        this.board = board;
    }

    protected void set_symbol(char s){
        symbol = s;
    }

    public ArrayList<Vec2D> get_moves(){
        return remove_impossible_move(get_playable_squares());
        //return moves;
    }



    public abstract ArrayList<Vec2D> get_playable_squares();

    public boolean is_in_corner(){
        if(is_in_bottom_left_corner() || is_in_bottom_right_corner() || is_in_top_left_corner() || is_in_top_right_corner()){
            return true;
        }
        return false;
    }

    public boolean is_in_top_left_corner(){
        LatronculesBoard board = get_board();
        Vec2D c = get_coordinates();
        return board.is_top_left_corner(c);
    }

    public boolean is_in_top_right_corner(){
        LatronculesBoard board = get_board();
        Vec2D c = get_coordinates();
        return board.is_top_right_corner(c);
    }

    public boolean is_in_bottom_left_corner(){
        LatronculesBoard board = get_board();
        Vec2D c = get_coordinates();
        return board.is_bottom_left_corner(c);
    }

    public boolean is_in_bottom_right_corner(){
        LatronculesBoard board = get_board();
        Vec2D c = get_coordinates();
        return board.is_bottom_right_corner(c);
    }

    public ArrayList<Vec2D> remove_impossible_move(ArrayList<Vec2D> moves) {
        ArrayList<Vec2D> possible_moves = new ArrayList<>();
        LatronculesBoard board = get_board();
        for (Vec2D v : moves) {
            if (board.can_put_pawn(this,v)){
                possible_moves.add(v);
            }
        }
        return possible_moves;
    }

    public void update() {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step == null) {
                animation = null;
            }
            else if (step == Animation.NOPStep) {
                Logger.debug("nothing to do", this);
            }
            else {
                Logger.debug("move animation", this);
                setLocation(step.getInt(0), step.getInt(1));
            }
        }
    }

}
