package view;

import boardifier.model.*;
import boardifier.view.ElementLook;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.util.EventListener;

public class ButtonLook extends ElementLook {
    Button button;
    TextElement textElement;

    public ButtonLook(TextElement textElement) {
        super(textElement);
        this.textElement = textElement;
        button = new Button(textElement.getText());
    }

    @Override
    protected void render() {
        addNode(button);
    }

    public void setListener(EventHandler<ActionEvent> listener){
        button.setOnAction(listener);
    }
}
