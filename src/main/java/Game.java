import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
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
    private int x = 10;
    private int y = 10;

    public Game(){
        try {
            terminalSize = new TerminalSize(40, 20);
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
    }
    private void draw() throws IOException{
        //try {
            screen.clear();
            screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
            screen.refresh();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    public void run() {
        boolean ctrl = true;
        while (ctrl) {
            try {
                draw();
                KeyStroke k = screen.readInput();
                ctrl = processKey(k);
            } catch (IOException e) {
            }
        }
        try {
            screen.close();
        } catch (IOException e) {
        }

    }

    private boolean processKey(KeyStroke key) {
        switch (key.getKeyType()) {
            case ArrowLeft:
                x = x - 1;
                break;
            case ArrowRight:
                x += 1;
                break;
            case ArrowUp:
                y -= 1;
                break;
            case ArrowDown:
                y += 1;
                break;
            default:
        }
        if ((key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') || key.getKeyType() == KeyType.EOF)
            return false;
        else
            return true;


    }
}
