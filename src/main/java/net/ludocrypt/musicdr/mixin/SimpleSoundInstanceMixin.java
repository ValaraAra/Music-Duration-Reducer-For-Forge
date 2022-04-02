package net.ludocrypt.musicdr.mixin;

import net.ludocrypt.musicdr.MusicDr;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

@Mixin(SimpleSoundInstance.class)
public class SimpleSoundInstanceMixin {

	@ModifyArg(method = "forMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/sounds/SoundSource;FFZILnet/minecraft/client/resources/sounds/SoundInstance$Attenuation;DDDZ)V"), index = 3)
	private static float musicdr$music(float in) {
		if (MusicDr.ExperimentalMusicConfig.chanceToPitchChange != null) {
			if (MusicDr.ExperimentalMusicConfig.distortPitch.get()) {
				Random random = new Random();
				if (random.nextDouble() < MusicDr.ExperimentalMusicConfig.chanceToPitchChange.get()) {
					float note = Mth.randomBetween(random, (float) Math.min(MusicDr.ExperimentalMusicConfig.minNoteChange.get(), MusicDr.ExperimentalMusicConfig.maxNoteChange.get()), (float) Math.max(MusicDr.ExperimentalMusicConfig.minNoteChange.get(), MusicDr.ExperimentalMusicConfig.maxNoteChange.get()));
					float pitch = (float) Math.pow(2.0F, note / 12.0F);
					return Mth.clamp(pitch, 0.5F, 2.0F);
				}
			}
		}
		return in;
	}

}
