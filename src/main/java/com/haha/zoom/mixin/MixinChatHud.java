package com.haha.zoom.mixin;

import com.haha.zoom.data.ZoomStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class MixinChatHud {
    @Shadow
    protected abstract boolean isChatHidden();

    @Shadow
    public abstract int getVisibleLineCount();

    @Shadow
    @Final
    private List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    public abstract double getChatScale();

    @Shadow
    protected abstract int getLineHeight();

    @Shadow
    private int scrolledLines;
    @Shadow
    @Final
    private MinecraftClient client;

    @Unique
    private static final int ANIM_TICKS = 10;       // 动画持续tick（越大越慢）
    @Unique
    private static final float ANIM_DIST = 25f;     // 最大位移（像素）

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(DrawContext context, int currentTick, int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        if (this.isChatHidden()) return;
        if (!ZoomStorage.getInstance().getData().isUseBetterChat()) return;

        int visibleLines = this.getVisibleLineCount();
        int msgCount = this.visibleMessages.size();
        if (msgCount == 0) return;

        float scale = (float) this.getChatScale();
        int winHeight = context.getScaledWindowHeight();
        float tickDelta = this.client.getRenderTickCounter().getTickProgress(true);

        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, 1.0F);
        context.getMatrices().translate(4.0F, 0.0F, 0.0F);

        int baseY = MathHelper.floor((float) (winHeight - 40) / scale);
        int lineHeight = this.getLineHeight();
        int scrollLines = this.scrolledLines;

        for (int r = 0; r + scrollLines < msgCount && r < visibleLines; ++r) {
            int idx = r + scrollLines;
            ChatHudLine.Visible visible = this.visibleMessages.get(idx);

            int age = currentTick - visible.addedTime();
            float smoothAge = age + tickDelta;

            if (!focused && smoothAge >= 200f) continue;

            float offsetX = 0f;
            if (smoothAge < ANIM_TICKS) {
                float progress = smoothAge / (float) ANIM_TICKS;
                float smooth = easeOutSine(progress);
                offsetX = (1.0f - smooth) * ANIM_DIST;
            }

            double chatOpacity = this.client.options.getChatOpacity().getValue() * 0.9 + 0.1;
            float fade = focused ? 1.0f : getMessageOpacityMultiplier((int) smoothAge);
            int msgAlpha = (int) (255.0f * fade * chatOpacity);

            if (msgAlpha > 3) {
                int y = baseY - r * lineHeight;
                context.getMatrices().push();
                context.getMatrices().translate(offsetX, 0, 50.0f);
                context.drawTextWithShadow(this.client.textRenderer, visible.content(), 0, y, ColorHelper.withAlpha(msgAlpha, -1));
                context.getMatrices().pop();
            }
        }
        context.getMatrices().pop();
        ci.cancel();
    }

    @Unique
    private static float easeOutSine(float t) {
        t = MathHelper.clamp(t, 0f, 1f);
        return (float) Math.sin(t * Math.PI * 0.5f);
    }

    @Unique
    private static float getMessageOpacityMultiplier(int age) {
        float t = age / 200.0f;
        t = 1.0f - t;
        t *= 10.0f;
        t = MathHelper.clamp(t, 0.0f, 1.0f);
        t = t * t;
        return t;
    }
}