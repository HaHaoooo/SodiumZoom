package com.haha.zoom.gui;

import net.caffeinemc.mods.sodium.client.gui.options.Option;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.awt.Color;

@Environment(EnvType.CLIENT)
public class ColorPickerScreen extends Screen {
    private final Option<Integer> option;
    private final int original;

    // HSVA
    private float hue, sat, bri;
    private boolean draggingWheel = false;

    private static final int WHEEL_DIAM = 150;
    private static final int SLW = 150, SLH = 12;
    private static final int MARGIN = 10;

    public ColorPickerScreen(Option<Integer> option) {
        super(Text.literal("Pick a Color"));
        this.option = option;
        this.original = option.getValue() == null ? 0xFFFFFFFF : option.getValue();

        Color c = new Color(this.original, true);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        this.hue = hsb[0];
        this.sat = hsb[1];
        this.bri = hsb[2];
    }

    @Override
    protected void init() {
        super.init();
        int cx = width / 2;
        int wy = height / 3;

        // 亮度滑条
        addDrawableChild(new SliderWidget(
                cx - SLW / 2, wy + WHEEL_DIAM + MARGIN,
                SLW, SLH,
                Text.literal("Brightness"),
                bri
        ) {
            @Override
            protected void applyValue() {
                bri = (float) value;
            }
            @Override
            protected void updateMessage() {
            }
        });

        // Cancel 按钮
        addDrawableChild(
                ButtonWidget.builder(Text.literal("Cancel"), btn -> {
                                    option.setValue(original);
                                    MinecraftClient.getInstance().setScreen(null);
                                }
                        )
                        .dimensions(cx - 100, wy + WHEEL_DIAM + MARGIN + 2 * (SLH + MARGIN), 80, 20)
                        .build()
        );
        // Confirm 按钮
        addDrawableChild(
                ButtonWidget.builder(Text.literal("Confirm"), btn -> {
                                    int rgb = Color.HSBtoRGB(hue, sat, bri) & 0xFFFFFF;
                                    option.setValue(rgb);
                                    option.applyChanges();
                                    option.getStorage().save();
                                    MinecraftClient.getInstance().setScreen(null);
                                }
                        )
                        .dimensions(cx + 20, wy + WHEEL_DIAM + MARGIN + 2 * (SLH + MARGIN), 80, 20)
                        .build()
        );
    }


    @Override
    public void render(DrawContext ctx, int mx, int my, float delta) {
        int cx = width / 2, cy = height / 3 + WHEEL_DIAM / 2;
        super.render(ctx, mx, my, delta);
        renderColorWheel(ctx, cx, cy);
        drawCrosshair(ctx, cx, cy);
    }

    private void renderColorWheel(DrawContext ctx, int cx, int cy) {
        int r = WHEEL_DIAM / 2;
        float alpha = 1.0f;
        int aMask = ((int) (alpha * 255) << 24) & 0xFF000000;
        for (int dy = -r; dy <= r; dy++) {
            for (int dx = -r; dx <= r; dx++) {
                float dist = (float) Math.hypot(dx, dy);
                if (dist <= r) {
                    float satVal = dist / r;
                    float hueVal = (float) ((Math.atan2(dy, dx) / (2 * Math.PI) + 1) % 1.0);
                    int rgb = Color.HSBtoRGB(hueVal, satVal, bri) & 0x00FFFFFF;
                    ctx.fill(cx + dx, cy + dy, cx + dx + 1, cy + dy + 1, aMask | rgb);
                }
            }
        }
    }

    private void drawCrosshair(DrawContext ctx, int cx, int cy) {
        double ang = hue * 2 * Math.PI;
        double rad = sat * ((WHEEL_DIAM / 2f) - 2);
        int px = (int) (cx + rad * Math.cos(ang));
        int py = (int) (cy + rad * Math.sin(ang));
        ctx.fill(px - 10, py, px + 10, py + 1, 0xFFFFFFFF);
        ctx.fill(px, py - 10, px + 1, py + 10, 0xFFFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        if (btn == 0) {
            int cx = width / 2, cy = height / 3 + WHEEL_DIAM / 2;
            float dx = (float) (mx - cx), dy = (float) (my - cy);
            if (Math.hypot(dx, dy) <= WHEEL_DIAM / 2f) {
                draggingWheel = true;
                updateHueSat(dx, dy);
                return true;
            }
        }
        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if (draggingWheel) {
            int cx = width / 2, cy = height / 3 + WHEEL_DIAM / 2;
            updateHueSat((float) (mx - cx), (float) (my - cy));
            return true;
        }
        return super.mouseDragged(mx, my, btn, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int btn) {
        draggingWheel = false;
        return super.mouseReleased(mx, my, btn);
    }

    private void updateHueSat(float dx, float dy) {
        hue = ((float) (Math.atan2(dy, dx) / (2 * Math.PI)) + 1f) % 1f;
        sat = Math.min((float) Math.hypot(dx, dy) / (WHEEL_DIAM / 2f), 1f);
    }
}