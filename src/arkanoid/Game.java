package arkanoid;

// Daniel Cohen 209313311
// Yona Dassa 211950340


import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import java.awt.Color;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * The arkanoid.Game class initializes and runs the game. It handles the creation of balls, blocks, paddles,
 * and borders, and manages the game loop.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private KeyboardSensor keyboard;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BORDER_THICK = 33;
    private static final int BALL_RADIUS = 7;
    private static final int MAX_BLOCK_IN_ROW = 9;
    private static final int MAX_BLOCK_IN_COLUMN = 5;
    private static final int BLOCK_WIDTH = 66;
    private static final int BLOCK_HEIGHT = 33;
    private static final int PADDLE_WIDTH = 80;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_SPEED = 15;
    private static final Color PADDLE_COLOR = Color.YELLOW;
    private static final Color BACK_GROUND = new Color(0x0D0D75);

    /**
     * Constructs a new arkanoid.Game object.
     * Initializes the sprite collection, game environment, GUI, and keyboard sensor.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("arkanoid.Game", SCREEN_WIDTH, SCREEN_HEIGHT);
        this.keyboard = gui.getKeyboardSensor();
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(3);
        this.score = new Counter(0);
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite object to the sprite collection.
     *
     * @param s the sprite object to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Initializes the game by generating balls, borders, blocks, and the paddle.
     */
    public void initialize() {
        generateBall();
        generateBorders();
        generateBlocks();
        createPaddle();
        createDeathRegion();

        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        scoreIndicator.addToGame(this);
    }

    /**
     * Generates and adds balls to the game.
     */
    private void generateBall() {
        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, BALL_RADIUS, Color.WHITE, environment);
            double angle = 270 + (Math.random() * 45);
            double speed = 3;
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            ball.setVelocity(v);
            ball.addToGame(this);
        }
    }

    /**
     * Generates and adds borders to the game.
     */
    private void generateBorders() {
        Block topBorder = new Block(new Rectangle(new Point(0, 0), SCREEN_WIDTH, BORDER_THICK), Color.GRAY);
        Block rightBorder = new Block(new Rectangle(new Point(SCREEN_WIDTH - BORDER_THICK, BORDER_THICK),
                BORDER_THICK, SCREEN_HEIGHT), Color.GRAY);
        Block leftBorder = new Block(new Rectangle(new Point(0, BORDER_THICK), BORDER_THICK, SCREEN_HEIGHT),
                Color.GRAY);
        Block bottomBorder = new Block(new Rectangle(new Point(0, SCREEN_HEIGHT),
                SCREEN_WIDTH, BORDER_THICK), BACK_GROUND, true);

        topBorder.addToGame(this);
        rightBorder.addToGame(this);
        leftBorder.addToGame(this);

        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        bottomBorder.addHitListener(ballRemover);
        bottomBorder.addToGame(this);
    }

    /**
     * Generates and adds blocks to the game.
     */
    private void generateBlocks() {
        Color[] colors = {
                new Color(92, 104, 220),
                new Color(43, 183, 183),
                new Color(40, 150, 108),
                new Color(65, 1, 41),
                new Color(255, 255, 0),
                new Color(225, 163, 76)
        };

        int blockInRow = MAX_BLOCK_IN_ROW;
        int yPos = 4 * BLOCK_HEIGHT;

        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);

        for (int i = 0; i <= MAX_BLOCK_IN_COLUMN; i++) {
            int xPos = SCREEN_WIDTH - BORDER_THICK - BLOCK_WIDTH;
            for (int j = 0; j <= blockInRow; j++) {
                Block block = new Block(new Rectangle(new Point(xPos, yPos), BLOCK_WIDTH, BLOCK_HEIGHT), colors[i]);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTrackingListener);
                block.addToGame(this);
                this.remainingBlocks.increase(1);
                xPos -= BLOCK_WIDTH;
            }
            yPos += BLOCK_HEIGHT;
            blockInRow--;
        }
    }

    /**
     * Creates and adds a paddle to the game.
     */
    private void createPaddle() {
        Point paddleUpperLeft = new Point((SCREEN_WIDTH - PADDLE_WIDTH) / 2,
                SCREEN_HEIGHT - PADDLE_HEIGHT - BORDER_THICK);
        Rectangle paddleRect = new Rectangle(paddleUpperLeft, PADDLE_WIDTH, PADDLE_HEIGHT);
        Paddle paddle = new Paddle(keyboard, paddleRect, PADDLE_SPEED, PADDLE_COLOR, SCREEN_WIDTH, BORDER_THICK);
        paddle.addToGame(this);
    }

    /**
     * Runs the game by starting the game loop. The game loop updates and redraws the game at a fixed frame rate.
     */
    public void run() {
        int framesPerSecond = 30;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (true) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            d.setColor(BACK_GROUND);
            d.fillRectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            this.sprites.notifyAllTimePassed();
            this.sprites.drawAllOn(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (this.remainingBlocks.getValue() == 0 || this.remainingBalls.getValue() == 0) {
                if (this.remainingBlocks.getValue() == 0) {
                    this.score.increase(100);
                }
                gui.close();
                return;
            }
        }
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the game's sprite collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * This method terminates the program.
     * Ends the game.
     */
    public void endGame() {
        gui.close();
        System.exit(0);
    }

    /**
     * Creates and adds a death region to the game.
     * The death region is a block that removes balls when hit.
     */
    private void createDeathRegion() {
        Block deathRegion = new Block(new Rectangle(new Point(0, SCREEN_HEIGHT),
                SCREEN_WIDTH, BORDER_THICK), BACK_GROUND, true);
        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);
    }
}
