package de.florianfinn.cropariumifcompat;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/** A delegate used only by Industrial Foregoing while it fertilizes a Croparium crop. */
public final class CropariumFertilizableBlock extends Block implements BonemealableBlock {
    public static final CropariumFertilizableBlock INSTANCE = new CropariumFertilizableBlock();

    private static final TagKey<Block> CROPS = TagKey.create(
            Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("cp_lib", "crops"));
    private static final int RIPE_AGE = 7;

    private CropariumFertilizableBlock() {
        super(BlockBehaviour.Properties.of());
    }

    public static boolean supports(BlockState state) {
        IntegerProperty age = ageProperty(state);
        return state.is(CROPS) && age != null && age.getPossibleValues().contains(RIPE_AGE)
                && !(state.getBlock() instanceof BonemealableBlock);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        BlockState current = level.getBlockState(pos);
        IntegerProperty age = ageProperty(current);
        return current.getBlock() == state.getBlock() && current.is(CROPS)
                && age != null && current.getValue(age) < RIPE_AGE;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return isValidBonemealTarget(level, pos, state);
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        // Re-read the crop in case it changed between selection and this operation.
        BlockState current = level.getBlockState(pos);
        if (current.getBlock() != state.getBlock() || !isValidBonemealTarget(level, pos, current)) {
            return;
        }

        IntegerProperty age = ageProperty(current);
        if (age != null) {
            int nextAge = Math.min(RIPE_AGE, current.getValue(age) + 2 + random.nextInt(4));
            level.setBlock(pos, current.setValue(age, nextAge), Block.UPDATE_ALL);
        }
    }

    private static IntegerProperty ageProperty(BlockState state) {
        var property = state.getBlock().getStateDefinition().getProperty("age");
        return property instanceof IntegerProperty age ? age : null;
    }
}
