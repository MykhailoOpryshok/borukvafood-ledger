package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.BlockBreakCallback;
import com.github.quiltservertools.ledger.callbacks.BlockChangeCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.llamalad7.mixinextras.sugar.Local;
import com.opryshok.item.KnifeTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KnifeTool.class)
public class KnifeMixin {
    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
    public void useOnBlockLedgerLogging(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir, @Local int i){
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = context.getPlayer();
        if  (i == 7){
            BlockBreakCallback.EVENT.invoker().breakBlock(world, pos, state, null, Sources.PLAYER, player);
        }
        else{
            BlockChangeCallback.EVENT.invoker().changeBlock(world, pos, state, world.getBlockState(pos), null, null, Sources.CONSUME,  player);
        }
    }
}
