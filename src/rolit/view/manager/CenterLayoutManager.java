package rolit.view.manager;

import java.awt.*;
import java.util.LinkedList;

/**
 * @author Pieter Bos
 *
 * Always centers a single component.
 */
public class CenterLayoutManager implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Component component = parent.getComponent(0);
        return component.getMinimumSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        Dimension parentSize = parent.getSize();
        Component component = parent.getComponent(0);
        Dimension componentSize = component.getPreferredSize();
        component.setBounds((parentSize.width - componentSize.width) / 2, (parentSize.height - componentSize.height) / 2, componentSize.width, componentSize.height);
    }
}
