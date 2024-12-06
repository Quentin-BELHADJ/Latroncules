package control;

import boardifier.control.Controller;
import boardifier.control.ControllerKey;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.io.Serializable;

public class LatronculesControllerKey extends ControllerKey implements EventHandler<KeyEvent>{
    public LatronculesControllerKey(Model model, View view, Controller control) {
        super(model, view, control);
    }
}
