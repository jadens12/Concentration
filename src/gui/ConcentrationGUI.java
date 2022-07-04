package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Card;
import model.ConcentrationModel;
import model.Observer;
import gui.PokemonButton;

import java.util.*;

/**
 * The ConcentrationGUI application is the UI for Concentration.
 *
 * @author Jaden Seaton
 */
public class ConcentrationGUI extends Application
        implements Observer< ConcentrationModel, Object > {

    private ConcentrationModel concentrationModel = new ConcentrationModel();

    ArrayList<Card> cards;
    ArrayList<PokemonButton> buttons = new ArrayList<>();
    public int moves1;
    public String statement1 = "Select your first card";

    Label moves;
    Label statement;


    /**
     * this method creates the GridPane
     * @return the GridPanes
     */
    private GridPane makeGridPane(){
        GridPane gridPane = new GridPane();

        int count1 = 0;
        Iterator<Card> iter = cards.iterator();
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col ++){
                Card card = iter.next();

                PokemonButton button = new PokemonButton(card);

                buttons.add(button);

                int finalCount = count1;
                button.setOnAction(actionEvent -> concentrationModel.selectCard(finalCount));
                count1 ++;
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    /**
     * starts constructs the layout for the game
     *
     * @param stage container (window) in which to render the UI
     *
     */
    @Override
    public void start( Stage stage ){

        System.out.println("init:   Initialize and connect to model");
        this.concentrationModel = new ConcentrationModel();
        this.concentrationModel.addObserver(this);

        cards = concentrationModel.getCards();
        moves1 = concentrationModel.getMoveCount();

        BorderPane borderPane = new BorderPane();

        HBox hBox = new HBox();
        statement = new Label(statement1);
        hBox.getChildren().add(statement);
        borderPane.setTop(hBox);

        GridPane gridPane = makeGridPane();
        borderPane.setCenter(gridPane);

        HBox hbox1 = new HBox();
        moves = new Label(moves1 + " Moves");

        Button button1 = new Button("Reset");
        button1.setOnAction(actionEvent ->
            concentrationModel.reset()
        );

        Button button2 = new Button("Undo");
        button2.setOnAction(actionEvent -> concentrationModel.undo());

        Button button3 = new Button("Cheat");
        button3.setOnAction(actionEvent -> {
            try {
                cheatStart( new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hbox1.getChildren().add(button1);
        hbox1.getChildren().add(button2);
        hbox1.getChildren().add(button3);
        hbox1.getChildren().add(moves);
        borderPane.setBottom(hbox1);

        stage.setScene(new Scene(borderPane));
        stage.setTitle("Concentration");
        stage.show();
    }

    /**
     * helper method for the cheat button
     * @param stage container (window) in which the render the UI
     *
     */
    public void cheatStart (Stage stage) {

        BorderPane borderPane = new BorderPane();

        GridPane gridPane = new GridPane();
        concentrationModel.cheat();
        ArrayList<Card> cheat = concentrationModel.getCheat();

        Iterator<Card> iter = cheat.iterator();
        for(int row = 0; row < 4; row++){
            for(int col = 0; col < 4; col ++){
                Card card = iter.next();
                PokemonButton button = new PokemonButton(card);

                button.setOnAction(actionEvent -> concentrationModel.selectCard(card.getNumber()));

                gridPane.add(button, col, row);
            }
        }

        borderPane.setCenter(gridPane);

        stage.setScene(new Scene(borderPane));
        stage.setTitle("Cheat Model");
        stage.show();
    }

    /**
     * The update makes calls to the public interface of the model components to determine the new values to display.
     *
     * @param concentrationModel the model object that knows the current board state
     * @param o null ⇒ non-cheating mode; non-null ⇒ cheating mode
     */
    @Override
    public void update( ConcentrationModel concentrationModel, Object o ) {

        moves1 = concentrationModel.getMoveCount();
        moves.setText(moves1 + " moves");

        if (concentrationModel.howManyCardsUp() == 0){
            statement1 = "Select your first card";
        }
        else if (concentrationModel.howManyCardsUp() == 1){
            statement1 = "Select your second card";
        }
        else if (concentrationModel.howManyCardsUp() == 2){
            statement1 = "No Match: Undo or select a carc";
        }
        statement.setText(statement1);

        for (int i = 0; i < buttons.size(); i ++){
            if (cards.get(i).getNumber() != -1){
                buttons.get(i).setGraphic(new ImageView(PokemonButton.getImage(cards.get(i).getNumber())));
            }
            else{
                buttons.get(i).setGraphic(new ImageView(PokemonButton.getImage(8)));
            }
        }

        if ( cards.stream()
                .allMatch( face -> face.isFaceUp() ) ) {
            System.out.println( "YOU WIN!" );
        }

    }

    /**
     * main entry point launches the JavaFX GUI.
     *
     * @param args not used
     */
    public static void main( String[] args ) {
        Application.launch( args );
    }
}
