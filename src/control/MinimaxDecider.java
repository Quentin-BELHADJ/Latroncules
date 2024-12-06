package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import model.*;

import java.util.*;

import static java.util.Arrays.sort;


public class MinimaxDecider extends Decider {

    int n_test = 0;
    public static final String name = "MinimaxBot";
    public static long executionTime = 0;
    public static int n_execution = 0;


    public MinimaxDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {

        LatronculesBoard actual_board = ((LatronculesStageModel)  model.getGameStage()).getBoard();


        long startTime = System.nanoTime();
        ArrayList<ActionList> actions = getBestActions(actual_board);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        //System.out.println((double) duration / 1_000_000_000 + " secondes avec "+ n_test + " possibilités visité");

        executionTime += duration;
        n_execution += 1;

        Random rand = new Random();
        int random_index = rand.nextInt(actions.size());

        ActionList action = actions.get(random_index);
        action.setDoEndOfTurn(true);

        return action;
    }

    public ArrayList<ActionList> getBestActions(LatronculesBoard actual_board){
        ArrayList<ActionList> actions = new ArrayList<>();
        int color = model.getIdPlayer();
        double value;
        double best_board_value;
        if(color == Pawn.PAWN_RED){
            best_board_value = Double.NEGATIVE_INFINITY;
        }else{
            best_board_value = Double.POSITIVE_INFINITY;
        }
        for(Pawn p: actual_board.get_pawns(color)) {
            for (Vec2D dest : p.get_moves()) {
                LatronculesBoard test_board = actual_board.copy();
                test_board.move_pawn(p.get_coordinates(), dest);
                value = minimax(test_board, 4,Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, color != Pawn.PAWN_RED);
                if(color == Pawn.PAWN_RED) {
                    if(value >= best_board_value) {
                        if(value > best_board_value) {
                            best_board_value = value;
                            actions.clear();
                        }
                        actions.add(ActionFactory.generateMoveWithinContainer(control,model ,p,dest.getY(), dest.getX(), AnimationTypes.MOVE_LINEARPROP, 10));
                    }
                }else{
                    if(value <= best_board_value) {
                        if(value < best_board_value) {
                            best_board_value = value;
                            actions.clear();
                        }
                        actions.add(ActionFactory.generateMoveWithinContainer(control,model ,p,dest.getY(), dest.getX(), AnimationTypes.MOVE_LINEARPROP, 10));
                    }
                }
            }
        }
        return actions;
    }

    public static double evaluation(LatronculesBoard board){
        //red:+; blue-; draw=0

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
        return result;
    }

    public double minimax(LatronculesBoard board, int depth, double alpha, double beta, boolean maximizingPlayer){
        n_test++;
        double eval;
        if(depth == 0 || board.game_finished()){
            eval = evaluation(board);
            //addBoardValue(s_board, eval, maximizingPlayer);
            return eval;
        }

        if(maximizingPlayer){
            double maxEval = Double.NEGATIVE_INFINITY;
            ArrayList<String> boards = getPossibilities(board, Pawn.PAWN_RED);

            Pair[] listPairs = new Pair[boards.size()];
            for (int i = 0; i < boards.size(); i++) {
                board.loadFromString(boards.get(i));
                Pair pair = new Pair(boards.get(i), evaluation(board));
                listPairs[i] = pair;
            }
            sort(listPairs, new PairComparator());



            for(int i = 0; i < listPairs.length; i++){
                board.loadFromString(listPairs[0].key);
                eval = minimax(board,depth-1, alpha, beta, false);
                if(eval > maxEval) maxEval = eval;
                if(eval > alpha) alpha = eval;
                if(beta <= alpha) break;
            }
            //addBoardValue(s_board, maxEval, maximizingPlayer);

            return maxEval;
        }else {
            double minEval = Double.POSITIVE_INFINITY;
            ArrayList<String> boards = getPossibilities(board, Pawn.PAWN_RED);

            Pair[] listPairs = new Pair[boards.size()];
            for (int i = 0; i < boards.size(); i++) {
                board.loadFromString(boards.get(i));
                Pair pair = new Pair(boards.get(i), evaluation(board));
                listPairs[i] = pair;
            }
            sort(listPairs, new PairComparator());

            for(int i = listPairs.length-1; i >= 0; i--){
                board.loadFromString(listPairs[0].key);
                eval = minimax(board, depth - 1, alpha, beta, true);
                if (eval < minEval) minEval = eval;
                if (eval < beta) beta = eval;
                if (beta <= alpha) break;
            }
            //addBoardValue(s_board, minEval, maximizingPlayer);
            return minEval;
        }
    }



    public ArrayList<String> getPossibilities(LatronculesBoard board, int color){

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
        return psblt;
    }

    public class PairComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair p1, Pair p2) {
            return p1.value.compareTo(p2.value);
        }
    }

    public class Pair {
        String key;
        Double value;

        public Pair(String key, Double value) {
            this.key = key;
            this.value = value;
        }

    }


}


