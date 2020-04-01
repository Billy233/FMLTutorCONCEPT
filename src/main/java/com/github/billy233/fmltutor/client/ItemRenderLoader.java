package com.github.billy233.fmltutor.client;

import com.github.billy233.fmltutor.block.BlockLoader;
import com.github.billy233.fmltutor.item.ItemLoader;

public class ItemRenderLoader {

    public ItemRenderLoader() {
        ItemLoader.registerRenders();
        BlockLoader.registerRenders();
    }
}
