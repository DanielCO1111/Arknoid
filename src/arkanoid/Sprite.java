package arkanoid;

//Daniel Cohen 209313311
//Yona Dassa 211950340

import biuoop.DrawSurface;
/**
 * Represents a arkanoid.Sprite object that can move one step and be added to a arkanoid.Game.
 */
public interface Sprite {

    /** draw the sprite to the screen.
     * @param d the given surface.
     */
    void drawOn(DrawSurface d);

    /** Notify the sprite that time has passed.
     */
    void timePassed();

    /** Adds the sprite object to a given arkanoid.Game object.
     @param g The arkanoid.Game object to which the sprite object will be added.
     */
    void addToGame(Game g);
}
