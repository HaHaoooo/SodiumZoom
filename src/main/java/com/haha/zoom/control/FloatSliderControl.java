package com.haha.zoom.control;

import com.haha.zoom.data.ZoomData;
import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.caffeinemc.mods.sodium.client.gui.options.OptionImpl;
import net.caffeinemc.mods.sodium.client.gui.options.control.Control;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class FloatSliderControl implements Control<Float> {

    private final OptionImpl<ZoomData, Float> option;

    public FloatSliderControl(OptionImpl<ZoomData, Float> option) {
        this.option = option;
    }

    @Override
    public Option<Float> getOption() {
        return option.getControl().getOption();
    }

    @Override
    public ControlElement<Float> createElement(Dim2i dim2i) {
        return new Slider(option, dim2i, 0.15f, 0.60f, 0.05f);
    }

    @Override
    public int getMaxWidth() {
        return 100;
    }

    private static class Slider extends ControlElement<Float> {
        private final Rect2i sliderBounds;
        private int contentWidth;
        private final float min;
        private final float max;
        private final float range;
        private final float interval;
        private double thumbPosition;
        private boolean sliderHeld;

        public Slider(Option<Float> option, Dim2i dim, float min, float max, float interval) {
            super(option, dim);
            this.min = min;
            this.max = max;
            this.range = max - min;
            this.interval = interval;
            this.sliderBounds = new Rect2i(dim.getLimitX() - 96, dim.getCenterY() - 5, 90, 10);
            this.sliderHeld = false;
            this.thumbPosition = getThumbPositionForValue(option.getValue());
        }

        @Override
        public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
            int sliderX = this.sliderBounds.getX();
            int sliderY = this.sliderBounds.getY();
            int sliderWidth = this.sliderBounds.getWidth();
            int sliderHeight = this.sliderBounds.getHeight();
            MutableText label = Text.literal(String.format("%.2f", this.option.getValue())).copy();
            if (!this.option.isAvailable()) {
                label.setStyle(Style.EMPTY.withColor(Formatting.GRAY).withItalic(true));
            }

            int labelWidth = this.font.getWidth(label);
            boolean drawSlider = this.option.isAvailable() && (this.hovered || this.isFocused());
            if (drawSlider) {
                this.contentWidth = sliderWidth + labelWidth;
            } else {
                this.contentWidth = labelWidth;
            }

            super.render(graphics, mouseX, mouseY, delta);
            if (drawSlider) {
                this.thumbPosition = this.getThumbPositionForValue(this.option.getValue());
                double thumbOffset = MathHelper.clamp((double) (this.getFloatValue() - this.min) / (double) this.range * (double) sliderWidth, 0.0, sliderWidth);
                int thumbX = (int) ((double) sliderX + thumbOffset - 2.0);
                int trackY = (int) ((double) ((float) sliderY + (float) sliderHeight / 2.0F) - 0.5);
                this.drawRect(graphics, thumbX, sliderY, thumbX + 4, sliderY + sliderHeight, -1);
                this.drawRect(graphics, sliderX, trackY, sliderX + sliderWidth, trackY + 1, -1);
                this.drawString(graphics, label, sliderX - labelWidth - 6, sliderY + sliderHeight / 2 - 4, -1);
            } else {
                this.drawString(graphics, label, sliderX + sliderWidth - labelWidth, sliderY + sliderHeight / 2 - 4, -1);
            }

        }

        public int getContentWidth() {
            return this.contentWidth;
        }

        private float getFloatValue() {
            float value = (float) (min + thumbPosition * range);
            float snapped = Math.round(value / interval) * interval;
            return MathHelper.clamp(snapped, min, max);
        }

        private double getThumbPositionForValue(float value) {
            return (value - min) / range;
        }

        public void setValue(double d) {
            this.thumbPosition = MathHelper.clamp(d, 0.0, 1.0);
            float value = getFloatValue();
            if (!valueEquals(this.option.getValue(), value)) {
                this.option.setValue(value);
            }
        }

        private boolean valueEquals(float a, float b) {
            return Math.abs(a - b) < 1e-5;
        }

        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.sliderHeld = false;
            if (this.option.isAvailable() && button == 0 && this.dim.containsCursor(mouseX, mouseY)) {
                if (this.sliderBounds.contains((int) mouseX, (int) mouseY)) {
                    this.setValueFromMouse(mouseX);
                    this.sliderHeld = true;
                }

                return true;
            } else {
                return false;
            }
        }

        private void setValueFromMouse(double d) {
            this.setValue((d - (double) this.sliderBounds.getX()) / (double) this.sliderBounds.getWidth());
        }

        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (!this.isFocused()) {
                return false;
            } else if (keyCode == 263) {
                this.option.setValue(MathHelper.clamp(this.option.getValue() - this.interval, this.min, this.max));
                return true;
            } else if (keyCode == 262) {
                this.option.setValue(MathHelper.clamp(this.option.getValue() + this.interval, this.min, this.max));
                return true;
            } else {
                return false;
            }
        }

        public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            if (this.option.isAvailable() && button == 0) {
                if (this.sliderHeld) {
                    this.setValueFromMouse(mouseX);
                }

                return true;
            } else {
                return false;
            }
        }
    }
}
