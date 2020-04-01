package com.github.billy233.fmltutor.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PotionLoader
{
    public static Potion potionFallProtection;

    public PotionLoader(FMLPreInitializationEvent event)
    {
        potionFallProtection = new PotionFallProtection();
    }
}
