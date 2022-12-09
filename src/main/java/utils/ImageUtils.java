package utils;

import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage rotate(BufferedImage bi, int degree) {
        BufferedImage biFlip;

        if (degree == 90 || degree == 270)
            biFlip = new BufferedImage(bi.getHeight(), bi.getWidth(), bi.getType());
        else if (degree == 180)
            biFlip = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
        else
            return bi;

        if (degree == 90) {
            for (int i = 0; i < bi.getWidth(); i++)
                for (int j = 0; j < bi.getHeight(); j++)
                    biFlip.setRGB(bi.getHeight() - j - 1, i, bi.getRGB(i, j));
        }

        if (degree == 180) {
            for (int i = 0; i < bi.getWidth(); i++)
                for (int j = 0; j < bi.getHeight(); j++)
                    biFlip.setRGB(bi.getWidth() - i - 1, bi.getHeight() - j - 1, bi.getRGB(i, j));
        }

        if (degree == 270) {
            for (int i = 0; i < bi.getWidth(); i++)
                for (int j = 0; j < bi.getHeight(); j++)
                    biFlip.setRGB(j, bi.getWidth() - i - 1, bi.getRGB(i, j));
        }

        bi.flush();

        return biFlip;
    }
}
