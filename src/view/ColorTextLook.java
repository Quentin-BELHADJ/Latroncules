package view;

import boardifier.model.GameElement;
import boardifier.view.TextLook;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorTextLook extends TextLook {

    public ColorTextLook(int fontSize, String color, GameElement element) {
        super(fontSize, color, element);
    }

    public void set_color(Paint paint){
        text.setFill(paint);
    }
}
