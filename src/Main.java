import javax.swing.*;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.io.File;

enum LOAD_TYPE {
    HARDCODAT, KEYBOARD, FILE
}

enum DISPLAY_TYPE  {
    CONSOLA, FISIER, GUI
}

public class Main {

    public static void main(String[] args) {
        Settings.initApplication();
        Thread ConsoleThread = new Thread(new ConsoleThread());
        Thread GUIThread = new Thread(new GraphicUserInterfaceThread());
        ConsoleThread.start();
        GUIThread.start();
    }
}