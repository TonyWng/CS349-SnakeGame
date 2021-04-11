public class Point {
    private int x;
    private int y;
    private boolean isHead;

    public Point(int x, int y, boolean isHead){
        this.x = x;
        this.y = y;
        this.isHead = isHead;
    }

    public boolean isHead(){
        return isHead;
    }

    public void setHead(){
        isHead = true;
    }

    public void setBody(){
        isHead = false;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void translateUp(){
        this.y -= 1.0f;
    }

    public void translateRight(){
        this.x += 1.0f;
    }

    public void translateDown(){
       this.y += 1.0f;
    }

    public void translateLeft(){
        this.x -= 1.0f;
    }

    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        }
        if(!(other instanceof Point)){
            return false;
        }
        Point pointComp = (Point) other;
        if(pointComp.getX() == this.x && pointComp.getY() == this.y){
            return true;
        } else {
            return false;
        }
    }
}
