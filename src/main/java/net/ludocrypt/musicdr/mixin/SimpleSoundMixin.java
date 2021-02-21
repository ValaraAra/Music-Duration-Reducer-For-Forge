package net.ludocrypt.musicdr.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.ludocrypt.musicdr.config.ExperimentalMusicConfig;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
@Mixin(value = SimpleSound.class, priority = 100)
public class SimpleSoundMixin {

	@Inject(method = "music(Lnet/minecraft/util/SoundEvent;)Lnet/minecraft/client/audio/SimpleSound;", at = @At("RETURN"), cancellable = true)
	private static void musicDr_changePitch(SoundEvent music, CallbackInfoReturnable<SimpleSound> ci) {
		Random random = new Random();
		if (ExperimentalMusicConfig.distortPitch.get() && (random.nextDouble() < ExperimentalMusicConfig.chanceToPitchChange.get())) {
			int note = random.nextInt((ExperimentalMusicConfig.maxNoteChange.get() - ExperimentalMusicConfig.minNoteChange.get()) + 1) + ExperimentalMusicConfig.minNoteChange.get();
			double newPitch = Math.pow(2.0D, (double) (note) / 12.0D);
			ci.setReturnValue(new SimpleSound(music.getName(), SoundCategory.MUSIC, 1.0F, (float) newPitch, false, 0, ISound.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true));
		}
	}

}
