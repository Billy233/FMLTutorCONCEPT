package com.github.billy233.fmltutor.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandLoader {
    
    public CommandLoader(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandPosition());
    }
}