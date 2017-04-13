package mango.core.drupe.video;

import mango.core.drupe.DrupeFont;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsUtil {

    public static void drawText(DrupeFont ftx, float x, float y, float size, String text) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glPushMatrix();

        glTranslatef(x, y, 0);
        glScalef(size, size, size);
        renderText(ftx, text);

        glPopMatrix();
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    public static void renderText(DrupeFont ftx, String text) {
        // TODO Implement me!
        glColor3f(1.0f, 0.0f, 0.0f);
        glRectf(0, 0, 640, 480);
    }
}
