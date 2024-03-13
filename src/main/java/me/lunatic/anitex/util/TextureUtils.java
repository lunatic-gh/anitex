package me.lunatic.anitex.util;

import me.lunatic.anitex.tex.Frame;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextureUtils {

    public static List<Frame> getFrameResources(Identifier container) {
        List<Frame> list = new ArrayList<>();
        for (Map.Entry<Identifier, List<Resource>> entry : MinecraftClient.getInstance().getResourceManager().findAllResources(container.getPath(), id -> id.getPath().endsWith(".png") && id.getNamespace().equals(container.getNamespace())).entrySet()) {
            Identifier id = entry.getKey();
            Resource res = null;
            for (Resource res1 : entry.getValue()) {
                res = res1; // Go through all resourcepacks
            }
            if (res != null) {
                try (InputStream in = res.getInputStream()) {
                    try (NativeImage img = NativeImage.read(in)) {
                        list.add(new Frame(id, img.getWidth(), img.getHeight()));
                    }
                } catch (IOException e) {

                }
            }
        }
        return list.stream().sorted(new FileNameComparator()).toList();
    }
}