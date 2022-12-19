import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main
{
    // Window size
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Difficulty
    private static final int DIFFICULTY = 5;
    private static final int OBSTACLE_DISTANCE = 300;

    // Default bird and Obstacle objects;
    private static final Bird DEFAULT_BIRD = new Bird(WIDTH/4, HEIGHT/2, 50, 50, 300, 1200);

    // Bird and obstacle objects
    private static Bird bird;
    private static ArrayList<Obstacle> obstacles;

    // Random number generator
    private static Random rand;

    // JFrame for the window
    private static JFrame frame;
    // JPanel for the game
    private static GamePanel gamePanel;
    // Input Manager for keystrokes
    private static InputManager inputManager;

    public static void main(String[] args)
    {
        initGraphics();
        runGame();
    }

    private static Obstacle makeObstacle(int difficulty)
    {
        int speed = 200;
        int gapSize = 200-10*Math.min(Math.max(difficulty, 0), 10);
        int gapHeight = (int)Math.min(Math.max(((HEIGHT-gapSize)*rand.nextDouble())+gapSize/2, gapSize), HEIGHT-gapSize);
        int width = 80;
        return new Obstacle(speed, WIDTH+width/2, gapHeight-gapSize/2, gapHeight+gapSize/2, width);
    }

    private static void initGraphics()
    {
        // Set up the window and key listener
        inputManager = new InputManager();

        frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
         frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addKeyListener(inputManager);

        gamePanel = new GamePanel();

        frame.add(gamePanel);
    }

    private static void runGame()
    {
        double difficulty = DIFFICULTY;
        // Initialize the bird and obstacles
        bird = new Bird(DEFAULT_BIRD);
        obstacles = new ArrayList<Obstacle>();
        rand = new Random();

        // Add the first obstacle
        obstacles.add(makeObstacle((int)difficulty));

        long start = System.nanoTime();
        double deltaTime = 0;

        // Game loop
        while (true)
        {
            difficulty += 0.0000001;
            deltaTime = (System.nanoTime()-start)/1000000000.0;
            start = System.nanoTime();
            // Update the bird and obstacles
            bird.update(deltaTime);
            for (Obstacle obs : obstacles)
            {
                obs.update(deltaTime);
            }

            // Check for collisions
            for (Obstacle obs : obstacles)
            {
                if (bird.detectCollision(obs) || bird.isTouchingGround(HEIGHT))
                {
                    // There is a collision or bird goes off screen, end the game
                    endGame();
                    //break;
                }
            }

            // Check if the obstacles have moved off the screen
            boolean obstaclesFarEnough = true;
            for (int i = 0; i < obstacles.size(); i ++)
            {
                if (obstacles.get(i).isOffScreen())
                {
                    obstacles.remove(i);
                    i--;
                }
                else if (obstacles.get(i).getXPos() > WIDTH-OBSTACLE_DISTANCE)
                {
                    obstaclesFarEnough = false;
                }
            }
            if (obstaclesFarEnough)
                obstacles.add(makeObstacle((int)difficulty));

            // Render the graphics
            gamePanel.revalidate();
            gamePanel.repaint();

            // Sleep for a short time to avoid using too much CPU time
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                // Do nothing
            }
        }
    }



    private static void endGame()
    {
        // Clean up and exit the game
        frame.dispose();
        System.exit(0);
    }

    static class GamePanel extends JPanel
    {
        public GamePanel()
        {
            setBackground(new Color(200, 200, 255));
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            drawBird(bird, g);
            for (Obstacle obs : obstacles)
            {
                drawObstacle(obs, g);
            }
        }

        private void drawBird(Bird brd, Graphics g)
        {
            g.setColor(Color.YELLOW);
            g.fillRect((int)(brd.getXPos() - brd.getWidth()/2), (int)(Main.HEIGHT - brd.getYPos() - brd.getHeight()/2), brd.getWidth(), brd.getHeight());
        }

        private void drawObstacle(Obstacle obs, Graphics g)
        {
            g.setColor(Color.GREEN);
            g.fillRect((int)(obs.getXPos())-obs.getWidth()/2, 0, obs.getWidth(), Main.HEIGHT - obs.getTopHeight());
            g.fillRect((int)(obs.getXPos())-obs.getWidth()/2, Main.HEIGHT - obs.getBottomHeight(), obs.getWidth(), obs.getBottomHeight());
        }
    }

    static class InputManager implements KeyListener
    {
        public InputManager()
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                // Make the bird jump when the space key is pressed
                bird.jump();
            }
        }
    
        @Override
        public void keyReleased(KeyEvent e) {}
    
        @Override
        public void keyTyped(KeyEvent e) {}
    }
}

