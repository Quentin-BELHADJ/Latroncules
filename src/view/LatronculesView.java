package view;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import control.LatronculesController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.LatronculesStageModel;

import java.util.*;

public class LatronculesView extends View {

    private MenuItem menuStart;
    private MenuItem menuIntro;
    private MenuItem menuQuit;

    public LatronculesView(Model model, Stage stage, LatronculesRootPane rootPane) {
        super(model, stage, rootPane);
    }

    public int create_first_player_dialog(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().clear();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        List<Player> players = model.getPlayers();

        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton rbPlayer1 = new RadioButton(players.get(0).getName());
        RadioButton rbPlayer2 = new RadioButton(players.get(1).getName());
        RadioButton rbRandom = new RadioButton("Random");

        rbPlayer1.setToggleGroup(toggleGroup);
        rbPlayer2.setToggleGroup(toggleGroup);
        rbRandom.setToggleGroup(toggleGroup);

        rbPlayer1.setTextFill(Color.BLUE);
        rbPlayer2.setTextFill(Color.RED);

        rbRandom.setSelected(true);

        grid.add(rbPlayer1, 0, 0);
        grid.add(rbPlayer2, 1, 0);
        grid.add(rbRandom, 2, 0);

        ButtonType start = new ButtonType("Start");

        dialog.getDialogPane().getButtonTypes().addAll(start);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait();

        if(rbPlayer1.isSelected()){
            return 0;
        }else if(rbPlayer2.isSelected()){
            return 1;
        }else{
            Random rand = new Random();
            return rand.nextInt(2);
        }
    }

    public boolean create_choice_dialog(LatronculesController control){

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText(null);
        dialog.getDialogPane().getButtonTypes().clear();

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        //Button okButton = new Button("OK");



        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        Text redText = new Text("Red player");
        redText.setFill(Color.RED);
        Text blueText = new Text("Blue player");
        blueText.setFill(Color.BLUE);

        grid.add(redText, 0, 1);
        grid.add(blueText, 0, 0);

        TextField name1 = new TextField();
        name1.setPromptText("Player1'name");
        TextField name2 = new TextField();
        name2.setPromptText("Player2'name");

        //grid.add(new Label("Player1'name"), 0, 0);
        grid.add(name1, 1, 0);
        //grid.add(new Label("Player2'name"), 0, 1);
        grid.add(name2, 1, 1);

        CheckBox cb1=new CheckBox("Ordi");
        CheckBox cb2=new CheckBox("Ordi");

        grid.add(cb1, 2, 0);
        grid.add(cb2, 2, 1);

        String[] bots = {"Random", "Randomax", "Minimax"};

        ComboBox<String> choices1 = new ComboBox<>();
        choices1.getItems().addAll(bots);
        choices1.setPromptText("Model");

        ComboBox<String> choices2 = new ComboBox<>();
        choices2.getItems().addAll(bots);
        choices2.setPromptText("Model");

        grid.add(choices1, 3, 0);
        grid.add(choices2, 3, 1);

        //grid.add(okButton, 1, 3);

        String defaultBot1Name = "Ordi1";
        String defaultBot2Name = "Ordi2";
        String defaultPlayer1Name = "Joueur1";
        String defaultPlayer2Name = "Joueur2";

        name1.setText(defaultPlayer1Name);
        name2.setText(defaultPlayer2Name);

        cb1.selectedProperty().addListener((observable, oldValue, newValue) -> {
            choices1.setDisable(!newValue);
            if(name1.getText().equals(defaultBot1Name) && !cb1.isSelected()){
                name1.setText(defaultPlayer1Name);
            }else if(name1.getText().equals(defaultPlayer1Name) && cb1.isSelected()){
                name1.setText(defaultBot1Name);
            }
            verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);
        });

        cb2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            choices2.setDisable(!newValue);
            if(name2.getText().equals(defaultBot2Name) && !cb2.isSelected()){
                name2.setText(defaultPlayer2Name);
            }else if(name2.getText().equals(defaultPlayer2Name) && cb2.isSelected()){
                name2.setText(defaultBot2Name);
            }
            verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);
        });

        name1.textProperty().addListener((observable,oldValue, newValue) -> {
            verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);
        });

        name2.textProperty().addListener((observable,oldValue, newValue) -> {
            verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);
        });

        choices1.valueProperty().addListener((observable,oldValue, newValue) -> {
            verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);
        });

        choices2.valueProperty().addListener((observable,oldValue, newValue) -> {
            verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);
        });

        choices1.setDisable(true);
        choices2.setDisable(true);
        dialog.getDialogPane().setContent(grid);

        verif_active_button(cb1,cb2,choices1,choices2,name1,name2,okButton);


        Optional<Pair<String, String>> result = dialog.showAndWait();
        if(!result.isPresent()){
            return false;
        }

        model.getPlayers().clear();

        if(cb1.isSelected()){
            model.addComputerPlayer(name1.getText());
            control.set_decider(choices1.getValue(), 0);
        }else{
            model.addHumanPlayer(name1.getText());
            control.set_decider("", 0);

        }
        if(cb2.isSelected()){
            model.addComputerPlayer(name2.getText());
            control.set_decider(choices2.getValue(), 1);
        }else {
            model.addHumanPlayer(name2.getText());
            control.set_decider("", 1);
        }

        return true;
    }

    private void verif_active_button(CheckBox cb1, CheckBox cb2, ComboBox<String> choices1, ComboBox<String> choices2, TextField name1, TextField name2, Node okButton){
        boolean ok = true;

        if(name1.getText().isEmpty() || name2.getText().isEmpty()){
            ok = false;
        }else if((cb1.isSelected() && choices1.getValue() == null) || (cb2.isSelected() && choices2.getValue() == null)){
            ok = false;
        }else if(name1.getText().equals(name2.getText())){
            ok = false;
        }

        okButton.setDisable(!ok);


    }

    @Override
    public void createMenuBar(){
        menuBar = new MenuBar();
        Menu menu1 = new Menu("Game");
        menuStart = new MenuItem("New game");
        menuIntro = new MenuItem("Menu");
        menuQuit = new MenuItem("Quit");
        menu1.getItems().add(menuStart);
        menu1.getItems().add(menuIntro);
        menu1.getItems().add(menuQuit);
        menuBar.getMenus().add(menu1);
    }

    public MenuItem getMenuStart() {
        return menuStart;
    }

    public MenuItem getMenuIntro() {
        return menuIntro;
    }

    public MenuItem getMenuQuit() {
        return menuQuit;
    }

}
