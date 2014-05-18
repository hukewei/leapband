package Utilities;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

import javax.imageio.*;
import java.io.*;

/*
 * class for reflection
 */
public class CrystalCaseFactory {
    private static CrystalCaseFactory instance = null;

    private static int IMAGE_WIDTH = 262;
    private static int IMAGE_HEIGHT = 233;
    
    private BufferedImage mask;

    public static CrystalCaseFactory getInstance() {
        if (instance == null) {
            instance = new CrystalCaseFactory();
        }
        return instance;
    }

    private CrystalCaseFactory() {
        mask = createGradientMask(IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    public BufferedImage createCrystalCase(Image cover) {
        BufferedImage crystal = new BufferedImage(IMAGE_WIDTH,
                                                  IMAGE_HEIGHT,
                                                  BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = crystal.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int width = cover.getWidth(null);
        int height = cover.getHeight(null);

        float scale;

        if (width > height)
        {
            scale = (float) IMAGE_WIDTH / (float) width;
        }
        else
        {
            scale = (float) IMAGE_HEIGHT / (float) height;
        }

        int scaledWidth = (int) ((float) width * scale);
        int scaledHeight = (int) ((float) height * scale);

        int x = (IMAGE_WIDTH  - scaledWidth) / 2;
        int y = (IMAGE_HEIGHT  - scaledHeight) / 2;

        g2.drawImage(cover, x, y, scaledWidth, scaledHeight, null);

        g2.dispose();

        return crystal;
    }



    public BufferedImage createReflectedPicture(BufferedImage avatar) {
        return createReflectedPicture(avatar, mask);
    }

    public BufferedImage createReflectedPicture(BufferedImage avatar,
                                                BufferedImage alphaMask) {
        int avatarWidth = avatar.getWidth();
        int avatarHeight = avatar.getHeight();

        BufferedImage buffer = createReflection(avatar,
                                                avatarWidth, avatarHeight);

        applyAlphaMask(buffer, alphaMask, avatarWidth, avatarHeight);

        return buffer;
    }

    private void applyAlphaMask(BufferedImage buffer,
                                BufferedImage alphaMask,
                                int avatarWidth, int avatarHeight) {

        Graphics2D g2 = buffer.createGraphics();
        g2.setComposite(AlphaComposite.DstOut);
        g2.drawImage(alphaMask, null, 0, avatarHeight);
        g2.dispose();
    }

    private BufferedImage createReflection(BufferedImage avatar,
                                           int avatarWidth,
                                           int avatarHeight) {

        BufferedImage buffer = new BufferedImage(avatarWidth, avatarHeight << 1,
                                                 BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffer.createGraphics();

        g.drawImage(avatar, null, null);
        g.translate(0, avatarHeight << 1);

        AffineTransform reflectTransform = AffineTransform.getScaleInstance(1.0, -1.0);
        g.drawImage(avatar, reflectTransform, null);
        g.translate(0, -(avatarHeight << 1));

        g.dispose();

        return buffer;
    }

    public BufferedImage createGradientMask(int avatarWidth, int avatarHeight) {
        BufferedImage gradient = new BufferedImage(avatarWidth, avatarHeight,
                                                   BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gradient.createGraphics();
        GradientPaint painter = new GradientPaint(0.0f, 0.0f,
                                                  new Color(1.0f, 1.0f, 1.0f, 0.5f),
                                                  0.0f, avatarHeight / 2.0f,
                                                  new Color(1.0f, 1.0f, 1.0f, 1.0f));
        g.setPaint(painter);
        g.fill(new Rectangle2D.Double(0, 0, avatarWidth, avatarHeight));

        g.dispose();

        return gradient;
    }
}

