package me.lunatic.test.mixin;

import me.lunatic.anitex.tex.AnimatedTexture;
import me.lunatic.anitex.util.TextureUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {

    @Unique
    private AnimatedTexture sampleTexture;
    @Unique
    private AnimatedTexture sampleTextureFromFile;

    protected MixinOptionsScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        // BIG NOTE: You almost never want to create textures more than once. Depending on the amount of frames, this may result in a lot of memory-consumption.
        // This is just as an example. Rather register them once on startup, and inside a reload listener (https://fabricmc.net/wiki/tutorial:custom_resources)
        // Here we create a new AnimatedTexture Object. Also provide the delay in milliseconds.
        this.sampleTexture = new AnimatedTexture(1000);
        // If you want it to actually render anything, you need to also load in some frames.
        // NOTE: getFrameResources will load all frames from a directory, in alphabetical order.
        this.sampleTexture.load(TextureUtils.getFrameResources(new Identifier("test", "textures/test")));
        // You can also load textures from actual files instead of assets
        this.sampleTextureFromFile = new AnimatedTexture(1000);
        this.sampleTextureFromFile.load(TextureUtils.getFrameFiles(new File("config/test/")));
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // Now that we loaded the texture above, we can easily render it here.
        // This will render the current frame, and automatically rotate to the next frame if the delay allows it to.
        // Required Args:
        // context - the DrawContext. It is usually provided by most render methods minecraft uses.
        // x/y - the top-left position of where to render the texture
        // width/height - the width and height to use when rendering the texture. NOT THE ACTUAL TEXTURE FILE DIMENSIONS!
        // delta - the millisecond-difference between the current and last frame. Same as the context, this is provided by most mc rendering methods.
        this.sampleTextureFromFile.render(context, 0, 0, this.width, this.height, delta);
    }
}
