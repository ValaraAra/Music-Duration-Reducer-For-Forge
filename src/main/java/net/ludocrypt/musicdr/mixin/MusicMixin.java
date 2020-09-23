package net.ludocrypt.musicdr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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

	@Shadow
	private int field_232658_c_;
	@Shadow
	private int field_232659_d_;

	@Inject(method = "func_232664_b_", at = @At("HEAD"), cancellable = true)
	private void getMinDelay(CallbackInfoReturnable<Integer> ci) {
		boolean configLoaded = true;
		boolean divide = configLoaded ? MusicConfig.divide.get() : false;
		int divisionAmount = configLoaded ? MusicConfig.division.get() : 0;
		int new_minTime = configLoaded ? MusicConfig.maxTime.get() : field_232658_c_;
		int new_maxTime = configLoaded ? MusicConfig.maxTime.get() : field_232659_d_;
		boolean minOverMax = configLoaded ? new_minTime > new_maxTime : false;
		if (configLoaded && !minOverMax && divisionAmount > 0) {
			if (divide)
				ci.setReturnValue(field_232658_c_ / divisionAmount);
			else
				ci.setReturnValue(new_minTime * 20);
		}
	}

	@Inject(method = "func_232666_c_", at = @At("HEAD"), cancellable = true)
	private void getMaxDelay(CallbackInfoReturnable<Integer> ci) {
		boolean configLoaded = true;
		boolean divide = configLoaded ? MusicConfig.divide.get() : false;
		int divisionAmount = configLoaded ? MusicConfig.division.get() : 0;
		int new_minTime = configLoaded ? MusicConfig.minTime.get() : field_232658_c_;
		int new_maxTime = configLoaded ? MusicConfig.maxTime.get() : field_232659_d_;
		boolean minOverMax = configLoaded ? new_minTime > new_maxTime : false;
		if (configLoaded && !minOverMax && divisionAmount > 0) {
			if (divide)
				ci.setReturnValue(field_232659_d_ / divisionAmount);
			else
				ci.setReturnValue(new_maxTime * 20);
		}
	}

}
