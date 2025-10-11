package com.portingdeadmods.power_armor.client.sounds;

import com.portingdeadmods.power_armor.events.ClientEvents;
import com.portingdeadmods.power_armor.registries.PASoundEvents;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JetpackSound extends AbstractTickableSoundInstance {
    private static final Map<Integer, JetpackSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final Player player;

    public JetpackSound(Player player) {
        super(PASoundEvents.JETPACK.get(), SoundSource.PLAYERS, player.getRandom());
        this.player = player;
        this.looping = true;
        PLAYING_FOR.put(player.getId(), this);
    }

    public static boolean isPlaying(int entityId) {
        return PLAYING_FOR.containsKey(entityId) && PLAYING_FOR.get(entityId) != null && !PLAYING_FOR.get(entityId).isStopped();
    }

    @Override
    public void tick() {
        Vec3 pos = this.player.position();

        this.x = (float) pos.x();
        this.y = (float) pos.y() - 10;
        this.z = (float) pos.z();

        if (!ClientEvents.isFlying(this.player)) {
            synchronized (PLAYING_FOR) {
                PLAYING_FOR.remove(this.player.getId());
                this.stop();
            }
        }
    }
}