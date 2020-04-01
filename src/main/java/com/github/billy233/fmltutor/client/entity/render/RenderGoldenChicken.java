package com.github.billy233.fmltutor.client.entity.render;

import com.github.billy233.fmltutor.FMLTutor;
import com.github.billy233.fmltutor.client.entity.model.ModelGoldenChicken;
import com.github.billy233.fmltutor.entity.EntityGoldenChicken;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGoldenChicken extends RenderLiving<EntityGoldenChicken> {
    
    private static final ResourceLocation GOLDEN_CHICKEN_TEXTURE = new ResourceLocation(FMLTutor.MODID + ":" + "textures/entity/golden_chicken.png");

    /*
    第一个参数表示的是Minecraft的渲染管理器，这里留作参数传入就可以了，我们刚刚也注意到了，在注册的时候，这确实作为参数，
    把Minecraft.getMinecraft().getRenderManager()传入了这个类的构造方法。第三个参数其实表示的是阴影大小，这里用默认的0.5就可以了。
    第二个参数表示的，就是这个实体生物的实体渲染模型了
    */
    public RenderGoldenChicken(RenderManager renderManager) {
        super(renderManager, new ModelGoldenChicken(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityGoldenChicken entity, float partialTickTime) {
        GlStateManager.scale(2.5F, 2.5F, 2.5F);
    } // 显然用于缩放

    @Override
    protected ResourceLocation getEntityTexture(EntityGoldenChicken entity) {
        return RenderGoldenChicken.GOLDEN_CHICKEN_TEXTURE;
    } // 返回 GOLDEN_CHICKEN_TEXTURE

    /*
    Render类的核心，就在于这个名为doRender的方法，这个方法传入了实体的坐标和转动角度，总共四个自由度对于渲染一个实体来说是足够用了。
    那么这个partialTicks是怎么回事呢？
    我们知道，在包括Minecraft在内的许多游戏中，游戏逻辑循环和渲染循环是相对独立的，毕竟在Minecraft中是每秒钟实现二十次逻辑，
    而如果游戏的渲染帧率只有20每秒的话，很多人的需求是无法被满足，所以说在游戏中的中间的部分的渲染，就要进行平滑的插值来完成，
    比如说实体 0tick 时在位置0，1tick 时在位置1,那么在 0.5tick 时（这时游戏还没有实现下一次逻辑！）的位置就应该是“(位置0+位置1)/2”，
    这是非常显而易见的，这里的partialTicks，就是tick数的小数部分。其实这里传入的四个坐标和姿态参数，就是这么算出来的。
    不过对于实现一些逻辑还是不太够，这里就把partialTicks一并传过来，供这个方法使用。
    这里我们尽可以覆写这个方法，甚至不依靠 RenderLiving 类，直接继承 Render 类并覆写，完成对实体的渲染，不过如果直接进行实体的渲染的话，那实在是太复杂了。
    不过幸运的是，Minecraft游戏本身提供了一些轮子，来帮助我们完成实体的渲染。在很多时候，我们可能只需要渲染若干个经过平移和旋转的长方体，就足够了。
    */
    @Override
    public void doRender(EntityGoldenChicken entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}