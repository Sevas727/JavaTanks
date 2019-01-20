package com.test.game;

import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage sheet;
    private int spriteCount;
    private int scale;
    private int spritesWidth;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale){
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;

        this.spritesWidth = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int index){
        index = index % spriteCount;
        int x = index % spritesWidth * scale;
        int y = index / spritesWidth * scale;

        return sheet.getSubimage(x, y, scale, scale);
    }
}
