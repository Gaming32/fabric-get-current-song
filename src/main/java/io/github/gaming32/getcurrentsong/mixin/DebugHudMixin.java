package io.github.gaming32.getcurrentsong.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.gaming32.getcurrentsong.SongNameDatabase;
import io.github.gaming32.getcurrentsong.SongNameDatabase.SongInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Inject(method = "getLeftText()Ljava/util/List;", at = @At("RETURN"))
    protected void getLeftText(CallbackInfoReturnable<List<String>> ci) {
        List<String> debugLines = ci.getReturnValue();
        debugLines.add("");
        MusicTracker musicTracker = MinecraftClient.getInstance().getMusicTracker();
        SoundInstance currentSoundInstance = ((MusicTrackerMixin)musicTracker).getCurrent();
        if (currentSoundInstance == null) {
            debugLines.add("Song: No song");
            return;
        }
        Sound currentSound = currentSoundInstance.getSound();
        Identifier songId = currentSound.getIdentifier();
        debugLines.add("Song: " + songId);
        if (SongNameDatabase.isInitialized()) {
            SongInfo songInfo = SongNameDatabase.getSong(songId);
            if (songInfo != null) {
                debugLines.add("Song: " + songInfo);
            }
        }
    }
}
