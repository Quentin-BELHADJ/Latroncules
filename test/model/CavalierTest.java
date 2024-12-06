package model;

import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CavalierTest {

    private LatronculesStageModel gameStageModel;
    private LatronculesBoard board;

    @BeforeEach
    void setUp() {
        Model model = new Model();
        gameStageModel = new LatronculesStageModel("TestStage", model);
        board = new LatronculesBoard(8, 8, gameStageModel); // Assuming an 8x8 board
        gameStageModel.setBoard(board);
    }

    @Test
    void test_copy() {
        Cavalier original = new Cavalier(gameStageModel, Pawn.PAWN_BLUE);
        Cavalier copy = (Cavalier) original.copy();
        assertNotSame(original, copy);
        assertEquals(original.get_color(), copy.get_color());
        assertEquals(original.get_symbol(), copy.get_symbol());
    }

    @Test
    void test_get_cavalier_playable_squares() {
        Cavalier cavalier = new Cavalier(gameStageModel, Pawn.PAWN_BLUE);
        board.add_pawn(cavalier, 2, 3); // Placing the cavalier at (3, 3)
        ArrayList<Vec2D> squares = Cavalier.get_cavalier_playable_squares(cavalier);
        assertEquals(8,squares.size());
        board.remove_pawn(cavalier);
        board.add_pawn(cavalier,0,7);
        squares = Cavalier.get_cavalier_playable_squares(cavalier);
        assertEquals(3,squares.size());
        board.remove_pawn(cavalier);
        board.add_pawn(cavalier,0,2);
        squares = Cavalier.get_cavalier_playable_squares(cavalier);
        assertEquals(2,squares.size());

    }
}
