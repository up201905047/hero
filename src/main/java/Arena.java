import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import javax.management.monitor.MonitorSettingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    Hero hero;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    public Arena(int width, int height){
        this.width = width;
        this.height = height;
        hero = new Hero(10, 10);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    public void draw(TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#006600"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall: walls)
            wall.draw(graphics);
        for (Coin coin: coins)
            coin.draw(graphics);
        for (Monster monster: monsters)
            monster.draw(graphics);
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
        else{
            moveMonsters();
            return true;
        }

    }

    public boolean canHeroMove(Position position){
        for (Wall wall: walls) {
            //if (wall.getPosition().getX() == position.getX() && wall.getPosition().getY() == position.getY())
            if (wall.getPosition().equals(position))
                return false;
        }
        return true;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        boolean ctrl = true;
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            ctrl = true;
            while (ctrl){
                Position p = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
                if (!p.equals(hero.getPosition()) && !coins.contains(new Coin(p.getX(), p.getY()))){
                    coins.add(new Coin(p.getX(), p.getY()));
                    ctrl = false;
                }
            }
        }
        return coins;
    }

    public void retrieveCoins(){
        for (int i = 0; i < coins.size(); i++){
            if (hero.position.equals(coins.get(i).getPosition())){
                coins.remove(i);
                return;
            }
        }
    }

    private List<Monster> createMonsters() {
        boolean ctrl = true;
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            ctrl = true;
            while (ctrl){
                Position p = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
                if (!p.equals(hero.getPosition()) && !monsters.contains(new Monster(p.getX(), p.getY())) && !coins.contains(new Coin (p.getX(), p.getY()))){
                    monsters.add(new Monster(p.getX(), p.getY()));
                    ctrl = false;
                }
            }
        }
        return monsters;
    }

    public boolean verifyMonsterCollisions(){
        for (Monster m : monsters){
            if (m.position.equals(hero.getPosition())){
                return true;
            }

        }
        return false;
    }

    private void moveMonsters() {
        boolean needNewMonster = true;
        for (Monster m : monsters){
            needNewMonster = true;
            while (needNewMonster){
                Position newPos = m.move();
                needNewMonster = false;
                for (Wall wall: walls) {
                    if (new Position(m.getPosition().getX() + newPos.getX(), m.getPosition().getY() + newPos.getY()).equals(wall.getPosition()) || coins.contains(new Coin (m.getPosition().getX() + newPos.getX(), m.getPosition().getY() + newPos.getY())))
                        needNewMonster = true;
                }
                if (!needNewMonster)
                    m.setPosition(new Position(m.getPosition().getX() + newPos.getX(), m.getPosition().getY() + newPos.getY()));
            }
        }
    }
}
