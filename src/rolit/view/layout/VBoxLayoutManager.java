package rolit.view.layout;

import java.awt.*;

/**
 * @author Pieter Bos
 *
 * Stacks all components vertically with a given gap. The components are left-aligned.
 */
public class VBoxLayoutManager implements LayoutManager {
    private static final int DEFAULT_GAP = 8;

    private int gap;

    public VBoxLayoutManager(int gap) {
        this.gap = gap;
    }

    public VBoxLayoutManager() {
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
        int totalHeight = 0;

        for(int i = parent.getComponentCount() - 1; i >= 0; i--) {
            if(parent.getComponent(i).getMinimumSize().width > max) {
                max = parent.getComponent(i).getMinimumSize().width;
            }

            totalHeight += parent.getComponent(i).getMinimumSize().height;
        }

        return new Dimension(max, totalHeight + (parent.getComponentCount() - 1) * gap);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int currentY = 0;

        int components = parent.getComponentCount();

        for(int i = 0; i < components; i++) {
            Component component = parent.getComponent(i);
            component.setBounds(0, currentY, component.getPreferredSize().width, component.getPreferredSize().height);

            currentY += component.getPreferredSize().height + gap;
        }
    }
}
