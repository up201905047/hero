import com.googlecode.lanterna.terminal.*;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.*;
import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }
}

