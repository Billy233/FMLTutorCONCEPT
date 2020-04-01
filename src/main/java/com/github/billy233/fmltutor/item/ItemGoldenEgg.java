package com.github.billy233.fmltutor.item;

import com.github.billy233.fmltutor.creativetab.CreativeTabsLoader;
import net.minecraft.item.Item;

public class ItemGoldenEgg extends Item {

    public ItemGoldenEgg() {

        super();
        this.setUnlocalizedName("goldenEgg");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }
}
