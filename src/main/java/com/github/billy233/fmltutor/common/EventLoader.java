package com.github.billy233.fmltutor.common;

import com.github.billy233.fmltutor.client.KeyLoader;
import com.github.billy233.fmltutor.enchantment.EnchantmentLoader;
import com.github.billy233.fmltutor.entity.EntityGoldenChicken;
import com.github.billy233.fmltutor.item.ItemLoader;
import com.github.billy233.fmltutor.potion.PotionLoader;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventLoader {

    public EventLoader() {

        MinecraftForge.EVENT_BUS.register(this);
        EventLoader.EVENT_BUS.register(this);
    }

    // EventTest 1
    @SubscribeEvent
    public void onPlayerItemPickup(PlayerEvent.ItemPickupEvent event) {

        if (event.player.isServerWorld()) {
            String info = String.format("%s picks up: %s", event.player.getName(), event.pickedUp.getEntityItem());
            ConfigLoader.logger().info(info);
        }
    }

    // EventTest 2
    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent event) {

        if (!event.world.isRemote) {
            String info = String.format("%s interacts with: %s", event.entityPlayer.getName(), event.pos);
            ConfigLoader.logger().info(info);
        }
    }

    // Custom DamageSource byPig Event
    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        EntityPlayer player = event.entityPlayer;

        if (player.isServerWorld() && event.target instanceof EntityPig) {
            EntityPig pig = (EntityPig)event.target;
            ItemStack stack = player.getCurrentEquippedItem();

            if (stack != null && (stack.getItem() == Items.wheat || stack.getItem() == Items.wheat_seeds)) {

                player.attackEntityFrom((new DamageSource("byPig")).setDifficultyScaled().setExplosion(), 8.0F);
                player.worldObj.createExplosion(pig, pig.posX, pig.posY, pig.posZ, 2.0F, false);
                pig.setDead();
            }
        }
    }

    // Enchantment fireBurn Event
    @SubscribeEvent
    public void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        if (!event.world.isRemote && event.harvester != null) {
            ItemStack itemStack = event.harvester.getHeldItem();
            if (EnchantmentHelper.getEnchantmentLevel(EnchantmentLoader.fireBurn.effectId, itemStack) > 0 && itemStack.getItem() != Items.shears) {
                for (int i = 0; i < event.drops.size(); ++i) {
                    ItemStack stack = event.drops.get(i);
                    ItemStack newStack = FurnaceRecipes.instance().getSmeltingResult(stack);
                    if (newStack != null) {
                        newStack = newStack.copy();
                        newStack.stackSize = stack.stackSize;
                        event.drops.set(i, newStack);
                    } else if (stack != null) {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        boolean b = (block == null);
                        if (!b && (block.isFlammable(event.world, event.pos, EnumFacing.DOWN)
                                || block.isFlammable(event.world, event.pos, EnumFacing.EAST)
                                || block.isFlammable(event.world, event.pos, EnumFacing.NORTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.SOUTH)
                                || block.isFlammable(event.world, event.pos, EnumFacing.UP)
                                || block.isFlammable(event.world, event.pos, EnumFacing.WEST))){
                            event.drops.remove(i);
                        }
                    }
                }
            }
        }
    }

    // PotionFallProtection effect Event
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.source.getDamageType().equals("fall")); {

            PotionEffect effect = event.entityLiving.getActivePotionEffect(PotionLoader.potionFallProtection);
            if (effect != null) {
                if (effect.getAmplifier() == 0) {
                    event.ammount /= 2;
                } else {
                    event.ammount = 0;
                }
            }
        }
    }

    // KeyBind showtime
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        if (KeyLoader.showTime.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            World world = Minecraft.getMinecraft().theWorld;
            player.addChatMessage(new ChatComponentTranslation("chat.fmltutor.time", world.getTotalWorldTime()));
        }
    }

    // When Player hurt, he will be given 64 Diamonds
    @SubscribeEvent
    public void onPlayerHurt(LivingHurtEvent event) {
        if (!event.entity.worldObj.isRemote) {

            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)event.entity;
                player.inventory.addItemStackToInventory(new ItemStack(Items.diamond, 64));
            }
        }
    }

    // Golden Chicken spawn event
    @SubscribeEvent
    public void onPlayerClickGrassBlock(PlayerRightClickGrassBlockEvent event)
    {
        if (!event.world.isRemote) {
            // getHelItem 返回 ItemStack 类型玩家手中的物品
            ItemStack heldItem = event.entityPlayer.getHeldItem();
            
            if (ItemLoader.goldenEgg.equals(heldItem.getItem())) {
                // .getItem() 拿到 Item 类型

                EntityLiving entityLiving = new EntityGoldenChicken(event.world);
                BlockPos pos = event.pos;
                entityLiving.setPositionAndUpdate(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                // Sets the position of the entity and updates the 'last' variables
                //设置实体位置并更新
                --heldItem.stackSize; // 自减
                event.world.spawnEntityInWorld(entityLiving);
                return; // 结束方法体
            }
            
            // 前者是用代码自定义生成 EntityGoldenChicken，用 goldenEgg 右击 GrassBlock 生成一个 entity
            // 后者是 tnt 爆炸事件
            BlockPos pos = event.pos;
            Entity tnt = new EntityTNTPrimed(event.world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, null);
            event.world.spawnEntityInWorld(tnt);
        }
    }

    // 
    @SubscribeEvent
    public void onPlayerClickGround(PlayerInteractEvent event) {
        if (!event.world.isRemote) {
            BlockPos pos = event.pos;
            World world = event.world;
            EntityPlayer player = event.entityPlayer;
            if (player.getHeldItem().getItem() == Items.stick) {
                
                for (int i = 1; i <= 50; i++) {

                    for (int j = 1; j <= 50; j++) {
                        
                        world.spawnEntityInWorld(new EntityTNTPrimed(world, pos.getX() + 2 * j, pos.getY(), pos.getZ() + 2 * i, null));
                    }
                    
                }
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////
    // Custom Event PlayerRightClickGrassBlockEvent
    @Cancelable
    public static class PlayerRightClickGrassBlockEvent extends net.minecraftforge.event.entity.player.PlayerEvent {

        public final BlockPos pos;
        public final World world;

        public PlayerRightClickGrassBlockEvent(EntityPlayer player, BlockPos pos, World world) {
            super(player);
            this.pos = pos;
            this.world = world;
        }
    }

    public static final EventBus EVENT_BUS = new EventBus();

}
