import boardifier.control.StageFactory;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import control.LatronculesController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.LatronculesRootPane;
import view.LatronculesView;
//import control.Latroncules.java;
//import control.MinimaxDecider.java;
//import control.RandoMaxDecider.java;
//import control.RandomDecider.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        StageFactory.registerModelAndView("Latroncules", "model.LatronculesStageModel", "view.LatronculesStageView");

        LatronculesRootPane rootPane = new LatronculesRootPane();

        LatronculesView view = new LatronculesView(model, stage, rootPane);

        LatronculesController control = new LatronculesController(model, view);

        stage.setTitle("The Latroncules");
        control.setFirstStageName("Latroncules");
        stage.show();

    }
}