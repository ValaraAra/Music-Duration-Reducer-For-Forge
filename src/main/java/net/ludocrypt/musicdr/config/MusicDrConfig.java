package net.ludocrypt.musicdr.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MusicDrConfig {
    private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec client_config;

    public static class General {
        public static ForgeConfigSpec.DoubleValue maxDelay;
        public static ForgeConfigSpec.DoubleValue minDelay;
        public static ForgeConfigSpec.BooleanValue divide;
        public static ForgeConfigSpec.DoubleValue division;

        public static void init(ForgeConfigSpec.Builder builder) {
            builder.push("Music Duration Reducer Config");

            minDelay = builder.comment("Minimum delay for next song to play").defineInRange("Min Delay", 0, 0, Double.MAX_VALUE);
            maxDelay = builder.comment("Maximum delay for next song to play").defineInRange("Max Delay", 30, 0, Double.MAX_VALUE);
            divide = builder.comment("Divide delays of length instead?").define("Divide", false);
            division = builder.comment("Division delay amount").defineInRange("Division", 2, 0, Double.MAX_VALUE);

            builder.pop();
        }
    }

    public static class Experimental {
        public static ForgeConfigSpec.BooleanValue distortPitch;
        public static ForgeConfigSpec.BooleanValue bellDistribution;
        public static ForgeConfigSpec.DoubleValue bellStandardDeviationReciprocal;
        public static ForgeConfigSpec.DoubleValue chanceToPitchChange;
        public static ForgeConfigSpec.DoubleValue minNoteChange;
        public static ForgeConfigSpec.DoubleValue maxNoteChange;

        public static void init(ForgeConfigSpec.Builder builder) {
            builder.push("Music Duration Reducer Experimental Config");

            distortPitch = builder.comment("Change pitch/key randomly each time a song starts").define("Distort Pitch", false);
            bellDistribution = builder.comment("Chooses pitches in a bell curve").define("Use Bell Distribution", true);
            bellStandardDeviationReciprocal = builder.comment("Reciprocal of Standard Deviation for Bell Distribution").defineInRange("Bell Standard Deviation Reciprocal", 2.0, Double.MIN_VALUE, Double.MAX_VALUE);
            chanceToPitchChange = builder.comment("The chance for a song to change pitch/key").defineInRange("Chance to Change Key", 0.3, 0.0, 1.0);
            minNoteChange = builder.comment("Minimum key change, Set lower for slower/lower music.").defineInRange("Min Key Change", -12.0D, -12.0D, 12.0D);
            maxNoteChange = builder.comment("Maximum key change, Set higher for faster/higher music.").defineInRange("Max Key Change", 12.0D, -12.0D, 12.0D);

            builder.pop();
        }
    }

    static {
        General.init(client_builder);
        Experimental.init(client_builder);

        client_config = client_builder.build();


    }
}
