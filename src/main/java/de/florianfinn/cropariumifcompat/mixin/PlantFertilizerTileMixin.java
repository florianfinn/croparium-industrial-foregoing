package de.florianfinn.cropariumifcompat.mixin;

import com.buuz135.industrial.block.agriculturehusbandry.tile.PlantFertilizerTile;
import de.florianfinn.cropariumifcompat.CropariumFertilizableBlock;
import de.florianfinn.cropariumifcompat.CropariumIndustrialForegoingCompat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/** Makes tagged crops appear bonemealable to the Plant Fertilizer's existing work routine. */
@Mixin(value = PlantFertilizerTile.class, remap = false)
public abstract class PlantFertilizerTileMixin {
    @Redirect(method = "work", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;"))
    private Block cropariumIfCompat$fertilizableCrop(BlockState state) {
        return CropariumFertilizableBlock.supports(state)
                ? CropariumIndustrialForegoingCompat.FERTILIZER_PROXY.get()
                : state.getBlock();
    }
}
