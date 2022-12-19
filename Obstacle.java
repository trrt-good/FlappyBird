public class Obstacle 
{
    // Field variables for the Obstacle object
    private double speed;
    private double xPos;
    private int bottomHeight;
    private int topHeight;
    private int width;

    // Constructor for the Obstacle object
    public Obstacle(double speed, double xPos, int bottomHeight, int topHeight, int width) {
        this.speed = speed;
        this.xPos = xPos;
        this.bottomHeight = bottomHeight;
        this.topHeight = topHeight;
        this.width = width;
    }

    public void update(double deltaTime)
    {
        xPos -= speed*deltaTime;
    }

    public boolean isOffScreen()
    {
        return xPos+width/2 <= 0;
    }

    // Getter and setter methods for the field variables
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }

    public double getXPos() { return xPos; }
    public void setXPos(double xPos) { this.xPos = xPos; }

    public int getBottomHeight() { return bottomHeight; }
    public void setBottomHeight(int bottomHeight) { this.bottomHeight = bottomHeight; }

    public int getTopHeight() { return topHeight; }
    public void setTopHeight(int topHeight) { this.topHeight = topHeight; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
}