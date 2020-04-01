package com.github.billy233.fmltutor.common;

import com.github.billy233.fmltutor.achievement.AchievementLoader;
import com.github.billy233.fmltutor.block.BlockLoader;
import com.github.billy233.fmltutor.command.CommandLoader;
import com.github.billy233.fmltutor.crafting.CraftingLoader;
import com.github.billy233.fmltutor.creativetab.CreativeTabsLoader;
import com.github.billy233.fmltutor.enchantment.EnchantmentLoader;
import com.github.billy233.fmltutor.entity.EntityLoader;
import com.github.billy233.fmltutor.item.ItemLoader;
import com.github.billy233.fmltutor.potion.PotionLoader;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        new ConfigLoader(event);
        new CreativeTabsLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
        new PotionLoader(event);
        new EntityLoader();
    }

    public void init(FMLInitializationEvent event) {
        new CraftingLoader();
        new EnchantmentLoader();
        new AchievementLoader();
        new EventLoader();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void serverStarting(FMLServerStartingEvent event) {
        new CommandLoader(event);
    }
}
