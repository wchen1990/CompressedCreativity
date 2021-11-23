package com.lgmrszd.extensiveadapters.index;

import com.lgmrszd.extensiveadapters.ExtensiveAdapters;
import com.lgmrszd.extensiveadapters.blocks.rotational_compressor.RotationalCompressorRenderer;
import com.lgmrszd.extensiveadapters.blocks.rotational_compressor.RotationalCompressorTileEntity;
import com.simibubi.create.content.contraptions.base.HorizontalHalfShaftInstance;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.repack.registrate.util.entry.TileEntityEntry;

public class EATileEntities {

    private static final CreateRegistrate REGISTRATE = ExtensiveAdapters.registrate();

    public static final TileEntityEntry<RotationalCompressorTileEntity> ROTATIONAL_COMPRESSOR = REGISTRATE
            .tileEntity("rotational_compressor", RotationalCompressorTileEntity::new)
            .instance(() -> HorizontalHalfShaftInstance::new)
            .validBlock(EABlocks.ROTATIONAL_COMPRESSOR)
            .renderer(() -> RotationalCompressorRenderer::new)
            .register();

    public static void register() {
    }
}
