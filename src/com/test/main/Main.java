package com.test.main;

import com.test.display.Display;
import com.test.game.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args) {
        Display.create(800, 600, "Tanks", 0xff000000, 3);

        Game t = new Game();
        t.start();
    }
}
