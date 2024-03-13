# Very simple and lightweight API for animated textures in fabric 

<h2>How to use:</h2>
Here's a small example of how to render an animated wallpaper on the option screen: <br>

```java
@Mixin(OptionsScreen.class)
public class MixinOptionsScreen extends Screen {

    @Unique
    private AnimatedTexture sampleTexture;

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
        this.sampleTexture.render(context, 0, 0, this.width, this.height, delta);
    }
}

```
