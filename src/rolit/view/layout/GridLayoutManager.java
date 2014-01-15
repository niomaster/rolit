package rolit.view.layout;

import java.awt.*;

public class GridLayoutManager implements LayoutManager {
    private static final int DEFAULT_GAP = 8;

    private int width;
    private int height;
    private int gap;

    public GridLayoutManager(int width, int height, int gap) {
        this.width = width;
        this.height = height;
        this.gap = gap;
    }

    public GridLayoutManager(int width, int height) {
        this(width, height, DEFAULT_GAP);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int[] x = new int[width + 1];
        int[] y = new int[height + 1];

        for(int i = 0; i < height; i++) {
            int maxHeight = 0;

            for(int j = 0; j < width; j++) {
                Dimension size = parent.getComponent(j + i * width).getPreferredSize();
                if(size.height > maxHeight) {
                    maxHeight = size.height;
                }
            }

            y[i + 1] = y[i] + maxHeight;
        }

        for(int i = 0; i < width; i++) {
            int maxWidth = 0;

            for(int j = 0; j < height; j++) {
                Dimension size = parent.getComponent(i + j * width).getPreferredSize();
                if(size.width > maxWidth) {
                    maxWidth = size.width;
                }
            }

            x[i + 1] = x[i] + maxWidth;
        }

        return new Dimension(x[width] + (width - 1) * gap, y[height] + (height - 1) * gap);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int[] x = new int[width + 1];
        int[] y = new int[height + 1];

        for(int i = 0; i < height; i++) {
            int maxHeight = 0;

            for(int j = 0; j < width; j++) {
                Dimension size = parent.getComponent(j + i * width).getPreferredSize();
                if(size.height > maxHeight) {
                    maxHeight = size.height;
                }
            }

            y[i + 1] = y[i] + maxHeight + gap;
        }

        for(int i = 0; i < width; i++) {
            int maxWidth = 0;

            for(int j = 0; j < height; j++) {
                Dimension size = parent.getComponent(i + j * width).getPreferredSize();
                if(size.width > maxWidth) {
                    maxWidth = size.width;
                }
            }

            x[i + 1] = x[i] + maxWidth + gap;
        }

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                parent.getComponent(i + j * width).setBounds(x[i], y[j], x[i + 1] - x[i] - gap, y[j + 1] - y[j] - gap);
            }
        }
    }
}
