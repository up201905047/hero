import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class Arena {
    private int width;
    private int height;
    Hero hero;
    public Arena(int width, int height){
        this.width = width;
        this.height = height;
        hero = new Hero(10, 10);
    }

    public void draw(Screen screen){
        screen.setCharacter(hero.getX(), hero.getY(), TextCharacter.fromCharacter('X')[0]);
    }

    public boolean processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            default:
        }
        if ((key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') || key.getKeyType() == KeyType.EOF)
            return false;
        else
            return true;
    }

    public boolean canHeroMove(Position position){
        if (position.getX() < width && position.getX() >= 0 && position.getY() < height && position.getY() >= 0)
            return true;
        else
            return false;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }
}