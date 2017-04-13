package mango.game;

import mango.core.drupe.video.GraphicsUtil;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    public void drawMenu() {
        Random rnd = new Random();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int numEntries = 2;
        for (int i = 0; i < numEntries; i++) {
            float[] color = new float[4];
            float[] active1 = new float[4];
            float[] active2 = new float[4];
            for (int j = 0; j < 4; j++) {
                color[j] = rnd.nextFloat();
            }
            glColor4f(color[0], color[1], color[2], color[3]);
            // TODO
            GraphicsUtil.drawText(null, 0, 0, 1, "tmp");
        }
        glDisable(GL_BLEND);
    }
}
