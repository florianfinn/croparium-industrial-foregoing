package de.florianfinn.cropariumifcompat;

import com.buuz135.industrial.api.plant.PlantRecollectable;
import com.buuz135.industrial.registry.IFRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CropariumIndustrialForegoingCompat.MOD_ID)
public final class CropariumIndustrialForegoingCompat {
    public static final String MOD_ID = "croparium_if_compat";

    private static final DeferredRegister<PlantRecollectable> PLANTS =
            DeferredRegister.create(IFRegistries.PLANT_RECOLLECTABLES_REGISTRY_KEY, MOD_ID);
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, MOD_ID);
    public static final DeferredHolder<Block, CropariumFertilizableBlock> FERTILIZER_PROXY =
            BLOCKS.register("fertilizer_proxy", CropariumFertilizableBlock::new);

    static {
        PLANTS.register("croparium_crops", CropariumCropRecollectable::new);
    }

    public CropariumIndustrialForegoingCompat(IEventBus modBus) {
        PLANTS.register(modBus);
        BLOCKS.register(modBus);
    }
}
