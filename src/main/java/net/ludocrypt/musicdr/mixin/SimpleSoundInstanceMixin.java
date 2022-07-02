package net.ludocrypt.musicdr.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import net.ludocrypt.musicdr.MusicDr;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.Mth;

@Mixin(SimpleSoundInstance.class)
public class SimpleSoundInstanceMixin {

	@ModifyArg(method = "forMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/sounds/SoundSource;FFZILnet/minecraft/client/resources/sounds/SoundInstance$Attenuation;DDDZ)V"), index = 3)
	private static float musicdr$music(float in) {
		if (MusicDr.ExperimentalMusicConfig.chanceToPitchChange != null) {
			if (MusicDr.ExperimentalMusicConfig.distortPitch.get()) {
				Random random = new Random();
				if (random.nextDouble() < MusicDr.ExperimentalMusicConfig.chanceToPitchChange.get()) {
					float note;

					if (MusicDr.ExperimentalMusicConfig.bellDistribution.get()) {
						note = (float) Mth.clamp(normal(1.0D / MusicDr.ExperimentalMusicConfig.bellStandardDeviationReciprocal.get()) * 0.25D, MusicDr.ExperimentalMusicConfig.minNoteChange.get(), MusicDr.ExperimentalMusicConfig.maxNoteChange.get());
					} else {
						note = Mth.m_14068_(random, (float) Mth.clamp(MusicDr.ExperimentalMusicConfig.minNoteChange.get(), MusicDr.ExperimentalMusicConfig.minNoteChange.get(), MusicDr.ExperimentalMusicConfig.maxNoteChange.get()), (float) Mth.clamp(MusicDr.ExperimentalMusicConfig.maxNoteChange.get(), MusicDr.ExperimentalMusicConfig.minNoteChange.get(), MusicDr.ExperimentalMusicConfig.maxNoteChange.get()));
					}

					float pitch = (float) Math.pow(2.0F, note / 12.0F);

					return Mth.clamp(pitch, 0.5F, 2.0F);
				}
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
