package Bulletmagnet.libgdx.SandGame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen, InputProcessor {

    private SpriteBatch batch;
    private Texture sandTexture;
    private Pixmap pixmap;
    private int sandWidth;
    private int sandHeight;
    private int[][] sandArray;
    private OrthographicCamera camera;
    int sandSize = 5;
    private int getSandSize = 5;

    @Override
    public void show() {
        // Initialize sprite batch and sand texture
        batch = new SpriteBatch();

        sandTexture = new Texture("sand.png");

        // Initialize sand pixmap and array
        sandWidth = Gdx.graphics.getWidth();
        sandHeight = Gdx.graphics.getHeight();

        pixmap = new Pixmap(sandWidth, sandHeight, Pixmap.Format.RGBA8888);
        sandArray = new int[sandWidth][sandHeight];

        // Set background color
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        // Draw initial sand texture
        batch.begin();
        for (int x = 0; x < sandWidth; x++) {
            for (int y = 0; y < sandHeight; y++) {
                if (MathUtils.randomBoolean(0.1f)) {
                    batch.draw(sandTexture, x, y);
                    sandArray[x][y] = 1;
                }
            }
        }

        batch.end();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, sandWidth, sandHeight);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        // Update sand
        updateSand();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Draw sand
        batch.begin();
        for (int x = 0; x < sandWidth; x++) {
            for (int y = 0; y < sandHeight; y++) {
                if (sandArray[x][y] == 1) {
                    batch.draw(sandTexture, x * sandSize, y * sandSize, sandSize, sandSize);
                }
            }
        }
        int centerX = sandArray.length / 2;
        int centerY = sandArray[0].length / 2;
        sandArray[centerX][centerY] = 1;
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 worldPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            int sandX = (int)(worldPos.x / sandSize);
            int sandY = (int)(worldPos.y / sandSize);
            if (sandX >= 0 && sandX < sandArray.length && sandY >= 0 && sandY < sandArray[0].length) {
                sandArray[sandX][sandY] = 1;
            }
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        // Dispose of resources
        batch.dispose();
        sandTexture.dispose();
        pixmap.dispose();
    }

    private void updateSand() {
        // Loop over each sand particle from bottom to top
        for (int y = 1; y < sandHeight; y++) {
            for (int x = 0; x < sandWidth; x++) {
                if (sandArray[x][y] == 1) {
                    // Check if the space below the sand particle is empty
                    if (sandArray[x][y - 1] == 0) {
                        // Move the sand particle down
                        sandArray[x][y] = 0;
                        sandArray[x][y - 1] = 1;
                    } else {
                        // Check if the space to the left or right of the sand particle is empty
                        if (x > 0 && sandArray[x - 1][y - 1] == 0 && MathUtils.randomBoolean(0.5f)) {
                            // Move the sand particle to the left
                            sandArray[x][y] = 0;
                            sandArray[x - 1][y - 1] = 1;
                        } else if (x < sandWidth - 1 && sandArray[x + 1][y - 1] == 0 && MathUtils.randomBoolean(0.5f)) {
                            // Move the sand particle to the right
                            sandArray[x][y] = 0;
                            sandArray[x + 1][y - 1] = 1;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convert the mouse click position to game world coordinates
        Vector3 worldPos = camera.unproject(new Vector3(screenX, screenY, 0));


        // Calculate the index of the sand particle that was clicked
        int sandX = (int) Math.floor(worldPos.x);
        int sandY = (int) Math.floor(worldPos.y);

        // Set the clicked sand particle to active
        if (sandX >= 0 && sandX < sandWidth && sandY >= 0 && sandY < sandHeight) {
            sandArray[sandX][sandY] = 1;
        }
        System.out.println(worldPos.x);
        System.out.println(worldPos.y);
        System.out.println(sandArray[sandX][sandY]);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
