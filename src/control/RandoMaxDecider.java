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

public class RandoMaxDecider extends Decider {

    int n_possibilities;
    public static final String name = "RandoMaxBot";
    public static long executionTime = 0;
    public static int n_execution = 0;

    public RandoMaxDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        n_possibilities = 0;
        long startTime = System.nanoTime();
        ActionList actions = get_best_move(10, 2);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        executionTime += duration;
        n_execution++;

        //System.out.println((double) duration / 1_000_000_000 + " seconds with "+n_possibilities + " possibilities");
        actions.setDoEndOfTurn(true);

        return actions;
    }

    public ActionList get_best_move(int depth, int n_visit){
        LatronculesBoard actual_board = ((LatronculesStageModel)  model.getGameStage()).getBoard();
        int color = model.getIdPlayer();
        int new_color;
        if(color==Pawn.PAWN_RED){
            new_color = Pawn.PAWN_BLUE;
        }else{
            new_color = Pawn.PAWN_RED;
        }
        double best_value = Double.NEGATIVE_INFINITY;
        ActionList actions = new ActionList();
        for(Pawn p: actual_board.get_pawns(color)){
            Vec2D p_coordinates = p.get_coordinates();
            for(Vec2D dest: p.get_moves()){
                LatronculesBoard copy_board = actual_board.copy();
                copy_board.move_pawn(p_coordinates, dest);
                double value = research(copy_board, new_color, depth-1, n_visit);
                if(value>best_value){
                    best_value = value;
                    actions = ActionFactory.generateMoveWithinContainer(control,model ,p,dest.getY(), dest.getX(), AnimationTypes.MOVE_LINEARPROP, 10);
                }
            }
        }
        return actions;
    }

    public double research(LatronculesBoard board ,int color,int depth, int n_visit){
        n_possibilities++;
        if(depth==0 || board.game_finished()){
            return evaluation(board);
        }
        double n = 0;
        double s = 0;
        int new_color;
        if(color==Pawn.PAWN_RED){
            new_color = Pawn.PAWN_BLUE;
        }else{
            new_color = Pawn.PAWN_RED;
        }
        if(n_visit<2){
            n_visit = 2;
        }
        for(String s_board: getPossibilities(board,color,n_visit)){
            board.loadFromString(s_board);
            s += research(board,new_color,depth-1, n_visit-1);
            n++;
        }
        return s/n;
    }


    public ArrayList<String> getPossibilities(LatronculesBoard board, int color, int max_length){
        String s_board = board.toString();
        ArrayList<String> psblt = new ArrayList<>();
        ArrayList<Pawn> pawns = board.get_pawns(color);
        Vec2D[] coordinate_pawns = new Vec2D[pawns.size()];
        for(int i = 0; i < pawns.size(); i++){
            coordinate_pawns[i] = pawns.get(i).get_coordinates();
        }
        for(Vec2D pawn_coordinate: coordinate_pawns){
            for(Vec2D dest_coordinate: board.get_pawn(pawn_coordinate).get_moves()){
                board.move_pawn(pawn_coordinate, dest_coordinate);
                psblt.add(board.toString());
                board.loadFromString(s_board);
            }
        }
        while (psblt.size() > max_length){
            Random rand = new Random();
            int random_index = rand.nextInt(psblt.size());
            psblt.remove(random_index);
        }
        return psblt;
    }


    public double evaluation(LatronculesBoard board){
        //red:+; blue-; draw=0
        int color = model.getIdPlayer();

        double result = 0;
        ArrayList<Pawn> redPawns = board.get_pawns(Pawn.PAWN_RED);
        ArrayList<Pawn> bluePawns = board.get_pawns(Pawn.PAWN_BLUE);

        result += redPawns.size();
        result -= bluePawns.size();
        result *= 20;


        for(Pawn pawn: redPawns){
            result += pawn.get_moves().size();
        }
        for(Pawn pawn:bluePawns){
            result-= pawn.get_moves().size();
        }

        if(color==Pawn.PAWN_BLUE){
            result *= -1;
        }

        return result;
    }


}