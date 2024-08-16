package com.opryshok.mixin;

import com.github.quiltservertools.ledger.callbacks.BlockChangeCallback;
import com.github.quiltservertools.ledger.utility.Sources;
import com.opryshok.block.leaves.LemonFruitLeaves;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LemonFruitLeaves.class)
public class LemonFruitLeavesMixin {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lcom/opryshok/utils/BorukvaFoodUtil;ledgerMixinInvoke()V"))
    public void onUseLedgerLogging(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir){
        LemonFruitLeaves leaves = (LemonFruitLeaves) (Object) this;
        BlockChangeCallback.EVENT.invoker().changeBlock(world, pos, state, leaves.getBaseBlockState(state), null, null, Sources.PLAYER, player);
    }
}
