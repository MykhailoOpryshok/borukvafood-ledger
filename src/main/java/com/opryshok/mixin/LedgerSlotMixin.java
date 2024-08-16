package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.ItemInsertCallback;
import com.github.quiltservertools.ledger.callbacks.ItemRemoveCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.opryshok.ui.LedgerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LedgerSlot.class)
public class LedgerSlotMixin {
    @Inject(method = "setStackNoCallbacks", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
    public void setStackLedgerLogging(ItemStack stack, CallbackInfo ci){
        LedgerSlot slot = (LedgerSlot) (Object) this;
        ItemInsertCallback.EVENT.invoker().insert(stack, slot.pos, slot.player.getServerWorld(), Sources.PLAYER, slot.player);
    }
    @Inject(method = "onTakeItem", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
    public void onTakeItemLedgerLogging(PlayerEntity player, ItemStack stack, CallbackInfo ci){
        LedgerSlot slot = (LedgerSlot) (Object) this;
        ItemRemoveCallback.EVENT.invoker().remove(stack, slot.pos, slot.player.getServerWorld(), Sources.PLAYER, slot.player);
    }
}
