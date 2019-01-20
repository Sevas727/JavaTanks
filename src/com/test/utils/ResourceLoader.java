package com.test.utils;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ResourceLoader {

    public static final String PATH = "res/";
    public static BufferedImage loadImage(String fileName){

        BufferedImage image = null;

        try {
            image = ImageIO.read( new File(PATH + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }
}
