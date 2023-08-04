package net.ludocrypt.musicdr;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.ludocrypt.musicdr.config.MusicDrConfig;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod("musicdr")
public class MusicDr {

	public MusicDr() {
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
				new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> AutoConfig.getConfigScreen(MusicDrConfig.class, parent).get()));
		AutoConfig.register(MusicDrConfig.class, Toml4jConfigSerializer::new);
		MinecraftForge.EVENT_BUS.register(this);
	}

}
