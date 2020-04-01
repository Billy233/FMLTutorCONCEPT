package com.github.billy233.fmltutor.entity;

import com.github.billy233.fmltutor.FMLTutor;
import com.github.billy233.fmltutor.client.entity.EntityRenderFactory;
import com.github.billy233.fmltutor.client.entity.render.RenderGoldenChicken;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLoader {

    private static int nextID = 0;

    public EntityLoader() {
        registerEntity(EntityGoldenChicken.class, "GoldenChicken", 80, 3, true);
        registerEntityEgg(EntityGoldenChicken.class, 0xffff66, 0x660000);
    }

    // registerRenders 与下方 registerEntityRender 方法用于渲染生物模型
    // 故用 @SideOnly 注解
    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerEntityRender(EntityGoldenChicken.class, RenderGoldenChicken.class);
    }

    /////////////////////////////////// Followings are private methods //////////////////////////////////////

    // 私有 registerEntity 用于注册 entity
    // 以下是 registerModEntity 方法：
    /*
     * Register the mod entity type with FML

     * @param entityClass The entity class
     * 传入这个实体对应的 class 实例(泛型)，这里传入 EntityGoldenChicken.class 没有什么问题的(在构造函数里传入的)
     * @param entityName A unique name for the entity
     * 传入这个实体的名称，不过这里我们和物品、方块等不一样，实体的名称建议使用"大写驼峰式"，这里传入的就是 "GoldenChicken"
     * @param id A mod specific ID for the entity
     * 表示这个实体类型的ID，"同一个"Mod的每一个实体类型的 ID 要不同，这里是通过递增处理的(private static int nextID = 0;)
     * @param mod The mod
     * 对应的Mod实例，这里我们使用主类中提供的Mod实例，也没有什么问题
     * @param trackingRange The range at which MC will send tracking updates
     * 跟踪半径，也就是说如果这个实体距离玩家一量超过对应的大小，这个实体就不更新了，一般情况下，生物设置成 64格 是比较合理的，当然有的实体有特殊需要，这个参数设置得比较大
     * @param updateFrequency The frequency of tracking updates
     * 更新频率，对于生物来说，每3 gameTick一更新是比较常见的做法，当然有的会设置成特别长，有10 gametick，20 gametick，甚至整数的最大值，也就是不更新
     * @param sendsVelocityUpdates Whether to send velocity information packets as well
     * 表示是否 同步实体的速度更新 ，对于一些实体，比如静止的实体，一些会手动更新数据的BOSS，是没有必要的，但是对于实体生物来说是有必要的，这里设置为 true
     * 
    public static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        instance().doModEntityRegistration(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
    */
    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
            int updateFrequency, boolean sendsVelocityUpdates) {
        EntityRegistry.registerModEntity(entityClass, name, nextID++, FMLTutor.instance, trackingRange, 
                updateFrequency, sendsVelocityUpdates);
    }

    // 私有 registerEntityEgg 用于注册刷怪蛋
    // 第一个参数表示实体对应的class实例，后面的两个参数表示刷怪蛋的主色和副色，也就是蛋本身的颜色和对应的斑点
    // ps:后两个参数为十六进制颜色
    private static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary) {
        EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
    }

    // 这里需要使用 RenderingRegistry 的 registerEntityRenderingHandler 方法来注册实体的渲染。
    // 这个方法一共只有两个参数，第一个参数表示这个实体对应的class实例，第二个参数需要一个IRenderFactory接口的实例，这里新建了 EntityRenderFactory 来实现这个接口
    @SideOnly(Side.CLIENT)
    private static <T extends Entity> void registerEntityRender(Class<T> entityClass, Class<? extends Render<T>> render) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, new EntityRenderFactory<T>(render));
    }
}
