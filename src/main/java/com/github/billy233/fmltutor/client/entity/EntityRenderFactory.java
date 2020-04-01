package com.github.billy233.fmltutor.client.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityRenderFactory<E extends Entity> implements IRenderFactory<E> {

    private final Class<? extends Render<E>> renderClass;

    public EntityRenderFactory(Class<? extends Render<E>> renderClass) {
        this.renderClass = renderClass;
    }

    // 接口 IRenderFactory 有且只有一个方法 public Render<? super T> createRenderFor(RenderManager manager); 需要被实现
    /*
    EntityRenderFactory 类的作用是传入一个 Class 类的实例( Class<? extends Render<E>> renderClass renderClass)，
    去调用它代表的类的一个传入 RenderManager 的构造方法生成。( return renderClass.getConstructor(RenderManager.class).newInstance(manager); )
    读者不需要关心下面这段代码的含义是什么，直接拿来用，并保证传入的 Class 类的实例代表的类有一个传入 RenderManager 的构造方法就可以了。
    */
    @Override
    public Render<E> createRenderFor(RenderManager manager) {
        try {
            return renderClass.getConstructor(RenderManager.class).newInstance(manager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}