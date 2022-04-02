package net.ludocrypt.musicdr.mixin;

import net.ludocrypt.musicdr.MusicDr;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Music.class)
public class MusicMixin {

	@Shadow
	@Final
	private int minDelay;

	@Shadow
	@Final
	private int maxDelay;

	@Inject(method = "getMinDelay", at = @At("RETURN"), cancellable = true)
	public void musicdr$getMinDelay(CallbackInfoReturnable<Integer> ci) {
		if (MusicDr.MusicConfig.divide != null) {
			if (MusicDr.MusicConfig.divide.get()) {
				ci.setReturnValue(ci.getReturnValue() / Math.abs(MusicDr.MusicConfig.division.get()));
			} else {
				ci.setReturnValue(Math.min(Math.abs(MusicDr.MusicConfig.minDelay.get()), Math.abs(MusicDr.MusicConfig.maxDelay.get())) * 20);
			}
		}
	}

	@Inject(method = "getMaxDelay", at = @At("RETURN"), cancellable = true)
	public void musicdr$getMaxDelay(CallbackInfoReturnable<Integer> ci) {
		if (MusicDr.MusicConfig.divide != null) {
			if (MusicDr.MusicConfig.divide.get()) {
				ci.setReturnValue(ci.getReturnValue() / Math.abs(MusicDr.MusicConfig.division.get()));
			} else {
				ci.setReturnValue(Math.max(Math.abs(MusicDr.MusicConfig.minDelay.get()), Math.abs(MusicDr.MusicConfig.maxDelay.get())) * 20);
			}
		}
	}
}
