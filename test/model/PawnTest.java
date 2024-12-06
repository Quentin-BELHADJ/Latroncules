package model;

import boardifier.model.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    private LatronculesStageModel gameStageModel;
    private LatronculesBoard board;
    private Cavalier cavalier;

    @BeforeEach
    void setUp() {
        Model model = new Model();
        gameStageModel = new LatronculesStageModel("TestStage", model);
        board = new LatronculesBoard(0, 0, gameStageModel);
        gameStageModel.setBoard(board);
        cavalier = new Cavalier(gameStageModel, Pawn.PAWN_BLUE);
        board.add_pawn(cavalier, 3, 3); // Placing the cavalier at (3, 3)
    }

    @Test
    void test_copy() {
        Cavalier copy = (Cavalier) cavalier.copy();
        assertNotSame(cavalier, copy);
        assertEquals(cavalier.get_color(), copy.get_color());
        assertEquals(cavalier.get_symbol(), copy.get_symbol());
    }

    @Test
    void test_get_color() {
        assertEquals(Pawn.PAWN_BLUE, cavalier.get_color());
    }

    @Test
    void test_get_symbol() {
        assertEquals(Cavalier.CavalierSymbol, cavalier.get_symbol());
    }

    @Test
    void test_get_coordinates() {
        Vec2D coordinates = cavalier.get_coordinates();
        assertEquals(new Vec2D(3, 3), coordinates);
    }

    @Test
    void test_is_red_pawn() {
        assertFalse(cavalier.is_red_pawn());
    }

    @Test
    void test_is_blue_pawn() {
        assertTrue(cavalier.is_blue_pawn());
    }

    @Test
    void test_get_board() {
        assertEquals(board, cavalier.get_board());
    }

    @Test
    void test_set_board() {
        LatronculesBoard newBoard = new LatronculesBoard(0, 0, gameStageModel);
        cavalier.set_board(newBoard);
        assertEquals(newBoard, cavalier.get_board());
    }

    @Test
    void test_get_moves() {
        ArrayList<Vec2D> moves = cavalier.get_moves();
        assertEquals(4,moves.size());
        assertTrue(moves.contains(new Vec2D(3, 2)));
        assertTrue(moves.contains(new Vec2D(3, 4)));
        assertTrue(moves.contains(new Vec2D(2, 2)));
        assertTrue(moves.contains(new Vec2D(4, 4)));

    }
}