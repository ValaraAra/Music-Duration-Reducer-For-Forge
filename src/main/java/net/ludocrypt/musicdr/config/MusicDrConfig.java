package net.ludocrypt.musicdr.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "musicdr")
@Config.Gui.Background(value = "minecraft:textures/block/stone.png")
public class MusicDrConfig implements ConfigData {

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.TransitiveObject
    public General general = new General();

    @ConfigEntry.Category("experimental")
    @ConfigEntry.Gui.TransitiveObject
    public Experimental experimental = new Experimental();

    public static class General {
        @ConfigEntry.Gui.Tooltip()
        public double minDelay = 0;
        @ConfigEntry.Gui.Tooltip()
        public double maxDelay = 30;
        @ConfigEntry.Gui.Tooltip()
        public boolean divide = false;
        @ConfigEntry.Gui.Tooltip()
        public double division = 2;
    }

    public static class Experimental {
        @ConfigEntry.Gui.Tooltip()
        public boolean distortPitch = false;
        @ConfigEntry.Gui.Tooltip()
        public boolean bellDistribution = true;
        @ConfigEntry.Gui.Tooltip()
        public double bellStandardDeviationReciprocal = 2.0;
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
        public int chanceToPitchChange = 30;
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.BoundedDiscrete(min = -12, max = 12)
        public int minNoteChange = -12;
        @ConfigEntry.Gui.Tooltip()
        @ConfigEntry.BoundedDiscrete(min = -12, max = 12)
        public int maxNoteChange = 12;
    }

    @Override
    public void validatePostLoad() {
        if (general.minDelay < 0) general.minDelay = 0;
        if (general.maxDelay < 0) general.maxDelay = 0;
        if (general.division < 0) general.division = Double.MIN_VALUE;

        if (experimental.chanceToPitchChange < 0) experimental.chanceToPitchChange = 0;
        if (experimental.minNoteChange < -12) experimental.minNoteChange = -12;
        if (experimental.maxNoteChange < -12) experimental.maxNoteChange = -12;
        if (experimental.minNoteChange > 12) experimental.minNoteChange = 12;
        if (experimental.maxNoteChange > 12) experimental.maxNoteChange = 12;
    }
}
