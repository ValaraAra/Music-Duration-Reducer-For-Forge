package net.ludocrypt.musicdr;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("musicdr")
public class MusicDr {

	public MusicDr() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MusicDrConfig.client_config);
		MusicDrConfig.loadConfig(MusicDrConfig.client_config, FMLPaths.CONFIGDIR.get().resolve("musicdr-client.toml").toString());
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static class MusicDrConfig {
		private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();
		public static final ForgeConfigSpec client_config;

		public static void loadConfig(ForgeConfigSpec config, String path) {
			final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
			file.load();
			config.setConfig(file);
		}

		static {
			MusicConfig.init(client_builder);
			ExperimentalMusicConfig.init(client_builder);
			client_config = client_builder.build();
		}
	}

	@Mod.EventBusSubscriber
	public static class MusicConfig {
		public static ForgeConfigSpec.DoubleValue maxDelay;
		public static ForgeConfigSpec.DoubleValue minDelay;
		public static ForgeConfigSpec.BooleanValue divide;
		public static ForgeConfigSpec.DoubleValue division;

		public static void init(ForgeConfigSpec.Builder client) {
			client.comment("Music Duration Reducer Config");
			minDelay = client.comment("Minimum delay for next song to play").defineInRange("music.mindelay", 0, 0, Double.MAX_VALUE);
			maxDelay = client.comment("Maximum delay for next song to play").defineInRange("music.maxdelay", 30, 0, Double.MAX_VALUE);
			divide = client.comment("Divide delays of length instead?").define("music.divide", false);
			division = client.comment("Division delay amount").defineInRange("music.division", 2, 0, Double.MAX_VALUE);
		}
	}

	@Mod.EventBusSubscriber
	public static class ExperimentalMusicConfig {

		public static ForgeConfigSpec.BooleanValue distortPitch;
		public static ForgeConfigSpec.BooleanValue bellDistribution;
		public static ForgeConfigSpec.DoubleValue bellStandardDeviationReciprocal;
		public static ForgeConfigSpec.DoubleValue chanceToPitchChange;
		public static ForgeConfigSpec.DoubleValue minNoteChange;
		public static ForgeConfigSpec.DoubleValue maxNoteChange;

		public static void init(ForgeConfigSpec.Builder client) {
			client.comment("Music Duration Reducer Experimental Config");
			distortPitch = client.comment("Change pitch/key randomly each time a song starts").define("music.experimental.distortPitch", false);
			bellDistribution = client.comment("Chooses pitches in a bell curve").define("music.experimental.bellDistribution", true);
			bellStandardDeviationReciprocal = client.comment("Reciprocal of Standard Deviation for Bell Distrobution").defineInRange("music.experimental.bellStandardDeviationReciprocal", 2.0, Double.MIN_VALUE, Double.MAX_VALUE);
			chanceToPitchChange = client.comment("The chance for a song to change pitch/key").defineInRange("music.experimental.chanceToPitchChange", 0.3, 0.0, 1.0);
			minNoteChange = client.comment("Minimum key change, Set lower for slower/lower music.").defineInRange("music.experimental.minNoteChange", -12.0D, -12.0D, 12.0D);
			maxNoteChange = client.comment("Maximum key change, Set higher for faster/higher music.").defineInRange("music.experimental.maxNoteChange", 12.0D, -12.0D, 12.0D);
		}
	}

}
