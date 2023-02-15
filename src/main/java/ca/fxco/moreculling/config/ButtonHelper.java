package ca.fxco.moreculling.config;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * Utility class for ease of porting to older Minecraft versions.
 */
public class ButtonHelper {

    public static ButtonWidget createButton(int x, int y, int width, int height, Text text, ButtonWidget.PressAction onPress) {
        return ButtonWidget.builder(text, onPress).dimensions(x, y, width, height).build();
    }
}