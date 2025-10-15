package com.portingdeadmods.power_armor.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.power_armor.PowerArmor;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.PowerableMob;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = PowerArmor.MODID, value = Dist.CLIENT)
public final class PAKeybinds {
    public static final Lazy<KeyMapping> CYCLE_ATTACK_TYPE_UP = keyBind("Cycle Attack Type Up", GLFW.GLFW_KEY_K);
    public static final Lazy<KeyMapping> CYCLE_ATTACK_TYPE_DOWN = keyBind("Cycle Attack Type Down", GLFW.GLFW_KEY_M);

    public static Lazy<KeyMapping> keyBind(String name, int key) {
        return Lazy.of(() -> new KeyMapping(name, InputConstants.Type.KEYSYM, key, PowerArmor.MODNAME));
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(CYCLE_ATTACK_TYPE_UP.get());
        event.register(CYCLE_ATTACK_TYPE_DOWN.get());
    }

}
