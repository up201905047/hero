import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.IOSafeExtendedTerminal;
import com.googlecode.lanterna.terminal.Terminal;

import javax.swing.*;
import java.io.IOException;

public class Game {
    TerminalSize terminalSize;
    Screen screen;
    Hero hero;
    Arena arena;
    private int width;
    private int height;


    public Game(){
        width = 40;
        height = 20;
        try {
            terminalSize = new TerminalSize(width, height);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                    .setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();

            screen = new TerminalScreen(terminal);

            screen.setCursorPosition(null);   // we don't need a cursor
            screen.startScreen();             // screens must be started
            screen.doResizeIfNecessary();     // resize screen if necessary
        } catch (IOException e) {
            e.printStackTrace();
        }

        arena = new Arena(width, height);
    }
    private void draw() throws IOException{
        screen.clear();
        arena.draw(screen.newTextGraphics());
        screen.refresh();
    }

    public void run() {
        boolean ctrl = true;
        while (ctrl) {
            try {
                draw();
                KeyStroke k = screen.readInput();
                ctrl = processKey(k);
                arena.retrieveCoins();
                if (arena.verifyMonsterCollisions()) ctrl = false;
            } catch (IOException e) {
            }
        }
        try {
            screen.close();
        } catch (IOException e) {
        }
    }

    private boolean processKey(KeyStroke key) {
        return arena.processKey(key);
    }
}
