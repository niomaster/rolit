package rolit.view.layout;

import java.awt.*;

/**
 * @author Pieter Bos
 *
 * Lays out all components horizontally with a given gap, aligned to the top.
 */
public class HBoxLayoutManager implements LayoutManager {
    private static final int DEFAULT_GAP = 8;

    private int gap;

    public HBoxLayoutManager(int gap) {
        this.gap = gap;
    }

    public HBoxLayoutManager() {
        this(DEFAULT_GAP);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        int max = 0;
        int totalWidth = 0;

        for(int i = parent.getComponentCount() - 1; i >= 0; i--) {
            if(parent.getComponent(i).getMinimumSize().height > max) {
                max = parent.getComponent(i).getMinimumSize().height;
            }

            totalWidth += parent.getComponent(i).getMinimumSize().width;
        }

        return new Dimension(totalWidth + (parent.getComponentCount() - 1) * gap, max);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int currentX = 0;

        int components = parent.getComponentCount();

        for(int i = 0; i < components; i++) {
            Component component = parent.getComponent(i);
            component.setBounds(currentX, 0, component.getPreferredSize().width, component.getPreferredSize().height);

            currentX += component.getPreferredSize().width + gap;
        }
    }
}
