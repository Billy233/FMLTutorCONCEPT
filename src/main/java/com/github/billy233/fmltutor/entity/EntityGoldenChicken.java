package com.github.billy233.fmltutor.entity;

import com.github.billy233.fmltutor.item.ItemLoader;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

// 生物 Entity 方面会写很多注释        目标：创建一个金鸡实体
public class EntityGoldenChicken extends EntityChicken {

    // 构造方法传入一个 World 实例，如果该生物想自然生成的话
    public EntityGoldenChicken(World worldIn) {
        super(worldIn);
    }

    // onLivingUpdate用于更新entity  若干gameTick一次
    // 这里显示覆盖一下
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
    }
    
    // 重写 dropFewItems 方法，设置掉落物
    /*
    在Minecraft中，通过调用实体生物的dropFewItems方法来使得实体生物掉落物品，
    一般会在实体生物死亡时调用。该方法的第一个参数 arg1 于给出该实体生物是被玩家攻击致死，还是由于自然原因等而死，
    比如蜘蛛眼的获取，就必须保证对应的蜘蛛是玩家攻击致死的。
    该方法的第二个参数，指的是抢夺等级，也就是玩家攻击生物致死时的使用的武器的抢夺等级。
    里我们覆写了这个方法，以10%的概率添加一种新的掉落。

    如果没有多于一个的掉落，或者数量异常的掉落，可以覆写对应类的getDropItem方法，
    通过覆写这个方法，我们就可以使用一种比较平凡的方式掉落我们想要的物品。
    */
    @Override
    protected void dropFewItems(boolean arg1, int arg2) {
        if (this.rand.nextInt(10) == 0) {
            // rand.nextInt(int n) 返回一个 整数k ∈ [0, n)。这里是 10% 掉落

            this.dropItem(ItemLoader.goldenEgg, 1);
            
            /*
            public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY) { ps: offsetY用于y轴偏差量
                if (itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
                    EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + (double)offsetY, this.posZ, itemStackIn);
                    entityitem.setDefaultPickupDelay();
                    if (captureDrops)
                        this.capturedDrops.add(entityitem);
                    else
                        this.worldObj.spawnEntityInWorld(entityitem);
                    return entityitem;
                } else {
                    return null;
                }
            }
            */

            super.dropFewItems(arg1, arg2);
        }
    }
}