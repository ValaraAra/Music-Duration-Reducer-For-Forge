package net.ludocrypt.musicdr;

import net.ludocrypt.musicdr.config.MusicDrConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("musicdr")
public class MusicDr {

	public MusicDr() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MusicDrConfig.client_config, "musicdr-client.toml");
		MinecraftForge.EVENT_BUS.register(this);
	}

}
