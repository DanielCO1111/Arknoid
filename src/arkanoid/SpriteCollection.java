package arkanoid;
//Daniel Cohen 209313311
//Yona Dassa 211950340


import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
/**
 * The arkanoid.SpriteCollection class represents a collection of sprites.
 * It provides methods to add sprites to the collection,
 * notify all sprites that time has passed, and draw all sprites on a given DrawSurface.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructs a new arkanoid.SpriteCollection object and initializes an empty list of sprites.
     */

    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s The sprite to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notifies all the sprites in the collection that time has passed.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * Draws all the sprites in the collection on the given DrawSurface.
     *
     * @param d The DrawSurface to draw the sprites on.
     */

    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}
