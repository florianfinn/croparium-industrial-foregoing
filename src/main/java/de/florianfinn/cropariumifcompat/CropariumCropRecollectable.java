package de.florianfinn.cropariumifcompat;

import cn.cp.lib.configuration.CropariumConfigConfiguration;
import cn.cp.lib.procedures.HarvestCropsFromStringProcedure;
import com.buuz135.industrial.api.plant.PlantRecollectable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.List;

/** Harvests the same mapped item as Croparium's right-click action, then regrows in place. */
public final class CropariumCropRecollectable extends PlantRecollectable {
    private static final TagKey<Block> CROPS = TagKey.create(
            Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("cp_lib", "crops"));
    private static final int RIPE_AGE = 7;
    private static final int RESET_AGE = 1;

    public CropariumCropRecollectable() {
        super("croparium_crops");
    }

    @Override
    public boolean canBeHarvested(Level level, BlockPos pos, BlockState state) {
        IntegerProperty age = ageProperty(state);
        return state.is(CROPS) && age != null && state.getValue(age) == RIPE_AGE
                && age.getPossibleValues().contains(RESET_AGE);
    }

    @Override
    public List<ItemStack> doHarvestOperation(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide() || !canBeHarvested(level, pos, state)) {
            return List.of();
        }

        // Croparium's own mapping also covers crops supplied by its add-on mods.
        ItemStack drop = HarvestCropsFromStringProcedure.execute(level, state, false);
        if (drop.isEmpty()) {
            return List.of();
        }

        IntegerProperty age = ageProperty(state);
        if (age == null || !level.setBlock(pos, state.setValue(age, RESET_AGE), Block.UPDATE_ALL)) {
            return List.of();
        }

        double baseDrop = CropariumConfigConfiguration.RIGHT_CLICK_BASE_DROP.get();
        drop.setCount((int) Math.max(1.0, baseDrop));
        return List.of(drop);
    }

    @Override
    public boolean shouldCheckNextPlant(Level level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public List<String> getRecollectablesNames() {
        return List.of("Croparium crops");
    }

    private static IntegerProperty ageProperty(BlockState state) {
        var property = state.getBlock().getStateDefinition().getProperty("age");
        return property instanceof IntegerProperty age ? age : null;
    }
}
