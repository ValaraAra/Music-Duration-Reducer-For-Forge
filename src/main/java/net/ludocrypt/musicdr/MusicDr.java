package net.ludocrypt.musicdr;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("musicdr")
public class MusicDr {

	public MusicDr() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MusicDrConfig.client_config);
		MusicDrConfig.loadConfig(MusicDrConfig.client_config,
				FMLPaths.CONFIGDIR.get().resolve("musicdr-client.toml").toString());
		MinecraftForge.EVENT_BUS.register(this);
	}

}
