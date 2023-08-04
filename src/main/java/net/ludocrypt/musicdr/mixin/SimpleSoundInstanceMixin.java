package net.ludocrypt.musicdr.mixin;

import java.util.Random;

import me.shedaniel.autoconfig.AutoConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.ludocrypt.musicdr.config.MusicDrConfig;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

@Mixin(SimpleSoundInstance.class)
public class SimpleSoundInstanceMixin {

	@ModifyArg(method = "forMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/sounds/SoundSource;FFLnet/minecraft/util/RandomSource;ZILnet/minecraft/client/resources/sounds/SoundInstance$Attenuation;DDDZ)V"), index = 3)
	private static float musicdr$music(float in) {
		MusicDrConfig config = AutoConfig.getConfigHolder(MusicDrConfig.class).getConfig();

		if (config.experimental.distortPitch && config.experimental.chanceToPitchChange > 0) {
			RandomSource random = SoundInstance.createUnseededRandom();
			if (random.nextDouble() * 100D < config.experimental.chanceToPitchChange) {
				float note;

				if (config.experimental.bellDistribution) {
					note = (float) Mth.clamp(normal(1.0D / config.experimental.bellStandardDeviationReciprocal) * 0.25D, config.experimental.minNoteChange, config.experimental.maxNoteChange);
				} else {
					note = Mth.nextFloat(random, (float) Mth.clamp(config.experimental.minNoteChange, config.experimental.minNoteChange, config.experimental.maxNoteChange), (float) Mth.clamp(config.experimental.maxNoteChange, config.experimental.minNoteChange, config.experimental.maxNoteChange));
				}

				float pitch = (float) Math.pow(2.0F, note / 12.0F);

				return Mth.clamp(pitch, 0.5F, 2.0F);
			}
		}
		return in;
	}

	private static double normal(double sig) {
		Random random = new Random();
		double u, v, x, y, q;
		do {
			u = random.nextDouble();
			v = 1.7156 * (random.nextDouble() - 0.5);
			x = u - 0.449871;
			y = Math.abs(v) + 0.386595;
			q = Math.sqrt(x) + y * (0.19600 * y - 0.25472 * x);
		} while (q > 0.27597 && (q > 0.27846 || Math.sqrt(v) > -4.0 * Math.log(u) * Math.sqrt(u)));
		return sig * v / u;
	}

}
