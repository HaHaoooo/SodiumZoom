package com.haha.zoom.control;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.Control;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;

import java.awt.*;

public class KeyBindControl implements Control<KeyBinding> {

    private final Option<KeyBinding> keyOption;

    public KeyBindControl(Option<KeyBinding> keyOption) {
        this.keyOption = keyOption;
    }

    @Override
    public Option<KeyBinding> getOption() {
        return keyOption;
    }

    @Override
    public ControlElement<KeyBinding> createElement(Dim2i dim2i) {
        return new KeyBindElement(keyOption, dim2i);
    }

    @Override
    public int getMaxWidth() {
        return 130;
    }

    public static class KeyBindElement extends ControlElement<KeyBinding> {
        private boolean waitingForInput = false;
        private final Rect2i button;

        public KeyBindElement(Option<KeyBinding> option, Dim2i dim) {
            super(option, dim);
            this.button = new Rect2i(dim.getLimitX() - 16, dim.getCenterY() - 5, 10, 10);
        }

        @Override
        public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
            super.render(graphics, mouseX, mouseY, delta);
            int x = this.button.getX();
            int y = this.button.getY();
            String keyName = waitingForInput ? "..." : this.option.getValue().getBoundKeyLocalizedText().getString();
            graphics.drawText(this.font, Text.literal(keyName), x + 2, y + 2, Color.WHITE.getRGB(), false);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.dim.containsCursor(mouseX, mouseY)) {
                this.waitingForInput = true;
                return true;
            }
            return false;
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (waitingForInput) {
                KeyBinding key = new KeyBinding("key.haha.zoom", InputUtil.Type.KEYSYM, keyCode, "category.haha");
                this.option.setValue(key);
                this.waitingForInput = false;
                return true;
            }
            return false;
        }
    }
}
