package ca.fxco.moreculling.config.cloth;

import ca.fxco.moreculling.config.ButtonHelper;
import ca.fxco.moreculling.config.TextHelper;
import ca.fxco.moreculling.mixin.accessors.ClickableWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DynamicBooleanListEntry extends AbstractDynamicEntry<Boolean> {

    public DynamicBooleanListEntry(Text fieldName, Text resetButtonKey, Boolean value, Supplier<Boolean> defaultValue, @Nullable Consumer<Boolean> saveConsumer, @Nullable BiConsumer<AbstractDynamicEntry<Boolean>, Boolean> changeConsumer, Supplier<Optional<Text[]>> tooltipSupplier, boolean requiresRestart, boolean locked) {
        super(fieldName, resetButtonKey, value, defaultValue, saveConsumer, changeConsumer, tooltipSupplier, requiresRestart, locked);
        this.onChange(); // Run once on load
    }

    @Override
    public void onChange() {
        if (this.getChangeConsumer() != null)
            this.getChangeConsumer().accept(this, this.isEnabled() && this.getValue());
    }

    @Override
    protected ClickableWidget createMainWidget() {
        return ButtonHelper.createButton(0, 0, 150, 20, TextHelper.EMPTY, (widget) -> {
            if (this.isEnabled()) {
                this.setValue(!this.getValue());
                this.onChange();
            }
        });
    }

    @Override
    protected void onRender(MatrixStack matrices, int y, int x, int entryWidth, int entryHeight) {
        Window window = MinecraftClient.getInstance().getWindow();
        this.mainWidget.setMessage(this.getYesNoText(this.getValue()));
        Text displayedFieldName = this.getDisplayedFieldName();
        if (MinecraftClient.getInstance().textRenderer.isRightToLeft()) {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, displayedFieldName.asOrderedText(), (float)(window.getScaledWidth() - x - MinecraftClient.getInstance().textRenderer.getWidth(displayedFieldName)), (float)(y + 6), 16777215);
            ((ClickableWidgetAccessor) this.resetButton).setX(x);
            ((ClickableWidgetAccessor) this.mainWidget).setX(x + this.resetButton.getWidth() + 2);
        } else {
            MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, displayedFieldName.asOrderedText(), (float)x, (float)(y + 6), this.getPreferredTextColor());
            ((ClickableWidgetAccessor) this.resetButton).setX(x + entryWidth - this.resetButton.getWidth());
            ((ClickableWidgetAccessor) this.mainWidget).setX(x + entryWidth - 150);
        }
        this.mainWidget.setWidth(150 - this.resetButton.getWidth() - 2);
    }

    public Text getYesNoText(boolean bool) {
        return TextHelper.translatable("text.cloth-config.boolean.value." + bool);
    }
}
