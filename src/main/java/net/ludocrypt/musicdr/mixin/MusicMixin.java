package net.ludocrypt.musicdr.mixin;

import me.shedaniel.autoconfig.AutoConfig;
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
		MusicDrConfig config = AutoConfig.getConfigHolder(MusicDrConfig.class).getConfig();

		if (config.general.divide && config.general.division > 0) {
			ci.setReturnValue(Math.round((float) ci.getReturnValue() / (float) Math.abs(config.general.division)));
		} else {
			ci.setReturnValue(Math.round((float) Mth.clamp(config.general.minDelay, config.general.minDelay, config.general.maxDelay) * 20.0F));
		}
	}

	@Inject(method = "getMaxDelay", at = @At("RETURN"), cancellable = true)
	public void musicdr$getMaxDelay(CallbackInfoReturnable<Integer> ci) {
		MusicDrConfig config = AutoConfig.getConfigHolder(MusicDrConfig.class).getConfig();

		if (config.general.divide && config.general.division > 0) {
			ci.setReturnValue(Math.round((float) ci.getReturnValue() / (float) Math.abs(config.general.division)));
		} else {
			ci.setReturnValue(Math.round((float) Mth.clamp(config.general.maxDelay, config.general.minDelay, config.general.maxDelay) * 20.0F));
		}
	}
}
