package utils;

import java.awt.image.BufferedImage;

public class ImageUtils {
    public static BufferedImage rotate(BufferedImage bi, int degree){
        int width = bi.getWidth();
        int height = bi.getHeight();

        BufferedImage biFlip;

        if (degree == 90 || degree == 270)
            biFlip = new BufferedImage(height, width, bi.getType());
        else if (degree == 180)
            biFlip = new BufferedImage(width, height, bi.getType());
        else
            return bi;

        if (degree == 90) {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    biFlip.setRGB(height - j - 1, i, bi.getRGB(i, j));
        }

        if (degree == 180) {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    biFlip.setRGB(width - i - 1, height - j - 1, bi.getRGB(i, j));
        }

        if (degree == 270) {
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                    biFlip.setRGB(j, width - i - 1, bi.getRGB(i, j));
        }

        bi.flush();

        return biFlip;
    }
}
