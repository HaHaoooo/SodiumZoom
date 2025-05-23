package com.haha.zoom.control;

import com.haha.zoom.ZoomClient;
import com.haha.zoom.gui.ColorPickerScreen;
import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.Control;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.util.Identifier;

public class ColorControl implements Control<Integer> {

    private final Option<Integer> option;

    public static final int DYNAMIC_COLOR_MARKER = 0;

    public ColorControl(Option<Integer> option) {
        this.option = option;
    }

    @Override
    public Option<Integer> getOption() {
        return option;
    }

    @Override
    public ControlElement<Integer> createElement(Dim2i dim) {
        return new ColorPickerButton(option, dim);
    }

    @Override
    public int getMaxWidth() {
        return 30; // 按钮大小，自行调整
    }

    public static class ColorPickerButton extends ControlElement<Integer> {
        private final Rect2i button;
        private final Identifier COLOR_PICKER_ICON = Identifier.of(ZoomClient.MOD_ID,"gui/color_picker.png");
        private final Identifier COLOR_PICKER_ICON_SELECTED = Identifier.of(ZoomClient.MOD_ID,"gui/color_picker_selected.png");

        public ColorPickerButton(Option<Integer> option, Dim2i dim) {
            super(option, dim);
            this.button = new Rect2i(dim.getLimitX() - 16, dim.getCenterY() - 5, 10, 10);
        }

        @Override
        public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
            super.render(ctx, mouseX, mouseY, delta);
            int x = this.button.getX();
            int y = this.button.getY();
            int w = this.button.getWidth();
            int h = this.button.getHeight();
            Identifier texture = isHovered() ? COLOR_PICKER_ICON_SELECTED : COLOR_PICKER_ICON;
            ctx.drawTexture(RenderLayer::getGuiTextured, texture, x, y, 0, 0, w, h, w, h);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (this.option.isAvailable() && button == 0 && this.dim.containsCursor(mouseX, mouseY)) {
                this.playClickSound();
                MinecraftClient.getInstance().setScreen(new ColorPickerScreen(this.option));
                return true;
            } else {
                return false;
            }
        }
    }
}