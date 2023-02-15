package ca.fxco.moreculling.mixin.accessors;

import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClickableWidget.class)
public interface ClickableWidgetAccessor {
    @Accessor("x")
    void setX(int x);

    @Accessor("y")
    void setY(int y);
}
