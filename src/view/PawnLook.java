package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import model.Cavalier;
import model.Pawn;

import javafx.scene.image.ImageView;

public class PawnLook extends ElementLook {

    int cellSize;

    public PawnLook(GameElement element, int cellSize) {
        super(element);
        this.cellSize = cellSize;
    }

    public void onFaceChange(){
        clearGroup();
        render();
    }

    @Override
    protected void render() {
        Pawn pawn = (Pawn) element;
        String color;
        if(pawn.is_blue_pawn()){
            color = "blue";
        }else{
            color = "red";
        }
        String pawn_type;
        if(pawn.get_symbol() == Cavalier.CavalierSymbol) {
            pawn_type = "cavalier";
        }else{
            pawn_type = "spadassin";
        }
        ImageView imageView = new ImageView();
        Image img = new Image("file:src/icons/"+color+"_"+pawn_type +".png");
        imageView.setImage(img);

        imageView.setFitHeight(cellSize);
        imageView.setFitWidth(cellSize);


        addNode(imageView);
    }

}
