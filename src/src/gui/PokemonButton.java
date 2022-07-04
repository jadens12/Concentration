package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Card;

public class PokemonButton extends Button {


    public Card card;
    private final Image abra = new Image(getClass().getResourceAsStream("resources/abra.png"));
    private final Image bulbasaur = new Image(getClass().getResourceAsStream("resources/bulbasaur.png"));
    private final Image charmander = new Image(getClass().getResourceAsStream("resources/charmander.png"));
    private final Image jigglypuff = new Image(getClass().getResourceAsStream("resources/jigglypuff.png"));
    private final Image meowth = new Image(getClass().getResourceAsStream("resources/meowth.png"));
    private final Image pikachu = new Image(getClass().getResourceAsStream("resources/pikachu.png"));
    private final Image pokeball = new Image(getClass().getResourceAsStream("resources/pokeball.png"));
    private final Image squirtle = new Image(getClass().getResourceAsStream("resources/squirtle.png"));
    private final Image venomoth = new Image(getClass().getResourceAsStream("resources/venomoth.png"));

    public static Image[] imageList = new Image[9];


    /**
     *
     * @param card
     */
    public PokemonButton ( Card card){
        this.card = card;
        Image image;

        imageList[0] = abra;
        imageList[1] = bulbasaur;
        imageList[2] = charmander;
        imageList[3] = jigglypuff;
        imageList[4] = meowth;
        imageList[5] = pikachu;
        imageList[6] = squirtle;
        imageList[7] = venomoth;
        imageList[8] = pokeball;

        if (card.getNumber() == 0){
            image = abra;
        }
        else if (card.getNumber() == 1){
            image = bulbasaur;
        }
        else if (card.getNumber() == 2){
            image = charmander;
        }
        else if (card.getNumber() == 3){
            image = jigglypuff;
        }
        else if (card.getNumber() == 4){
            image = meowth;
        }
        else if (card.getNumber() == 5){
            image = pikachu;
        }
        else if (card.getNumber() == 6){
            image = squirtle;
        }
        else if (card.getNumber() == 7){
            image = venomoth;
        }
        else{
            image = pokeball;
        }
        this.setGraphic(new ImageView(image));
    }

    public static Image getImage(int i){
        return imageList[i];
    }
}
