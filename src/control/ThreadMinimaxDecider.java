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
import java.util.Comparator;
import java.util.Random;

import static java.util.Arrays.sort;


public class ThreadMinimaxDecider extends Decider {

    int n_test;
    public static final String name = "MinimaxBot";
    public static long executionTime = 0;
    public static int n_execution = 0;


    public ThreadMinimaxDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        n_test = 0;
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

        ArrayList<BranchThread> threads = new ArrayList<>();

        for(Pawn pawn: actual_board.get_pawns(color)) {
            for (Vec2D v : pawn.get_moves()) {
                LatronculesBoard test_board = actual_board.copy();
                BranchThread thread = new BranchThread(this, test_board,color,4, pawn, v);
                thread.start();
                threads.add(thread);
            }
        }

        for(BranchThread branch: threads) {
            try {
                branch.join();
                Pawn p = branch.pawn;
                Vec2D dest = branch.dest;
                value = branch.get_result();
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return actions;
    }

    public static double evaluation(LatronculesBoard board){
        //red:+; blue-; draw=0

        double result = 0;
        ArrayList<Pawn> redPawns = board.get_pawns(Pawn.PAWN_RED);
        ArrayList<Pawn> bluePawns = board.get_pawns(Pawn.PAWN_BLUE);

        //result += redPawns.size();
        //result -= bluePawns.size();
        //result *= 100;


        for(Pawn pawn: redPawns){
            int n = pawn.get_moves().size();
            Vec2D coord = pawn.get_coordinates();
            if(coord.getX() > 1 && coord.getX() < 6 && coord.getY() > 1 && coord.getY() < 6){
                result += n*2;
            }
            else {
                result += n;
            }
        }
        for(Pawn pawn:bluePawns){
            int n = pawn.get_moves().size();
            Vec2D coord = pawn.get_coordinates();
            if(coord.getX() > 1 && coord.getX() < 6 && coord.getY() > 1 && coord.getY() < 6){
                result -= n*2;
            }
            else {
                result -= n;
            }
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
            ArrayList<String> boards = getPossibilities(board, Pawn.PAWN_BLUE);

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

    private class BranchThread extends Thread{
        ThreadMinimaxDecider decider;
        LatronculesBoard board;
        Pawn pawn;
        Vec2D dest;
        double result;
        int color;
        int depth;

        public BranchThread(ThreadMinimaxDecider minimaxDecider, LatronculesBoard board, int color, int depth, Pawn p, Vec2D dest){
            this.board = board;
            board.move_pawn(p.get_coordinates(), dest);
            decider = minimaxDecider;
            this.color = color;
            this.depth = depth;
            this.pawn = p;
            this.dest = dest;
        }

        @Override
        public void run(){
            result = decider.minimax(board, depth,Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, color != Pawn.PAWN_RED);
        }

        public double get_result(){
            return result;
        }
    }


}



