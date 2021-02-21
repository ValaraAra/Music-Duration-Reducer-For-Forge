package net.ludocrypt.musicdr.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ExperimentalMusicConfig {

	public static ForgeConfigSpec.BooleanValue distortPitch;
	public static ForgeConfigSpec.DoubleValue chanceToPitchChange;
	public static ForgeConfigSpec.IntValue minNoteChange;
	public static ForgeConfigSpec.IntValue maxNoteChange;

	public static void init(ForgeConfigSpec.Builder client) {
		client.comment("Music Duration Reducer Experimental Config");
		distortPitch = client.comment("Change pitch/key randomly each time a song starts").define("music.experimental.distortPitch", false);
		chanceToPitchChange = client.comment("The chance for a song to change pitch/key").defineInRange("music.experimental.chanceToPitchChange", 0.3, 0.0, 1.0);
		minNoteChange = client.comment("Minimum key change (inclusive), Set lower for slower/lower music.").defineInRange("music.experimental.minNoteChange", -12, -12, 12);
		maxNoteChange = client.comment("Maximum key change (inclusive), Set higher for faster/higher music.").defineInRange("music.experimental.maxNoteChange", 12, -12, 12);
	}
}
