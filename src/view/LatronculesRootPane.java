package view;

import boardifier.view.RootPane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LatronculesRootPane extends RootPane {

    public LatronculesRootPane() {
        super();
    }

    @Override
    public void createDefaultGroup(){
        Rectangle frame = new Rectangle(400, 100, Color.LIGHTGREY);
        Text text = new Text("Playing the Latroncules");
        text.setFont(new Font(15));
        text.setFill(Color.BLACK);
        text.setX(10);
        text.setY(50);
        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(frame, text);
    }

}
