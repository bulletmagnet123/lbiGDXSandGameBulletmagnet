package Bulletmagnet.libgdx.SandGame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import javax.swing.text.html.parser.DTD;


public class FallingSandGame extends Game {

    private Screen gameScreen;

    @Override
    public void create() {
        gameScreen = new GameScreen();
        setScreen(gameScreen);
    }


    @Override
    public void dispose() {
        gameScreen.dispose();

    }


}
