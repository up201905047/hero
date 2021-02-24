import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.IOSafeExtendedTerminal;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    TerminalSize terminalSize;
    Screen screen;
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
            screen.setCharacter(10, 10, TextCharacter.fromCharacter('G')[0]);
            screen.refresh();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    public void run(){
        try {
            draw();
        }
        catch (IOException e){}
    }
}
