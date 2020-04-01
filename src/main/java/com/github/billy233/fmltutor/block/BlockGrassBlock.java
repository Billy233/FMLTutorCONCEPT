package com.github.billy233.fmltutor.block;

import com.github.billy233.fmltutor.common.EventLoader;
import com.github.billy233.fmltutor.creativetab.CreativeTabsLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockGrassBlock extends Block {

    public BlockGrassBlock() {
        super(Material.ground);
        this.setUnlocalizedName("grassBlock");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGrass);
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        EventLoader.PlayerRightClickGrassBlockEvent event;
        event = new EventLoader.PlayerRightClickGrassBlockEvent(playerIn, pos, worldIn);
        EventLoader.EVENT_BUS.post(event);

        if (!event.isCanceled() && !worldIn.isRemote) {
            worldIn.setBlockToAir(pos);
            return true;
        }
        return false;
    }
}
