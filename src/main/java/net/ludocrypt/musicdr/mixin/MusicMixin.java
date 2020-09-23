package net.ludocrypt.musicdr.mixin;

import net.ludocrypt.musicdr.MusicConfig;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(BackgroundMusicSelector.class)
public class MusicMixin {

	@Shadow
	private int minDelay;
	@Shadow
	private int maxDelay;

	@Inject(method = "getMinDelay", at = @At("HEAD"), cancellable = true)
	private void getMinDelay(CallbackInfoReturnable<Integer> ci) {
		boolean divide = MusicConfig.divide.get();
		int divisionAmount = MusicConfig.division.get();
		int new_minDelay = MusicConfig.minDelay.get();
		int new_maxDelay = MusicConfig.maxDelay.get();
		boolean minOverMax = new_minDelay > new_maxDelay ? true : false;
		if (!minOverMax && divisionAmount > 0) {
			if (divide)
				ci.setReturnValue(minDelay / divisionAmount);
			else
				ci.setReturnValue(new_minDelay * 20);
		}
	}

	@Inject(method = "getMaxDelay", at = @At("HEAD"), cancellable = true)
	private void getMaxDelay(CallbackInfoReturnable<Integer> ci) {
		boolean divide = MusicConfig.divide.get();
		int divisionAmount = MusicConfig.division.get();
		int new_minDelay = MusicConfig.minDelay.get();
		int new_maxDelay = MusicConfig.maxDelay.get();
		boolean minOverMax = new_minDelay > new_maxDelay ? true : false;
		if (!minOverMax && divisionAmount > 0) {
			if (divide)
				ci.setReturnValue(maxDelay / divisionAmount);
			else
				ci.setReturnValue(new_maxDelay * 20);
		}
	}
}
