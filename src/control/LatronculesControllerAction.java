package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.LatronculesStageModel;
import view.LatronculesView;

public class LatronculesControllerAction extends ControllerAction implements EventHandler<ActionEvent> {

    private LatronculesView latronculesView;

    public LatronculesControllerAction(Model model, View view, Controller control) {
        super(model, view, control);
        latronculesView = (LatronculesView) view;
        setMenuHandlers();
    }

    private void setMenuHandlers() {
        // set event handler on the MenuStart item
        latronculesView.getMenuStart().setOnAction(e -> {
            try {
                boolean ok = latronculesView.create_choice_dialog((LatronculesController) control);
                if(ok) {
                    model.stopStage();
                    control.startGame();
                }
            }
            catch(GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }
        });
        // set event handler on the MenuIntro item
        latronculesView.getMenuIntro().setOnAction(e -> {
            control.stopGame();
            latronculesView.resetView();
        });
        // set event handler on the MenuQuit item
        latronculesView.getMenuQuit().setOnAction(e -> {
            System.exit(0);
        });
    }

    public void handle(ActionEvent event) {
        if (!model.isCaptureActionEvent()) return;
    }

}
