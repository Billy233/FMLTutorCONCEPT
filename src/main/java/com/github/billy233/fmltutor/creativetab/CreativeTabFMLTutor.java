package com.github.billy233.fmltutor.creativetab;

import com.github.billy233.fmltutor.item.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabFMLTutor extends CreativeTabs {

    public CreativeTabFMLTutor() {
        super("fmltutor");
        this.setBackgroundImageName("fmltutor.png");
    }

    @Override
    public Item getTabIconItem() {
        return ItemLoader.goldenEgg;
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }
}
