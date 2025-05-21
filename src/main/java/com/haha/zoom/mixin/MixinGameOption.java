package com.haha.zoom.mixin;

import com.haha.zoom.data.ZoomStorage;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameOptions.class)
public class MixinGameOption {

    @ModifyReturnValue(method = "getGamma", at = @At(value = "RETURN"))
    private SimpleOption<Double> forceBright(SimpleOption<Double> original){
        if (!ZoomStorage.data.fullBright) return original;
        return new SimpleOption<>("options.gamma", SimpleOption.emptyTooltip(), (optionText, value) -> null, SimpleOption.DoubleSliderCallbacks.INSTANCE, 16.0, (value) -> {});
    }
}
