package com.haha.zoom.control;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.Control;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.Arrays;

public class KeyBindControl implements Control<Integer> {

    private final Option<Integer> keyCode;

    public KeyBindControl(Option<Integer> keyCode) {
        this.keyCode = keyCode;
    }

    @Override
    public Option<Integer> getOption() {
        return keyCode;
    }

    @Override
    public ControlElement<Integer> createElement(Dim2i dim2i) {
        return new KeyBindElement(keyCode, dim2i);
    }

    @Override
    public int getMaxWidth() {
        return 130;
    }

    public static class KeyBindElement extends ControlElement<Integer> {
        private boolean waitingForInput = false;
        private final Rect2i button;

        public KeyBindElement(Option<Integer> option, Dim2i dim) {
            super(option, dim);
            this.button = new Rect2i(dim.getLimitX() - 16, dim.getCenterY() - 5, 10, 10);
        }

        @Override
        public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
            super.render(graphics, mouseX, mouseY, delta);
            int x = this.button.getX();
            int y = this.button.getY();
            InputUtil.Key key = InputUtil.fromKeyCode(option.getValue(), 0);
            String keyName = waitingForInput ? "..." : key.getLocalizedText().getString();
            boolean conflict = isKeyConflict(key);
            int color = conflict ? Color.RED.getRGB() : Color.WHITE.getRGB();
            graphics.drawText(this.font, Text.literal(keyName), x + 2, y + 2, color, false);
            // 显示冲突提示
            if (this.button.contains(mouseX, mouseY) && conflict) {
                graphics.drawTooltip(this.font, Text.literal("键位已被其他功能占用").setStyle(Style.EMPTY.withColor(Formatting.RED)), mouseX, mouseY);
            }
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
                InputUtil.Key key = InputUtil.fromKeyCode(keyCode, scanCode);
                this.option.setValue(key.getCode());
                this.waitingForInput = false;
                return true;
            }
            return false;
        }

        private boolean isKeyConflict(InputUtil.Key targetKey) {
            return Arrays.stream(MinecraftClient.getInstance().options.allKeys)
                    .anyMatch(binding -> {
                        InputUtil.Key bound = InputUtil.fromTranslationKey(binding.getBoundKeyTranslationKey());
                        return !bound.equals(InputUtil.UNKNOWN_KEY) && bound.equals(targetKey);
                    });
        }
    }
}
