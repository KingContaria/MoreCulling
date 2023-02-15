package ca.fxco.moreculling.config;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * Utility class for ease of porting to older Minecraft versions.
 */
public class TextHelper {

    public static MutableText literal(String string) {
        return Text.literal(string);
    }

    public static MutableText translatable(String key, Object... args) {
        return Text.translatable(key, args);
    }
}