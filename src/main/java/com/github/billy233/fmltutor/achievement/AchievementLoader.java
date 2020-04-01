package com.github.billy233.fmltutor.achievement;

import com.github.billy233.fmltutor.item.ItemLoader;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;

public class AchievementLoader {
    public static Achievement worseThanPig = new Achievement("achievement.fmltutor.worseThanPig", "fmltutor.worseThanPig", 5, -4, ItemLoader.goldenEgg, AchievementList.buildSword);

    public AchievementLoader() {
        worseThanPig.setSpecial().registerStat();
    }
}
