package mango.core.callbacks;

import mango.core.drupe.DrupeSystem;

public class ConfigureCallbacks implements CallbackBase {
    private final DrupeSystem drupe;

    public ConfigureCallbacks(DrupeSystem drupe) {
        this.drupe = drupe;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void display() {

    }

    @Override
    public void idle() {

    }

    @Override
    public void keyboard(int keyState, int scanCode) {

    }

    @Override
    public void init() {

    }

    @Override
    public void exit() {

    }

    @Override
    public void mouse(int buttons, int state, int x, int y) {

    }

    @Override
    public void mouseMotion(int x, int y) {

    }
}
