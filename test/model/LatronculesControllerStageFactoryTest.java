package model;

import boardifier.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LatronculesControllerStageFactoryTest {

    @Test
    void test_setup() {
        Model model = new Model();
        LatronculesStageModel stageModel = new LatronculesStageModel("Test Stage", model);
        LatronculesStageFactory factory = new LatronculesStageFactory(stageModel);

        // CALL SETUP
        factory.setup();

        // Check that the board is correctly initialized
        LatronculesBoard board = stageModel.getBoard();
        assertNotNull(board);
        assertEquals(8, board.getNbRows());
        assertEquals(8, board.getNbCols());

        // Check that the pawns are correctly initialized
        assertEquals(16, board.get_n_blue_pawn());
        assertEquals(16, board.get_n_red_pawn());


        // Checks that the pawns are correctly placed
        assertTrue(board.get_pawn(new Vec2D(0, 0)) instanceof Cavalier);
        assertTrue(board.get_pawn(new Vec2D(0, 1)) instanceof Spadassin);
        assertTrue(board.get_pawn(new Vec2D(6, 0)) instanceof Cavalier);
        assertTrue(board.get_pawn(new Vec2D(6, 1)) instanceof Spadassin);
    }
}