package mango.core.callbacks;

public interface CallbackBase {
    String getName();

    void display();

    void idle();

    void keyboard(int keyState, int scanCode); // TODO

    void init();

    void exit();

    void mouse(int buttons, int state, int x, int y); // TODO

    void mouseMotion(int x, int y);
}
