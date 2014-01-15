package rolit.view.layout;

import java.awt.*;

/**
 * @author Pieter Bos
 *
 * Lays out exactly two components left and right, with a given minimum gap.
 */
public class LeftRightLayoutManager implements LayoutManager {
    private static final int DEFAULT_MINIMUM_GAP = 8;

    private int minimumGap;

    public LeftRightLayoutManager(int minimumGap) {
        this.minimumGap = minimumGap;
    }

    public LeftRightLayoutManager() {
        this(DEFAULT_MINIMUM_GAP);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int width = Math.max(minimumLayoutSize(parent).width, parent.getParent().getPreferredSize().width);
        int height = minimumLayoutSize(parent).height;
        return new Dimension(width, height);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension size1 = parent.getComponent(0).getPreferredSize();
        Dimension size2 = parent.getComponent(1).getPreferredSize();
        return new Dimension(size1.width + size2.width + minimumGap, Math.max(size1.height, size2.height));
    }

    @Override
    public void layoutContainer(Container parent) {
        int height = minimumLayoutSize(parent).height;
        Dimension size1 = parent.getComponent(0).getPreferredSize();
        Dimension size2 = parent.getComponent(1).getPreferredSize();
        parent.getComponent(0).setBounds(0, (height - size1.height) / 2, size1.width, size1.height);
        parent.getComponent(1).setBounds(parent.getSize().width - size2.width, (height - size2.height) / 2, size2.width, size2.height);
    }
}
