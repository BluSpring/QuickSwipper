package xyz.bluspring.quickswipper;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class QuickSwipper implements ModInitializer {
    @Override
    public void onInitialize() {

    }

    public static InteractionResultHolder<ItemStack> tryQuickSwap(Level level, Player player, InteractionHand hand, ItemStack stack) {
        var slot = Mob.getEquipmentSlotForItem(stack);
        var equipped = player.getItemBySlot(slot);

        //player.setItemSlot(slot, stack);
        //player.setItemInHand(hand, equipped);

        if (level.isClientSide()) {
            tryQuickSwapClient(slot, hand, stack, equipped);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private static void tryQuickSwapClient(EquipmentSlot slot, InteractionHand hand, ItemStack heldStack, ItemStack equippedStack) {
        var player = Minecraft.getInstance().player;
        var interaction = Minecraft.getInstance().gameMode;
        var inventory = player.inventoryMenu;
        var selected = player.getInventory().selected;
        var handSlot = hand == InteractionHand.MAIN_HAND ? InventoryMenu.USE_ROW_SLOT_START + player.getInventory().selected : InventoryMenu.SHIELD_SLOT;
        var armorSlot = InventoryMenu.ARMOR_SLOT_END - slot.getFilterFlag();

        interaction.handleInventoryMouseClick(inventory.containerId, armorSlot, selected, ClickType.SWAP, player);
    }
}
