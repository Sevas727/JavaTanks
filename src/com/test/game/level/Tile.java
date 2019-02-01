package com.test.game.level;

import com.test.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private TileType type;

    protected Tile(BufferedImage image, int scale, TileType type) {
        this.type = type;
        this.image = Utils.resize(image, image.getWidth() * scale, image.getHeight() * scale);
    }

    protected void render(Graphics2D g,int x, int y){
        g.drawImage(image, x, y, null);
    }

    public void transparentRender(Graphics2D g, float x, float y){
        g.drawImage(makeImageTranslucent(image, 0.80), (int)(x), (int)(y), null);
    }

    public static BufferedImage makeImageTranslucent(BufferedImage source,
                                                     double alpha) {
        BufferedImage target = new BufferedImage(source.getWidth(),
                source.getHeight(), java.awt.Transparency.TRANSLUCENT);
        // Get the images graphics
        Graphics2D g = target.createGraphics();
        // Set the Graphics composite to Alpha
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                (float) alpha));
        // Draw the image into the prepared reciver image
        g.drawImage(source, null, 0, 0);
        // let go of all system resources in this Graphics
        g.dispose();
        // Return the image
        return target;
    }

    protected TileType type() {
        return type;
    }
}
