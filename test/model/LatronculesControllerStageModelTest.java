package model;

import boardifier.model.GameStageModel;
import boardifier.model.Model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LatronculesControllerStageModelTest {

    @Test
    void test_setBoard() {
        Model model = new Model();
        LatronculesStageModel stageModel = new LatronculesStageModel("Test Stage", model);
        LatronculesBoard board = new LatronculesBoard(8, 8, stageModel); // Utiliser le bon constructeur

        stageModel.setBoard(board);
        assertEquals(board, stageModel.getBoard());
        assertTrue(stageModel.getElements().contains(board));
    }

    @Test
    void test_getBoard() {
        Model model = new Model();
        LatronculesStageModel stageModel = new LatronculesStageModel("Test Stage", model);
        LatronculesBoard board = new LatronculesBoard(8, 8, stageModel); // Utiliser le bon constructeur

        stageModel.setBoard(board);
        assertEquals(board, stageModel.getBoard());
    }

    @Test
    void test_getDefaultElementFactory() {
        Model model = new Model();
        LatronculesStageModel stageModel = new LatronculesStageModel("Test Stage", model);

        assertTrue(stageModel.getDefaultElementFactory() instanceof LatronculesStageFactory);
    }

    @Test
    void test_init_pawns() {
        Model model = new Model();
        LatronculesStageModel stageModel = new LatronculesStageModel("Test Stage", model);
        LatronculesBoard board = new LatronculesBoard(8, 8, stageModel); // Utiliser le bon constructeur
        stageModel.setBoard(board);

        stageModel.init_pawns();

        // Vérifiez le nombre de pions ajoutés au plateau
        assertEquals(32, stageModel.getElements().size() - 1); // Moins 1 pour le plateau lui-même
        // Vérifiez quelques positions spécifiques
        assertTrue(board.get_pawn(new Vec2D(0, 0)) instanceof Cavalier);
        assertTrue(board.get_pawn(new Vec2D(0, 1)) instanceof Spadassin);
        assertTrue(board.get_pawn(new Vec2D(6, 0)) instanceof Cavalier);
        assertTrue(board.get_pawn(new Vec2D(6, 1)) instanceof Spadassin);
    }

    @Test
    void test_add_pawn_to_board() {
        Model model = new Model();
        LatronculesStageModel stageModel = new LatronculesStageModel("Test Stage", model);
        LatronculesBoard board = new LatronculesBoard(8, 8, stageModel); // Utiliser le bon constructeur
        stageModel.setBoard(board);

        Pawn spadassin = new Spadassin(stageModel, Pawn.PAWN_BLUE);
        stageModel.add_pawn_to_board(spadassin, 3, 3);

        assertEquals(spadassin, board.get_pawn(new Vec2D(3, 3)));
        assertTrue(stageModel.getElements().contains(spadassin));
    }
}
