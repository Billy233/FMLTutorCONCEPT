package com.github.billy233.fmltutor.item;

import com.github.billy233.fmltutor.creativetab.CreativeTabsLoader;

import net.minecraft.item.Item;

public class ItemTestItem extends Item {

    public ItemTestItem() {
        super();
        this.setUnlocalizedName("testItem");
        this.setCreativeTab(CreativeTabsLoader.tabFMLTutor);
    }
}
