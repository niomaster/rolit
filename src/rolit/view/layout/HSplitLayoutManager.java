package rolit.view.layout;

import java.awt.*;

public class HSplitLayoutManager implements LayoutManager {
    public enum HSplitType {
        Left,
        Right
    }

    private static final int DEFAULT_SPLIT = 24;
    private static final HSplitType DEFAULT_TYPE = HSplitType.Left;

    private HSplitType splitType;
    private int split;

    public HSplitLayoutManager(int split, HSplitType type) {
        this.splitType = type;
        this.split = split;
    }

    public HSplitLayoutManager(int split) {
        this(split, DEFAULT_TYPE);
    }

    public HSplitLayoutManager(HSplitType type) {
        this(DEFAULT_SPLIT, type);
    }

    public HSplitLayoutManager() {
        this(DEFAULT_TYPE);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getParent().getMinimumSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension size1 = parent.getComponent(0).getMinimumSize();
        Dimension size2 = parent.getComponent(1).getMinimumSize();
        return new Dimension(size1.width + size2.width, Math.max(size1.height, size2.height));
    }

    @Override
    public void layoutContainer(Container parent) {
        Dimension parentSize = parent.getSize();
        Dimension size1 = parent.getComponent(0).getPreferredSize();
        Dimension size2 = parent.getComponent(1).getPreferredSize();

        if(splitType == HSplitType.Left) {
            parent.getComponent(0).setBounds(0, 0, split, parentSize.height);
            parent.getComponent(1).setBounds(split, 0, parentSize.width - split, parentSize.height);
        } else {
            parent.getComponent(0).setBounds(0, 0, parentSize.width - split, parentSize.height);
            parent.getComponent(1).setBounds(parentSize.width - split, 0, split, parentSize.height);
        }
    }
}
