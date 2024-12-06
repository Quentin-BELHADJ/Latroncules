package model;

import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

import java.util.ArrayList;

public class BoardSquare extends GameElement {

    boolean horizontal;
    boolean vertical;
    boolean diagonal_tl_br;
    boolean diagonal_bl_tr;

    public BoardSquare(GameStageModel gameStageModel, boolean horizontal, boolean vertical, boolean diagonal_tr_bl, boolean diagonale_bl_tr) {
        super(gameStageModel);
        this.horizontal = horizontal;
        this.vertical = vertical;
        this.diagonal_tl_br = diagonal_tr_bl;
        this.diagonal_bl_tr = diagonale_bl_tr;
    }

    public ArrayList<Vec2D> getArrows(){
        ArrayList<Vec2D> arrows = new ArrayList<>();
        boolean is_w = is_white();

        if(horizontal || is_w){
            arrows.add(new Vec2D(1,0));
            arrows.add(new Vec2D(-1,0));
        }
        if(vertical || is_w){
            arrows.add(new Vec2D(0,1));
            arrows.add(new Vec2D(0,-1));
        }
        if(diagonal_tl_br || is_w){
            arrows.add(new Vec2D(-1,-1));
            arrows.add(new Vec2D(1,1));
        }
        if(diagonal_bl_tr || is_w){
            arrows.add(new Vec2D(-1,1));
            arrows.add(new Vec2D(1,-1));
        }
        return arrows;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isDiagonal_tl_br() {
        return diagonal_tl_br;
    }

    public boolean isDiagonal_bl_tr() {
        return diagonal_bl_tr;
    }

    public boolean is_white(){
        return !(horizontal || vertical || diagonal_bl_tr || diagonal_tl_br);
    }

}
