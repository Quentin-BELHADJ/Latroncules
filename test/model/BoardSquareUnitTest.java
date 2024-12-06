package model;

import boardifier.model.Model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardSquareUnitTest {

    // Classe de modèle vide pour satisfaire le constructeur de LatronculesStageModel.java
    static class TestModel extends Model {}
    @Test
    void testGetArrows() {
        TestModel model = new TestModel();
        LatronculesStageModel gameStageModel = new LatronculesStageModel("TestStage", model);

        // Test for horizontal and vertical arrows
        BoardSquare square1 = new BoardSquare(gameStageModel, true, true, false, false);
        ArrayList<Vec2D> arrows1 = square1.getArrows();
        assertEquals(4, arrows1.size());
        assertTrue(arrows1.contains(new Vec2D(1, 0)));
        assertTrue(arrows1.contains(new Vec2D(-1, 0)));
        assertTrue(arrows1.contains(new Vec2D(0, 1)));
        assertTrue(arrows1.contains(new Vec2D(0, -1)));

        // Test for all directions
        BoardSquare square2 = new BoardSquare(gameStageModel, true, true, true, true);
        ArrayList<Vec2D> arrows2 = square2.getArrows();
        assertEquals(8, arrows2.size());

        // Test for a white square (no directions set)
        BoardSquare square3 = new BoardSquare(gameStageModel, false, false, false, false);
        ArrayList<Vec2D> arrows3 = square3.getArrows();
        assertEquals(8, arrows3.size()); // White square should have all directions
    }



    @Test
    void testIsHorizontal() {
        TestModel model = new TestModel();
        LatronculesStageModel gameStageModel = new LatronculesStageModel("TestStage", model);
        BoardSquare square = new BoardSquare(gameStageModel, true, false, false, false);
        assertTrue(square.isHorizontal());

        BoardSquare square2 = new BoardSquare(gameStageModel, false, false, false, false);
        assertFalse(square2.isHorizontal());
    }

    @Test
    void testIsVertical() {
        TestModel model = new TestModel();
        LatronculesStageModel gameStageModel = new LatronculesStageModel("TestStage", model);
        BoardSquare square = new BoardSquare(gameStageModel, false, true, false, false);
        assertTrue(square.isVertical());

        BoardSquare square2 = new BoardSquare(gameStageModel, false, false, false, false);
        assertFalse(square2.isVertical());
    }

    @Test
    void testIsDiagonal_tl_br() {
        TestModel model = new TestModel();
        LatronculesStageModel gameStageModel = new LatronculesStageModel("TestStage", model);
        BoardSquare square = new BoardSquare(gameStageModel, false, false, true, false);
        assertTrue(square.isDiagonal_tl_br());

        BoardSquare square2 = new BoardSquare(gameStageModel, false, false, false, false);
        assertFalse(square2.isDiagonal_tl_br());
    }

    @Test
    void testIsDiagonal_bl_tr() {
        TestModel model = new TestModel();
        LatronculesStageModel gameStageModel = new LatronculesStageModel("TestStage", model);
        BoardSquare square = new BoardSquare(gameStageModel, false, false, false, true);
        assertTrue(square.isDiagonal_bl_tr());

        BoardSquare square2 = new BoardSquare(gameStageModel, false, false, false, false);
        assertFalse(square2.isDiagonal_bl_tr());
    }

    @Test
    void testIs_white() {
        TestModel model = new TestModel();
        LatronculesStageModel gameStageModel = new LatronculesStageModel("TestStage", model);
        BoardSquare square = new BoardSquare(gameStageModel, false, false, false, false);
        assertTrue(square.is_white());

        BoardSquare square2 = new BoardSquare(gameStageModel, true, false, false, false);
        assertFalse(square2.is_white());

        BoardSquare square3 = new BoardSquare(gameStageModel, false, true, false, false);
        assertFalse(square3.is_white());

        BoardSquare square4 = new BoardSquare(gameStageModel, false, false, true, false);
        assertFalse(square4.is_white());

        BoardSquare square5 = new BoardSquare(gameStageModel, false, false, false, true);
        assertFalse(square5.is_white());
    }

}