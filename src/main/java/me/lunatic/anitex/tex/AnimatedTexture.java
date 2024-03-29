package me.lunatic.anitex.tex;

import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnimatedTexture {

    private final List<Frame> frames;
    private long prevTime = 0L;
    private final int delayMs;

    public AnimatedTexture(int delayMs) {
        this.frames = new ArrayList<>();
        this.delayMs = delayMs;
    }

    public void load(List<Frame> frames) {
        this.frames.clear();
        this.frames.addAll(frames);
    }

    public void nextFrame() {
        Collections.rotate(this.frames, -1);
    }

    public void render(DrawContext context, int x, int y, int width, int height) {
        boolean b = this.frames.size() > 1;
        if (this.prevTime == 0L) {
            this.prevTime = System.currentTimeMillis();
        }
        Frame frame = this.frames.get(0);
        context.drawTexture(frame.id(), x, y, width, height, 0, 0, frame.width(), frame.height(), frame.width(), frame.height());
        if (b) {
            long l = System.currentTimeMillis();
            if (l >= this.prevTime + delayMs) {
                this.nextFrame();
                this.prevTime = l;
            }
        }
    }

    public List<Frame> getFrames() {
        return frames;
    }
}
