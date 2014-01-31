package rolit.view.layout;

import java.awt.*;

/**
 * @author Pieter Bos
 *
 * Lays out exactly two components stacked vertically, splitting at a given distance from the top or bottom.
 */
public class VSplitLayoutManager implements LayoutManager {
    public enum VSplitType {
        Top,
        Bottom
    }

    private static final int DEFAULT_SPLIT = 32;
    private static final VSplitType DEFAULT_TYPE = VSplitType.Top;

    private VSplitType splitType;
    private int split;

    public VSplitLayoutManager(int split, VSplitType type) {
        this.splitType = type;
        this.split = split;
    }

    public VSplitLayoutManager(int split) {
        this(split, DEFAULT_TYPE);
    }

    public VSplitLayoutManager(VSplitType type) {
        this(DEFAULT_SPLIT, type);
    }

    public VSplitLayoutManager() {
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
        return new Dimension(Math.max(size1.width, size2.width), size1.height + size2.height);
    }

    @Override
    public void layoutContainer(Container parent) {
        Dimension parentSize = parent.getSize();
        Dimension size1 = parent.getComponent(0).getPreferredSize();
        Dimension size2 = parent.getComponent(1).getPreferredSize();

        if(splitType == VSplitType.Top) {
            parent.getComponent(0).setBounds(0, 0, parentSize.width, split);
            parent.getComponent(1).setBounds(0, split, parentSize.width, parentSize.height - split);
        } else {
            parent.getComponent(0).setBounds(0, 0, parentSize.width, parentSize.height - split);
            parent.getComponent(1).setBounds(0, parentSize.height - split, parentSize.width, split);
        }
    }
}
