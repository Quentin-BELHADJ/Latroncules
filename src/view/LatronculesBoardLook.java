package view;

import boardifier.model.ContainerElement;
import boardifier.view.ClassicBoardLook;
import javafx.scene.paint.Color;

public class LatronculesBoardLook extends ClassicBoardLook {
    public LatronculesBoardLook(int size,ContainerElement element) {
        super((size-10)/8, element, -1, Color.color(0.450980392,0.584313725,0.321568627), Color.ANTIQUEWHITE, 0, Color.BLACK, 5, Color.BLACK, true);
        setPaddingLeft(-get_cell_size());
        setPaddingTop(-get_cell_size());
    }

    public int get_cell_size(){
        return (getWidth()-10)/8;
    }
}
