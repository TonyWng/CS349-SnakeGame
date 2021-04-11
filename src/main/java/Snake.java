import java.util.ArrayList;
public class Snake {
    private ArrayList<Point> gameSnake;
    private Direction snakeDir;
    private int score;
    private int colNum = 1240/40;
    private int rowNum = 700/40;
    private boolean didLose;

    public Snake(){
        ArrayList<Point> initSnake = new ArrayList();
        Point headPoint = new Point(1,1,true);
        initSnake.add(headPoint);
        gameSnake = initSnake;
        didLose = false;
        this.snakeDir = Direction.RIGHT;
        score = 0;
    }

    public ArrayList<Point> getSnake(){
        return gameSnake;
    }

    public int getScore(){
        return score;
    }

    public Direction getSnakeDir(){
        return this.snakeDir;
    }

    public void setSnakeDir(Direction dir){
        this.snakeDir = dir;
    }

    private void addSnakePoint(Point newPoint){
        gameSnake.add(0, newPoint);
    }
    private boolean checkBounds(Point currHead){
        int x = currHead.getX();
        int y = currHead.getY();

        //Check Borders
        if(x < 0 || x > colNum){
            return true;
        }
        if(y < 0 || y > rowNum){
            return true;
        }

        if(gameSnake.contains(currHead)){
            return true;
        }
        return false;
    }

    public boolean checkLoss(){
        return didLose;
    }

    public void translateSnake(ArrayList<Point> fruitPoints){
        Point headPoint = gameSnake.get(0);
        if(snakeDir == Direction.RIGHT){
            Point newPos = new Point(headPoint.getX() + 1,headPoint.getY(), false);
            if(fruitPoints.contains(newPos)){
                fruitPoints.remove(newPos);
                score += 10;
            } else if(checkBounds(newPos)){
                didLose = true;
            } else {
                gameSnake.remove(gameSnake.size() - 1);
            }
            headPoint.setBody();
            Point newHead = new Point(headPoint.getX(), headPoint.getY(), true);
            newHead.translateRight();
            addSnakePoint(newHead);
        } else if(snakeDir == Direction.LEFT){
            Point newPos = new Point(headPoint.getX() - 1,headPoint.getY(), false);
            if(fruitPoints.contains(newPos)){
                fruitPoints.remove(newPos);
                score += 10;
            } else if(checkBounds(newPos)){
                didLose = true;
            } else {
                gameSnake.remove(gameSnake.size() - 1);
            }
            headPoint.setBody();
            Point newHead = new Point(headPoint.getX(), headPoint.getY(), true);
            newHead.translateLeft();
            addSnakePoint(newHead);
        } else if(snakeDir == Direction.UP){
            Point newPos = new Point(headPoint.getX(),headPoint.getY() - 1, false);
            if(fruitPoints.contains(newPos)){
                fruitPoints.remove(newPos);
                score += 10;
            } else if(checkBounds(newPos)){
                didLose = true;
            } else {
                gameSnake.remove(gameSnake.size() - 1);
            }
            headPoint.setBody();
            Point newHead = new Point(headPoint.getX(), headPoint.getY(), true);
            newHead.translateUp();
            addSnakePoint(newHead);
        } else if(snakeDir == Direction.DOWN){
            Point newPos = new Point(headPoint.getX(),headPoint.getY() + 1, false);
            if(fruitPoints.contains(newPos)){
                fruitPoints.remove(newPos);
                score += 10;
            } else if(checkBounds(newPos)){
                didLose = true;
            } else {
                gameSnake.remove(gameSnake.size() - 1);
            }
            headPoint.setBody();
            Point newHead = new Point(headPoint.getX(), headPoint.getY(), true);
            newHead.translateDown();
            addSnakePoint(newHead);
        }
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}

