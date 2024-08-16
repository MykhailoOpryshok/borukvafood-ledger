package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.ItemInsertCallback;
import com.github.quiltservertools.ledger.callbacks.ItemRemoveCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.llamalad7.mixinextras.sugar.Local;
import com.opryshok.block.cooking.CuttingBoard;
import com.opryshok.entity.CuttingBoardBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CuttingBoard.class)
public class CuttingBoardMixin {
	@Inject(method = "tryAddItemFromPlayerHand", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
	private void AddItemLedgerLogging(World world, CuttingBoardBlockEntity cuttingBoardBlockEntity, PlayerEntity player, CallbackInfoReturnable<ActionResult> cir, @Local (ordinal = 1) ItemStack stack) {
		if (player instanceof ServerPlayerEntity serverPlayer){
			ItemInsertCallback.EVENT.invoker().insert(stack, cuttingBoardBlockEntity.getPos(), serverPlayer.getServerWorld(), Sources.PLAYER, serverPlayer);
		}
	}
	@Inject(method = "pullOutItemWithPlayer", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
	public void PullOutItemLedgerLogging(World world, CuttingBoardBlockEntity cuttingBoardBlockEntity, PlayerEntity player, CallbackInfo ci){
		if (player instanceof ServerPlayerEntity serverPlayer){
			ItemRemoveCallback.EVENT.invoker().remove(cuttingBoardBlockEntity.getItemStack(), cuttingBoardBlockEntity.getPos(), serverPlayer.getServerWorld(), Sources.PLAYER, serverPlayer);
		}
	}
}