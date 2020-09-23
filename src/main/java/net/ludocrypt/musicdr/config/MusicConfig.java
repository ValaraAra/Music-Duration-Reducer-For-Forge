package net.ludocrypt.musicdr.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MusicConfig {
	public static ForgeConfigSpec.IntValue maxTime;
	public static ForgeConfigSpec.IntValue minTime;
	public static ForgeConfigSpec.BooleanValue divide;
	public static ForgeConfigSpec.IntValue division;

	public static void init(ForgeConfigSpec.Builder client) {
		client.comment("MusicDr Config");
		maxTime = client.comment("Maximum time for next song to play").defineInRange("music.maxtimeconfig", 30, 0,
				2147483647);

		minTime = client.comment("Minimum time for next song to play").defineInRange("music.mintimeconfig", 0, 0,
				2147483647);

		divide = client.comment("Divide song length instead?").define("music.divideconfig", false);
		
		division = client.comment("Division amount").defineInRange("music.mintimeconfig", 0, 0,
				2147483647);
	}

}
