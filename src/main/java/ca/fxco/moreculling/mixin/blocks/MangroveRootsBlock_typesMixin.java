package ca.fxco.moreculling.mixin.blocks;

import ca.fxco.moreculling.MoreCulling;
import ca.fxco.moreculling.api.block.MoreBlockCulling;
import ca.fxco.moreculling.config.option.LeavesCullingMode;
import ca.fxco.moreculling.utils.CullingUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.MangroveRootsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(value = MangroveRootsBlock.class, priority = 1220)
public class MangroveRootsBlock_typesMixin extends Block implements MoreBlockCulling {

    public MangroveRootsBlock_typesMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (!MoreCulling.CONFIG.includeMangroveRoots) return super.isSideInvisible(state, stateFrom, direction);
        if (MoreCulling.CONFIG.leavesCullingMode == LeavesCullingMode.FAST || CullingUtils.areLeavesOpaque())
            return stateFrom.getBlock() instanceof LeavesBlock || super.isSideInvisible(state, stateFrom, direction);
        return super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    public boolean usesCustomShouldDrawFace(BlockState state) {
        return MoreCulling.CONFIG.includeMangroveRoots &&
                MoreCulling.CONFIG.leavesCullingMode != LeavesCullingMode.DEFAULT; //Fast will skip this call
    }

    @Override
    public Optional<Boolean> customShouldDrawFace(BlockView view, BlockState thisState, BlockState sideState,
                                                  BlockPos thisPos, BlockPos sidePos, Direction side) {
        return switch (MoreCulling.CONFIG.leavesCullingMode) { // Can't use state culling here
            case CHECK -> CullingUtils.shouldDrawFaceCheck(view, sideState, sidePos, thisPos, side);
            case GAP -> CullingUtils.shouldDrawFaceGap(view, sideState, sidePos, side);
            case DEPTH -> CullingUtils.shouldDrawFaceDepth(view, sideState, sidePos, side);
            case RANDOM -> CullingUtils.shouldDrawFaceRandom(view, sideState, sidePos, side);
            default -> Optional.empty();
        };
    }
}
