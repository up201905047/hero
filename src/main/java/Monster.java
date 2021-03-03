import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.List;
import java.util.Random;

public class Monster extends Element{
    public Monster(int x, int y){super(x,y);}

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#EE0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if(getClass() != o.getClass()) return false;

        Monster m = (Monster) o;
        return m.getPosition().equals(position);
    }

    public Position move(){
        Random random = new Random();
        int r = random.nextInt(4);
        switch (r) {
            case 0:
                return new Position(0,  - 1);
            case 1:
                return new Position(- 1, 0);
            case 2:
                return new Position(0, 1);
            case 3:
                return new Position( 1, 0);
            default:
                return position;
            }
    }
}
