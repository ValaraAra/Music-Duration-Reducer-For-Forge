package net.ludocrypt.musicdr;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MusicConfig {
	public static ForgeConfigSpec.IntValue maxDelay;
	public static ForgeConfigSpec.IntValue minDelay;
	public static ForgeConfigSpec.BooleanValue divide;
	public static ForgeConfigSpec.IntValue division;

	public static void init(ForgeConfigSpec.Builder client) {
		client.comment("Music Duration Reducer Config");
		minDelay = client.comment("Minimum delay for next song to play").defineInRange("music.mindelay", 0, 0,
				2147483647);
		maxDelay = client.comment("Maximum delay for next song to play").defineInRange("music.maxdelay", 30, 0,
				2147483647);
		divide = client.comment("Divide delays of length instead?").define("music.divide", false);
		division = client.comment("Division delay amount").defineInRange("music.division", 2, 0,
				2147483647);
	}
}
