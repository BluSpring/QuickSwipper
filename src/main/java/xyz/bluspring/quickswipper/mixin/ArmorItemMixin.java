package xyz.bluspring.quickswipper.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import xyz.bluspring.quickswipper.QuickSwipper;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/InteractionResultHolder;fail(Ljava/lang/Object;)Lnet/minecraft/world/InteractionResultHolder;"))
    private <T> InteractionResultHolder<T> trySwitchArmorSlot(T object, Level level, Player player, InteractionHand hand, @Local(ordinal = 0) ItemStack stack) {
        return (InteractionResultHolder<T>) QuickSwipper.tryQuickSwap(level, player, hand, stack);
    }
}
