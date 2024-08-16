package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.ItemRemoveCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.llamalad7.mixinextras.sugar.Local;
import com.opryshok.ui.LedgerSimpleGui;
import com.opryshok.ui.LedgerSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LedgerSimpleGui.class)
public class LedgerSimpleGuiMixin {
    @Inject(method = "quickMove", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"), cancellable = true)
    public void quickMoveLedgerLogging(int index, CallbackInfoReturnable<ItemStack> cir, @Local Slot slot, @Local (ordinal = 0) ItemStack stack){
        LedgerSimpleGui gui = (LedgerSimpleGui) (Object) this;
        if (slot instanceof LedgerSlot ledgerSlot) {
            ItemRemoveCallback.EVENT.invoker().remove(stack, ledgerSlot.pos, gui.getPlayer().getServerWorld(), Sources.PLAYER, gui.getPlayer());
        }
    }
}
