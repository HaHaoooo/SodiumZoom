package com.haha.zoom.control;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.control.Control;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;
import java.util.List;


public class ColorControl implements Control<Color> {

    private final Option<Color> option;

    public static final List<Color> COLORS = List.of(
            Color.CYAN,
            Color.WHITE,
            Color.ORANGE,
            Color.MAGENTA,
            Color.PINK
    );

    public ColorControl(Option<Color> option) {
        this.option = option;
    }

    @Override
    public Option<Color> getOption() {
        return option;
    }

    @Override
    public ControlElement<Color> createElement(Dim2i dim2i) {
        return new ColorElement(option, dim2i);
    }

    @Override
    public int getMaxWidth() {
        return 120;
    }

    public static class ColorElement extends ControlElement<Color> {
        private final List<Color> colors = COLORS;
        private final int buttonSize = 20;
        private final int spacing = 6;

        public ColorElement(Option<Color> option, Dim2i dim) {
            super(option, dim);
        }

        @Override
        public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
            int x = this.dim.x();
            int y = this.dim.y();
            int totalWidth = colors.size() * buttonSize + (colors.size() - 1) * spacing;

            // 水平居中
            int drawX = x + (this.dim.width() - totalWidth) / 2;

            Color selected = this.option.getValue();

            // 绘制每个颜色按钮
            for (int i = 0; i < colors.size(); i++) {
                int bx = drawX + i * (buttonSize + spacing);

                boolean isHovered = mouseX >= bx && mouseX < bx + buttonSize && mouseY >= y && mouseY < y + buttonSize;
                boolean isSelected = selected != null && selected.equals(colors.get(i));

                // 按钮边框
                int borderColor = isSelected ? 0xFFFFFFFF : (isHovered ? 0xFFAAAAAA : 0xFF666666);
                ctx.fill(bx - 1, y - 1, bx + buttonSize + 1, y + buttonSize + 1, borderColor);

                // 填充颜色
                ctx.fill(bx, y, bx + buttonSize, y + buttonSize, 0xFF000000 | colors.get(i).getRGB());

                // 如果选中，加一个内框
                if (isSelected) {
                    ctx.drawBorder(bx - 2, y - 2, buttonSize + 4, buttonSize + 4, 0xFFFFCC00);
                }
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int x = this.dim.x();
            int y = this.dim.y();
            int totalWidth = colors.size() * buttonSize + (colors.size() - 1) * spacing;
            int drawX = x + (this.dim.width() - totalWidth) / 2;

            for (int i = 0; i < colors.size(); i++) {
                int bx = drawX + i * (buttonSize + spacing);
                if (mouseX >= bx && mouseX < bx + buttonSize && mouseY >= y && mouseY < y + buttonSize) {
                    this.option.setValue(colors.get(i));
                    return true;
                }
            }
            return false;
        }
    }
}
