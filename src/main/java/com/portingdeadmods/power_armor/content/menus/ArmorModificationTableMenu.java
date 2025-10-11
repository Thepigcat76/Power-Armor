package com.portingdeadmods.power_armor.content.menus;

import com.portingdeadmods.portingdeadlibs.api.gui.menus.PDLAbstractContainerMenu;
import com.portingdeadmods.power_armor.content.blockentities.ArmorModificationTableBlockEntity;
import com.portingdeadmods.power_armor.registries.PAMenuTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArmorModificationTableMenu extends PDLAbstractContainerMenu<ArmorModificationTableBlockEntity> {
    private final List<ArmorSlot> armorSlots;
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final Map<EquipmentSlot, ResourceLocation> TEXTURE_EMPTY_SLOTS = Map.of(
            EquipmentSlot.FEET,
            InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
            EquipmentSlot.LEGS,
            InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
            EquipmentSlot.CHEST,
            InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
            EquipmentSlot.HEAD,
            InventoryMenu.EMPTY_ARMOR_SLOT_HELMET
    );

    public ArmorModificationTableMenu(int containerId, Inventory inv, FriendlyByteBuf byteBuf) {
        this(containerId, inv, (ArmorModificationTableBlockEntity) inv.player.level().getBlockEntity(byteBuf.readBlockPos()));
    }

    public ArmorModificationTableMenu(int containerId, @NotNull Inventory inv, @NotNull ArmorModificationTableBlockEntity blockEntity) {
        super(PAMenuTypes.ARMOR_MODIFICATION_TABLE.get(), containerId, inv, blockEntity);

        this.armorSlots = new ArrayList<>();

        addSlot(new SlotItemHandler(blockEntity.getItemHandler(), 0, 80, 57));

        for (int i = 0; i < 4; i++) {
            int x = 22 + (i == 0 || i == 3 ? 4 : 0);
            addSlot(new SlotItemHandler(blockEntity.getItemHandler(), i + 1, x, 18 + i * 26));
        }

        for (int i = 0; i < 4; i++) {
            int x = 138 - (i == 0 || i == 3 ? 4 : 0);
            addSlot(new SlotItemHandler(blockEntity.getItemHandler(), i + 5, x, 18 + i * 26));
        }

        addPlayerInventory(inv, 132);
        addPlayerHotbar(inv, 190);

        NonNullList<ItemStack> armor = inv.armor;
        int index = this.slots.stream().mapToInt(Slot::getSlotIndex).max().getAsInt();
        for (int i = 0; i < armor.size(); i++) {
            EquipmentSlot equipmentSlot = SLOT_IDS[i];
            ResourceLocation resourcelocation = TEXTURE_EMPTY_SLOTS.get(equipmentSlot);
            ArmorSlot slot = new ArmorSlot(inv, inv.player, equipmentSlot, 39 - i, 179, 46 + i * 20, resourcelocation);
            slot.setActive(true);
            this.addSlot(slot);
            this.armorSlots.add(slot);
        }

    }

    public List<ArmorSlot> getArmorSlots() {
        return armorSlots;
    }

    @Override
    protected int getMergeableSlotCount() {
        return 9;
    }

    public void setArmorSlotPositions(int startY) {
        List<ArmorSlot> upgradeSlots = this.getArmorSlots();
        for (int i = 0; i < upgradeSlots.size(); i++) {
            ArmorSlot upgradeSlot = upgradeSlots.get(i);
            ((SlotAccessor) upgradeSlot).powerArmor$setY(startY + i * 20);
        }
    }
}
