import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {
    public Position position;
    public Element(int x, int y){
        position = new Position(x,y);
    }
    public abstract void draw(TextGraphics graphics);

    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position moveUp() {
        return position.moveUp();
    }

    public Position moveDown() {
        return position.moveDown();
    }

    public Position moveLeft() {
        return position.moveLeft();
    }
    
    public Position moveRight() {
        return position.moveRight();
    }
}
