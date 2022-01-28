package io.github.gaming32.getcurrentsong;

import com.mojang.brigadier.context.CommandContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.gaming32.getcurrentsong.mixin.MusicTrackerMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.text.Text;

public class GetCurrentSongMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("getcurrentsong");

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing...");
        ClientCommandManager.DISPATCHER.register(
            ClientCommandManager.literal("getsong")
                .executes(this::getCurrentSongCommandExecutor)
        );
        ClientCommandManager.DISPATCHER.register(
            // Simple helper command for testing
            ClientCommandManager.literal("forceplaysong")
                .executes(context -> {
                    MusicTracker musicTracker = getMusicTracker(context);
                    musicTracker.play(context.getSource().getClient().getMusicType());
                    return 0;
                })
        );
        LOGGER.info("Initialized");
	}

    private MusicTracker getMusicTracker(CommandContext<FabricClientCommandSource> context) {
        return context.getSource().getClient().getMusicTracker();
    }

    private int getCurrentSongCommandExecutor(CommandContext<FabricClientCommandSource> context) {
        MusicTracker musicTracker = getMusicTracker(context);
        SoundInstance currentSoundInstance = ((MusicTrackerMixin)musicTracker).getCurrent();
        if (currentSoundInstance == null) {
            context.getSource().sendFeedback(Text.of("No song currently playing!"));
            return 0;
        }
        Sound currentSound = currentSoundInstance.getSound();
        context.getSource().sendFeedback(Text.of(currentSound.getIdentifier().toString()));
        return 0;
    }
}
