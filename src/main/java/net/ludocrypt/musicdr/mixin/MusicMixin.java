package net.ludocrypt.musicdr.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.musicdr.MusicDr;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;

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
			if (MusicDr.MusicConfig.divide.get() && MusicDr.MusicConfig.division.get() > 0) {
				ci.setReturnValue(Math.round((float) ci.getReturnValue() / (float) Math.abs(MusicDr.MusicConfig.division.get())));
			} else {
				ci.setReturnValue(Math.round((float) Mth.clamp(MusicDr.MusicConfig.minDelay.get(), MusicDr.MusicConfig.minDelay.get(), MusicDr.MusicConfig.maxDelay.get()) * 20.0F));
			}
		}
	}

	@Inject(method = "getMaxDelay", at = @At("RETURN"), cancellable = true)
	public void musicdr$getMaxDelay(CallbackInfoReturnable<Integer> ci) {
		if (MusicDr.MusicConfig.divide != null) {
			if (MusicDr.MusicConfig.divide.get() && MusicDr.MusicConfig.division.get() > 0) {
				ci.setReturnValue(Math.round((float) ci.getReturnValue() / (float) Math.abs(MusicDr.MusicConfig.division.get())));
			} else {
				ci.setReturnValue(Math.round((float) Mth.clamp(MusicDr.MusicConfig.maxDelay.get(), MusicDr.MusicConfig.minDelay.get(), MusicDr.MusicConfig.maxDelay.get()) * 20.0F));
			}
		}
	}
}
