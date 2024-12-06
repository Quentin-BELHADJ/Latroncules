package model;

import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SpadassinTest {

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
        Spadassin original = new Spadassin(gameStageModel, Pawn.PAWN_BLUE);
        Spadassin copy = (Spadassin) original.copy();
        assertNotSame(original, copy);
        assertEquals(original.get_color(), copy.get_color());
        assertEquals(original.get_symbol(), copy.get_symbol());
        assertEquals(original.is_promoted(), copy.is_promoted());

        original.promote();
        Spadassin promotedCopy = (Spadassin) original.copy();
        assertNotSame(original, promotedCopy);
        assertEquals(original.is_promoted(), promotedCopy.is_promoted());
    }

    @Test
    void test_spadassin_playable_squares() {
        Spadassin spadassin = new Spadassin(gameStageModel, Pawn.PAWN_BLUE);
        Cavalier obstacle = new Cavalier(gameStageModel, Pawn.PAWN_BLUE);

        board.add_pawn(spadassin, 6, 3);

        ArrayList<Vec2D> squares = Spadassin.spadassin_playable_squares(spadassin);
        assertNotNull(squares);
        assertEquals(1, squares.size());
        assertTrue(squares.contains(new Vec2D(3, 7)));

        board.move_pawn(new Vec2D(3, 6), new Vec2D(3, 7));

        squares = Spadassin.spadassin_playable_squares(spadassin);
        assertEquals(0, squares.size());

        board.remove_pawn(spadassin);
        board.add_pawn(spadassin,3,3);
        board.add_pawn(obstacle,4,3);
        squares = Spadassin.spadassin_playable_squares(spadassin);
        assertEquals(0, squares.size());
    }


    @Test
    void test_is_promoted() {
        Spadassin spadassin = new Spadassin(gameStageModel, Pawn.PAWN_BLUE);
        assertFalse(spadassin.is_promoted());

        spadassin.promote();
        assertTrue(spadassin.is_promoted());
    }

    @Test
    void test_promote() {
        Spadassin spadassin = new Spadassin(gameStageModel, Pawn.PAWN_BLUE);
        assertFalse(spadassin.is_promoted());

        spadassin.promote();
        assertTrue(spadassin.is_promoted());
        assertEquals(Cavalier.CavalierSymbol, spadassin.get_symbol());
    }
}
