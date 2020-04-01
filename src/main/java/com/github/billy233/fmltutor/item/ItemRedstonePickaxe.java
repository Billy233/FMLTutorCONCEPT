package com.github.billy233.fmltutor.item;

import com.github.billy233.fmltutor.creativetab.CreativeTabsLoader;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRedstonePickaxe extends ItemPickaxe {

    public static final ToolMaterial REDSTONE = EnumHelper.addToolMaterial("REDSTONE", 3, 16, 16.0F, 0.0F, 10);

    public ItemRedstonePickaxe() {
        super(REDSTONE);
        this.setUnlocalizedName("redstonePickaxe");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }
}
