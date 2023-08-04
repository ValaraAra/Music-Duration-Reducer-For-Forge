package net.ludocrypt.musicdr.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.Mth;
import net.minecraft.sounds.Music;
import net.ludocrypt.musicdr.config.MusicDrConfig;

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
		if (MusicDrConfig.General.divide != null) {
			if (MusicDrConfig.General.divide.get() && MusicDrConfig.General.division.get() > 0) {
				ci.setReturnValue(Math.round((float) ci.getReturnValue() / (float) Math.abs(MusicDrConfig.General.division.get())));
			} else {
				ci.setReturnValue(Math.round((float) Mth.clamp(MusicDrConfig.General.minDelay.get(), MusicDrConfig.General.minDelay.get(), MusicDrConfig.General.maxDelay.get()) * 20.0F));
			}
		}
	}

	@Inject(method = "getMaxDelay", at = @At("RETURN"), cancellable = true)
	public void musicdr$getMaxDelay(CallbackInfoReturnable<Integer> ci) {
		if (MusicDrConfig.General.divide != null) {
			if (MusicDrConfig.General.divide.get() && MusicDrConfig.General.division.get() > 0) {
				ci.setReturnValue(Math.round((float) ci.getReturnValue() / (float) Math.abs(MusicDrConfig.General.division.get())));
			} else {
				ci.setReturnValue(Math.round((float) Mth.clamp(MusicDrConfig.General.maxDelay.get(), MusicDrConfig.General.minDelay.get(), MusicDrConfig.General.maxDelay.get()) * 20.0F));
			}
		}
	}
}
