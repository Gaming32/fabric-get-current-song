package io.github.gaming32.getcurrentsong;

import com.mojang.brigadier.context.CommandContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

public class GetCurrentSongMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("getcurrentsong");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing...");
        ClientCommandManager.DISPATCHER.register(
            ClientCommandManager.literal("getsong")
                .executes(this::getCurrentSongCommandExecutor)
        );
        LOGGER.info("Initialized");
	}

    private int getCurrentSongCommandExecutor(CommandContext<FabricClientCommandSource> context) {
        LOGGER.info("getsong run");
        return 0;
    }
}
