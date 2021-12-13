package net.ludocrypt.musicdr.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.musicdr.config.ExperimentalMusicConfig;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
@Mixin(value = SimpleSoundInstance.class, priority = 100)
public class SimpleSoundInstanceMixin {

	@Inject(method = "forMusic(Lnet/minecraft/sounds/SoundEvent;)Lnet/minecraft/client/resources/sounds/SimpleSoundInstance;", at = @At("RETURN"), cancellable = true)
	private static void musicDr$changePitch(SoundEvent music, CallbackInfoReturnable<SimpleSoundInstance> ci) {
		Random random = new Random();
		if (ExperimentalMusicConfig.distortPitch.get() && (random.nextDouble() < ExperimentalMusicConfig.chanceToPitchChange.get())) {
			int note = random.nextInt((ExperimentalMusicConfig.maxNoteChange.get() - ExperimentalMusicConfig.minNoteChange.get()) + 1) + ExperimentalMusicConfig.minNoteChange.get();
			double newPitch = Math.pow(2.0D, (double) (note) / 12.0D);
			ci.setReturnValue(new SimpleSoundInstance(music.getLocation(), SoundSource.MUSIC, 1.0F, (float) newPitch, false, 0, SoundInstance.Attenuation.NONE, 0.0D, 0.0D, 0.0D, true));
		}
	}

}
