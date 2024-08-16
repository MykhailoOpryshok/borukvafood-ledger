package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.ItemRemoveCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.opryshok.entity.CuttingBoardBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CuttingBoardBlockEntity.class)
public class CuttingBoardEntityMixin {
    @Inject(method = "processItemUsingTool", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
    public void ProcessItemLedgerLogging(World world, ItemStack tool, PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        if (player instanceof ServerPlayerEntity serverPlayer){
            CuttingBoardBlockEntity entity = (CuttingBoardBlockEntity) (Object) this;
            ItemRemoveCallback.EVENT.invoker().remove(entity.getItemStack(), entity.getPos(), serverPlayer.getServerWorld(), Sources.PLAYER, serverPlayer);
        }
    }

}
