package com.lgmrszd.compressedcreativity.blocks.rotational_compressor;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;


public class RotationalCompressorRenderer extends KineticTileEntityRenderer {
    public RotationalCompressorRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    protected void renderSafe(KineticTileEntity ote, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {

//        super.renderSafe(ote, partialTicks, ms, buffer, light, overlay);
//


        if (Backend.canUseInstancing(ote.getLevel())) return;

        if (!(ote instanceof RotationalCompressorTileEntity)) return;
        RotationalCompressorTileEntity te = (RotationalCompressorTileEntity) ote;

        Direction direction = te.getBlockState()
                .getValue(RotationalCompressorBlock.HORIZONTAL_FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

        int lightBehind = LevelRenderer.getLightColor(te.getLevel(), te.getBlockPos().relative(direction.getOpposite()));
        int lightInFront = LevelRenderer.getLightColor(te.getLevel(), te.getBlockPos().relative(direction));

        SuperByteBuffer shaftHalf =
                CachedBufferer.partialFacing(AllBlockPartials.SHAFT_HALF, te.getBlockState(), direction.getOpposite());
        SuperByteBuffer fanInner =
                CachedBufferer.partialFacing(AllBlockPartials.ENCASED_FAN_INNER, te.getBlockState(), direction.getOpposite());

        float time = AnimationTickHolder.getRenderTime(te.getLevel());
        float speed = te.getSpeed() * 5;
        if (speed > 0)
            speed = Mth.clamp(speed, 80, 64 * 20);
        if (speed < 0)
            speed = Mth.clamp(speed, -64 * 20, -80);
        float angle = (time * speed * 3 / 10f) % 360;
        angle = angle / 180f * (float) Math.PI;

        standardKineticRotationTransform(shaftHalf, te, lightBehind).renderInto(ms, vb);
        kineticRotationTransform(fanInner, te, direction.getAxis(), angle, lightInFront).renderInto(ms, vb);
    }
}
