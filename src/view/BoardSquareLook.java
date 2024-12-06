package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.BoardSquare;
import model.Pawn;

public class BoardSquareLook extends ElementLook {

    public BoardSquareLook(GameElement element) {
        super(element);
    }

    public void add_image(String name){
        ImageView imageView = new ImageView();
        Image img = new Image("file:src/icons/"+name);
        imageView.setImage(img);
        int cellSize = ((LatronculesBoardLook)  getParent()).get_cell_size();
        imageView.setFitHeight(cellSize);
        imageView.setFitWidth(cellSize);
        addNode(imageView);
    }


    @Override
    public void onSelectionChange() {
        clearGroup();
        int cellSize = ((LatronculesBoardLook)  getParent()).get_cell_size();
        if (element.isSelected()) {
            Rectangle rectangle = new Rectangle(cellSize, cellSize, Color.color(0.96, 0.96, 0.50));
            addShape(rectangle);
        }
        render();
    }


    @Override
    protected void render() {

        BoardSquare bs = (BoardSquare) getElement();
        if(bs.isDiagonal_tl_br() ){
            add_image("diagonal_arrows1.png");
        }
        if(bs.isDiagonal_bl_tr()){
            add_image("diagonal_arrows2.png");
        }
        if(bs.isHorizontal()){
            add_image("horizontal_arrows.png");
        }
        if(bs.isVertical()){
            add_image("vertical_arrows.png");
        }

    }
}
