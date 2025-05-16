package com.haha.zoom.mixin;

import com.haha.zoom.manager.ZoomManager;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    @ModifyReturnValue(method = "getFov", at = @At("RETURN"))
    private float modifyFovWithZoom(float fov, @Local(argsOnly = true) float tickDelta) {
        return fov * ZoomManager.getScale(tickDelta);
    }
}
