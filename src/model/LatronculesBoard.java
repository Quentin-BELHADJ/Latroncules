package model;

import boardifier.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LatronculesBoard extends ContainerElement {

    public final BoardSquare[][] squares;

    public LatronculesBoard(int x, int y, GameStageModel gameStageModel) {
        super("LatronculesBoard", x, y, 8, 8, gameStageModel);
        resetReachableCells(false);
        squares = new BoardSquare[8][8];
        for(int i = 0; i < 16; i++){
            int col = i % 8;
            int row = i / 8;
            if(i%2==row){
                squares[row][col] = new BoardSquare(gameStageModel,false,false,true, true);
                squares[row+6][col] = new BoardSquare(gameStageModel,false,false,true, true);
            }
        }
        
        squares[2][0] = new BoardSquare(gameStageModel,true,false,false,true);
        squares[2][2] = new BoardSquare(gameStageModel,true,false,false,true);
        squares[5][7] = new BoardSquare(gameStageModel,true,false,false,true);
        squares[5][5] = new BoardSquare(gameStageModel,true,false,false,true);

        squares[2][6] = new BoardSquare(gameStageModel,true,false,true, false);
        squares[2][4] = new BoardSquare(gameStageModel,true,false,true, false);
        squares[5][1] = new BoardSquare(gameStageModel,true,false,true, false);
        squares[5][3] = new BoardSquare(gameStageModel,true,false,true, false);

        squares[4][0] = new BoardSquare(gameStageModel,false,true,false,true);
        squares[4][2] = new BoardSquare(gameStageModel,false,true,false,true);
        squares[3][5] = new BoardSquare(gameStageModel,false,true,false,true);
        squares[3][7] = new BoardSquare(gameStageModel,false,true,false,true);

        squares[4][6] = new BoardSquare(gameStageModel,false,true,true, false);
        squares[4][4] = new BoardSquare(gameStageModel,false,true,true, false);
        squares[3][1] = new BoardSquare(gameStageModel,false,true,true, false);
        squares[3][3] = new BoardSquare(gameStageModel,false,true,true, false);


        for(int c = 0; c < 8; c++){
            for(int r = 0; r < 8; r++){
                if(squares[r][c] == null){
                    squares[r][c] = new BoardSquare(gameStageModel,false,false,false, false);
                }
            }
        }
    }

    public LatronculesBoard copy(){
        LatronculesBoard board = new LatronculesBoard(0,0, gameStageModel);
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                GameElement e = getElement(r,c);
                if(e != null){
                    Pawn p = (Pawn) e;
                    Pawn pc = p.copy();
                    board.add_pawn(pc,r,c);
                }
            }
        }
        return board;
    }

    public void add_pawn(Pawn pawn, int r, int c){
        addElement(pawn,r,c);
        pawn.set_board(this);
    }

    public Pawn get_pawn(Vec2D v){
        if(!is_valid_coordinates(v)) return null;
        GameElement e = getElement(v.getY(), v.getX());
        if (e instanceof Pawn){
            return (Pawn) e;
        }else{
            return null;
        }
    }

    public void moveElement(GameElement e, int r, int c){
        super.moveElement(e,r,c);
        Pawn p = (Pawn) e;
        verif_pawn_take_pawns(p);
        verif_promotion(p);
        /*
        Vec2D coordinates = ((Pawn) e).get_coordinates();
        move_pawn(coordinates, new Vec2D(c,r));
        String  s = (char)(coordinates.getX() + 65) + String.valueOf(coordinates.getY()+1) + " to " + (char)(c + 65) + String.valueOf(r+1);
        System.out.println(s);

         */
    }

    public void move_pawn(Vec2D pawnCoord2D, Vec2D destCoord2D){
        Pawn pawn = get_pawn(pawnCoord2D);
        if(pawn.get_moves().contains(destCoord2D)){
            removeElement(pawn);
            addElement(pawn, destCoord2D.getY(), destCoord2D.getX());
            verif_pawn_take_pawns(pawn);
            verif_promotion(pawn);
        }
    }

    public boolean is_empty_square(Vec2D coord2D){
        return get_pawn(coord2D) == null;
    }

    public boolean is_valid_coordinates(Vec2D coord2D){
        if(coord2D.getX() < 0 || coord2D.getY() < 0){
            return false;
        }else if(coord2D.getX() >= getNbCols() || coord2D.getY() >= getNbRows()){
            return false;
        }
        return true;
    };

    public int get_n_red_pawn(){
        int n = 0;
        for(int x = 0; x < getNbCols(); x++){
            for(int y = 0; y < getNbRows(); y++){
                Vec2D coord2D = new Vec2D(x, y);
                Pawn pawn = get_pawn(coord2D);
                if(pawn != null){
                    if (pawn.is_red_pawn()){
                        n++;
                    }
                }
            }
        }
        return n;
    }

    public BoardSquare get_square(Vec2D coord2D){
        return squares[coord2D.getY()][coord2D.getX()];
    }

    public BoardSquare get_square(int x, int y){
        return squares[y][x];
    }

    public int get_n_blue_pawn(){
        int n = 0;
        for(int x = 0; x < getNbCols(); x++){
            for(int y = 0; y < getNbRows(); y++){
                Vec2D coord2D = new Vec2D(x, y);
                Pawn pawn = get_pawn(coord2D);
                if(pawn != null){
                    if (pawn.is_blue_pawn()){
                        n++;
                    }
                }
            }
        }
        return n;
    }

    public void verif_pawn_take_pawns(Pawn pawn){
        Vec2D coordinates = pawn.get_coordinates();
        for(int x = -1; x <= 1; x++){
            for(int y = -1; y <= 1; y++){
                if(x!=0 || y!=0){
                    Vec2D v = new Vec2D(x, y);
                    Vec2D coordinates1 = coordinates.add(v);
                    if(is_valid_coordinates(coordinates1)){
                        Pawn pawn1 = get_pawn(coordinates1);
                        if(pawn1 != null ) {
                            if (pawn1.is_in_corner()) {
                                int y2,x2;
                                if(coordinates.getX() == 0){
                                    x2 = 1;
                                }else if(coordinates.getX() == 1){
                                    x2 = 0;
                                }else if(coordinates.getX() == getNbCols()-2){
                                    x2 = getNbCols()-1;
                                }else{
                                    x2 = getNbCols()-2;
                                }
                                if(coordinates.getY() == 0){
                                    y2 = 1;
                                }else if(coordinates.getY() == 1){
                                    y2 = 0;
                                }else if(coordinates.getY() == getNbRows()-2){
                                    y2 = getNbRows()-1;
                                }else{
                                    y2 = getNbRows()-2;
                                }
                                Vec2D coordinate2 = new Vec2D(x2, y2);
                                Pawn pawn2 = get_pawn(coordinate2);
                                if(pawn2 != null){
                                    if(pawn2.get_color() == pawn.get_color()){
                                        remove_pawn(pawn1);
                                    }
                                }
                            } else {
                                Vec2D coordinates2 = coordinates.add(v.multiply(2));
                                if (is_valid_coordinates(coordinates2)) {
                                    Pawn pawn2 = get_pawn(coordinates2);
                                    if (pawn2 != null) {
                                        if (pawn1.get_color() != pawn.get_color() && pawn.get_color() == pawn2.get_color()) {
                                            remove_pawn(pawn1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean red_pawn_can_move(){
        for(int x = 0; x < getNbCols(); x++){
            for(int y = 0; y < getNbRows(); y++){
                Vec2D coord2D = new Vec2D(x, y);
                Pawn pawn = get_pawn(coord2D);
                if(pawn != null){
                    if(pawn.is_red_pawn()){
                        if(!pawn.get_moves().isEmpty()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean blue_pawn_can_move(){
        for(int x = 0; x < getNbCols(); x++){
            for(int y = 0; y < getNbRows(); y++){
                Vec2D coord2D = new Vec2D(x, y);
                Pawn pawn = get_pawn(coord2D);
                if(pawn != null){
                    if(pawn.is_blue_pawn()){
                        if(!pawn.get_moves().isEmpty()){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean game_finished(){
        int n_red_pawn = get_n_red_pawn();
        int n_blue_pawn = get_n_blue_pawn();
        if (n_red_pawn == 0 || n_blue_pawn == 0){
            return true;
        }
        if(!(red_pawn_can_move() && blue_pawn_can_move())){
            return true;
        }
        return false;
    }

    public int get_winner(){
        if(red_pawn_can_move() && !blue_pawn_can_move()){
            return Pawn.PAWN_RED;
        }
        if(!red_pawn_can_move() && blue_pawn_can_move()){
            return Pawn.PAWN_BLUE;
        }
        if(!red_pawn_can_move() && !blue_pawn_can_move()){
            int n_red = get_n_red_pawn();
            int n_blue = get_n_blue_pawn();
            if(n_red == n_blue){
                return -1;
            }
            if(n_red > n_blue){
                return Pawn.PAWN_RED;
            }else{
                return Pawn.PAWN_BLUE;
            }
        }
        return -1;
    }

    public void verif_promotion(Pawn pawn){
        if(pawn instanceof Spadassin){
            Vec2D coordinates = pawn.get_coordinates();
            if((coordinates.getY() == 0 && pawn.is_red_pawn()) || (coordinates.getY() == getNbRows()-1 && pawn.is_blue_pawn())){
                Spadassin spadassin = (Spadassin) pawn;
                spadassin.promote();
            }
        }

    }

    public void remove_pawn(Pawn pawn){
        removeElement(pawn);
        pawn.setVisible(false);
    }

    public boolean is_corner(Vec2D c){
        if(is_bottom_left_corner(c) || is_bottom_right_corner(c) || is_top_left_corner(c) || is_top_right_corner(c)){
            return true;
        }
        return false;
    }

    public boolean is_top_left_corner(Vec2D c){
        if(c.equals(new Vec2D(0,0))) return true;
        return false;
    }

    public boolean is_top_right_corner(Vec2D c){
        if(c.equals(new Vec2D(getNbCols()-1,0))) return true;
        return false;
    }

    public boolean is_bottom_left_corner(Vec2D c){
        if(c.equals(new Vec2D(0,getNbRows()-1))) return true;
        return false;
    }

    public boolean is_bottom_right_corner(Vec2D c){
        if(c.equals(new Vec2D(getNbCols()-1,getNbRows()-1))) return true;
        return false;
    }

    public boolean can_put_pawn(Pawn pawn,Vec2D dest){
        if(is_valid_coordinates(dest) && is_empty_square(dest)){
            if(is_corner(dest)){
                int n = 0;
                Vec2D[] vectors = {new Vec2D(0, 1), new Vec2D(1, 0), new Vec2D(-1, 0), new Vec2D(0, -1)};
                for(Vec2D v : vectors){
                    Pawn p1 = get_pawn(dest.add(v));
                    if(p1 != null){
                        if(p1.get_color() != pawn.get_color()){
                            Pawn p2 = get_pawn(dest.add(v.multiply(2)));
                            if(!(p2 != null && p2.get_color() == pawn.get_color() && p2 != pawn)){
                                n += 1;
                            }
                        }
                    }
                }
                if(n == 2){
                    return false;
                }
            }else {
                Vec2D[] vectors = {new Vec2D(0, 1), new Vec2D(1, 0), new Vec2D(-1, 1), new Vec2D(1, 1)};
                for (Vec2D v : vectors) {
                    Pawn p1 = get_pawn(dest.add(v));
                    Pawn p2 = get_pawn(dest.add(v.multiply(-1)));
                    if ((p1 != null && p1.get_color() != pawn.get_color()) && (p2 != null && p2.get_color() == p1.get_color())) {
                        Pawn p3 = get_pawn(dest.add(v.multiply(2)));
                        Pawn p4 = get_pawn(dest.add(v.multiply(-2)));
                        if (!((p3 != null && p3.get_color() == pawn.get_color() && p3 != pawn) || (p4 != null && p4.get_color() == pawn.get_color() && p4 != pawn))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    public ArrayList<Pawn> get_pawns(){
        ArrayList<Pawn> pawns = new ArrayList<>();
        for(int r = 0; r < getNbRows(); r++){
            for(int c = 0; c < getNbCols(); c++){
                Pawn p = get_pawn(new Vec2D(c,r));
                if(p != null){
                    pawns.add(p);
                }
            }
        }
        return pawns;
    }

    public ArrayList<Pawn> get_pawns(int color){
        ArrayList<Pawn> pawns = new ArrayList<>();
        for(int r = 0; r < getNbRows(); r++){
            for(int c = 0; c < getNbCols(); c++){
                Pawn p = get_pawn(new Vec2D(c,r));
                if(p != null){
                    if(p.get_color() == color){
                        pawns.add(p);
                    }
                }
            }
        }
        return pawns;
    }

    public String toString(){
        char[] symbols = new char[getNbRows()*getNbCols()];
        for(int r = 0; r < getNbRows(); r++){
            for(int c = 0; c < getNbCols(); c++){
                int i = r*getNbCols()+c;
                Pawn p = get_pawn(new Vec2D(c,r));
                if(p != null){
                    symbols[i] = (char) (p.get_symbol() + p.get_color());
                }else{
                    symbols[i] = ' ';
                }

            }
        }
        return new String(symbols);
    }

    public void loadFromString(String string){
        for(int r = 0; r < getNbRows(); r++) {
            for (int c = 0; c < getNbCols(); c++) {
                int i = r * getNbCols() + c;
                char symbol = string.charAt(i);
                Pawn p = get_pawn(new Vec2D(c,r));
                if(p == null || symbol != (p.get_symbol() + p.get_color())){
                    if(p!=null){
                        remove_pawn(p);
                    }
                    if(symbol == Spadassin.SpadassinSymbol+Pawn.PAWN_BLUE){
                        add_pawn(new Spadassin(gameStageModel,Pawn.PAWN_BLUE), r , c);
                    }else if(symbol == Spadassin.SpadassinSymbol+Pawn.PAWN_RED){
                        add_pawn(new Spadassin(gameStageModel,Pawn.PAWN_RED), r , c);
                    }else if(symbol == Cavalier.CavalierSymbol+Pawn.PAWN_BLUE){
                        add_pawn(new Cavalier(gameStageModel,Pawn.PAWN_BLUE), r , c);
                    }else if(symbol == Cavalier.CavalierSymbol+Pawn.PAWN_RED){
                        add_pawn(new Cavalier(gameStageModel,Pawn.PAWN_RED), r , c);
                    }
                }
            }
        }
    }

}
