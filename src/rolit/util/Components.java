package rolit.util;

import java.awt.*;

public class Components {
    public static String getTree(Component component) {
        String result = " = " + component.getClass().getName();

        Component child = component;
        Container parent = component.getParent();

        while(parent != null) {
            for(int i = 0; i < parent.getComponentCount(); i++) {
                if(parent.getComponent(i) == child) {
                    result = " > " + parent.getClass().getName() + "(" + i + ")" + result;
                    break;
                }
            }

            child = parent;
            parent = child.getParent();
        }

        return result;
    }
}
