package mango.core.callbacks;

import mango.core.drupe.DrupeSystem;
import mango.game.GUI;
import mango.game.StateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.opengl.GL11.*;

public class GUICallbacks implements CallbackBase {
    private static final Logger log = LoggerFactory.getLogger(GUICallbacks.class.getName());

    private GUI gui;
    private DrupeSystem drupe;

    public GUICallbacks(DrupeSystem drupe) {
        this.gui = new GUI();
        this.drupe = drupe;
    }

    @Override
    public String getName() {
        return "gui";
    }

    @Override
    public void display() {
        log.debug("display");
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        glColor3f(1.0f, 0.0f, 0.0f);
//        glRectf(0, 0, 640, 480);

        gui.drawMenu();
        drupe.swapBuffers();

        // XXX TODO Remove below!
        drupe.setReturnCode(StateEvent.NotImplemented);
    }

    @Override
    public void idle() {
        log.debug("idle");
        drupe.pushRedisplay();
    }

    @Override
    public void keyboard(int keyState, int scanCode) {
        log.debug("keyboard");

    }

    @Override
    public void init() {
        log.debug("init");

    }

    @Override
    public void exit() {
        log.debug("exit");

    }

    @Override
    public void mouse(int buttons, int state, int x, int y) {
        log.debug("mouse");

    }

    @Override
    public void mouseMotion(int x, int y) {
        log.debug("mouseMotion");
    }
}
