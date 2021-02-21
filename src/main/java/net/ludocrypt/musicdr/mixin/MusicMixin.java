package net.ludocrypt.musicdr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.musicdr.config.MusicConfig;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@Mixin(BackgroundMusicSelector.class)
public class MusicMixin {

	@Inject(method = "getMinDelay", at = @At("RETURN"), cancellable = true)
	private void musicDr_getMinDelay(CallbackInfoReturnable<Integer> ci) {

		if (MusicConfig.divide.get()) {
			ci.setReturnValue(ci.getReturnValue() / MusicConfig.division.get());
		} else {
			ci.setReturnValue(Math.min(ci.getReturnValue(), Math.min(MusicConfig.minDelay.get() * 20, MusicConfig.maxDelay.get() * 20)));
		}

	}

	@Inject(method = "getMaxDelay", at = @At("RETURN"), cancellable = true)
	private void musicDr_getMaxDelay(CallbackInfoReturnable<Integer> ci) {

		if (MusicConfig.divide.get()) {
			ci.setReturnValue(ci.getReturnValue() / MusicConfig.division.get());
		} else {
			ci.setReturnValue(Math.min(ci.getReturnValue(), Math.max(MusicConfig.minDelay.get() * 20, MusicConfig.maxDelay.get() * 20)));
		}

	}

}
