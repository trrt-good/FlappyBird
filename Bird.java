public class Bird 
{
    // x and y position of the bird
    private double xPos;
    private double yPos;

    // velocity of the bird
    private double yVel;

    // size of the bird
    private int width;
    private int height;

    private double jumpHeight = 10; 
    private double gravity = 10;

    // constructor for the bird
    public Bird(double xPos, double yPos, int width, int height, double jumpHeight, double gravity) 
    {
        this.yVel = 0;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.jumpHeight = jumpHeight;
        this.gravity = gravity;
    }

    // copy constructor for the bird
    public Bird(Bird other)
    {
        this.xPos = other.xPos;
        this.yPos = other.yPos;
        this.yVel = other.yVel;
        this.width = other.width;
        this.height = other.height;
        this.jumpHeight = other.jumpHeight;
        this.gravity = other.gravity;
    }

    // method to update the bird's position based on its velocity
    public void update(double deltaTime) 
    {
        yPos += yVel*deltaTime;
        yVel -= gravity*deltaTime;
    }

    // method to make the bird jump
    public void jump() 
    {
        if (yVel > 0)
            yVel += jumpHeight;
        else
            yVel = jumpHeight;
    }

    public boolean detectCollision(Obstacle obs) {
        // Check if the x position of the bird is within the range of the obstacle
        double obsLeftEdge = obs.getXPos() - obs.getWidth()/2;
        double obsRightEdge = obs.getXPos() + obs.getWidth()/2;
        double birdLeftEdge = xPos - width/2;
        double birdRightEdge = xPos + width/2;

        double birdTop = yPos + height/2;
        double birdBottom = yPos - height/2;
        // (birdLeftEdge < obsRightEdge && birdRightEdge > obsLeftEdge)
        if ((birdRightEdge > obsLeftEdge && birdLeftEdge < obsRightEdge) || 
            (birdLeftEdge < obsRightEdge && birdRightEdge > obsLeftEdge)) {
            // Check if the y position of the bird is within the range of the obstacle
            if (birdTop > obs.getTopHeight() || birdBottom < obs.getBottomHeight()) {
                // There is a collision
                return true;
            }
        }
        // There is no collision
        return false;
    }

    public boolean isTouchingGround(int Screenheight)
    {
        return yPos - height/2 <= 0;
    }

    // getters for the bird's position and size
    public double getXPos() { return xPos; }
    public double getYPos() { return yPos; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}