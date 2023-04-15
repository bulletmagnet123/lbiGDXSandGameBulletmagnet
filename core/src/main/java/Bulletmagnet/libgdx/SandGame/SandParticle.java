package Bulletmagnet.libgdx.SandGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SandParticle {

    public Texture texture;
    public float x;
    public float y;
    public float size;

    public SandParticle(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.texture = texture;
    }

    public void update(float deltaTime) {
        // Update particle position based on gravity and other forces
    }

    public void render(SpriteBatch spriteBatch, int sandSize) {
        // Render the particle using the SpriteBatch
        spriteBatch.draw(texture, x, y);
    }

}
