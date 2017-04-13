package mango.core.drupe;

import mango.core.callbacks.CallbackBase;
import mango.game.StateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;

public class DrupeSystem {
    public static final int DRUPE_KEY_NONE = 0;
    public static final int DRUPE_KEY_PRESS = 1;
    public static final int DRUPE_KEY_RELEASE = 2;
    private static final Logger log = LoggerFactory.getLogger(DrupeSystem.class.getName());
    private static final int DRUPE_NUM_SCANCODES = 10000; // TODO
    private StateEvent returnCode = StateEvent.None; // TODO
    private CallbackBase currentCallbacks;
    private int[] keymap = new int[DRUPE_NUM_SCANCODES];

    private long windowHandle;
    private boolean idle = false;
    private boolean redisplay = true;

    public void updateCallbacks(CallbackBase cb) {
        if (currentCallbacks != null) {
            currentCallbacks.exit();
        }
        currentCallbacks = cb;
        if (currentCallbacks != null) {
            currentCallbacks.init();
        }
    }

    public StateEvent getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(StateEvent returnCode) {
        this.returnCode = returnCode;
    }

    public void setWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
    }

    public StateEvent mainLoop(String current) {
        boolean validCallbacks = currentCallbacks != null;

        while (returnCode == StateEvent.None) {
            if (validCallbacks && redisplay) {
                currentCallbacks.display();
                redisplay = false;
            }
            if (validCallbacks && idle) {
                currentCallbacks.idle();
            }
        }

        if (validCallbacks) {
            currentCallbacks.exit();
        }

        currentCallbacks = null;
        return returnCode;
    }

    public void handleKey(long window, int key, int scancode, int action, int mods) {
        int keyState = DRUPE_KEY_NONE;

        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            log.debug("ESC detected. Alerting GLFW.");
            glfwSetWindowShouldClose(window, true);
        }

        // Don't care about GLFW_REPEAT
        if (action == GLFW_PRESS) {
            keyState = DRUPE_KEY_PRESS;
        } else if (action == GLFW_RELEASE) {
            keyState = DRUPE_KEY_RELEASE;
        }
        setKeyState(scancode, keyState);
        if (currentCallbacks != null) {
            currentCallbacks.keyboard(key, scancode);
        }
    }

    private void setKeyState(int scanCode, int keyState) {
        if (scanCode < DRUPE_NUM_SCANCODES) {
            log.debug("Updating key " + scanCode + " to state " + keyState);
            keymap[scanCode] = keyState;
        }
    }

    public void pushRedisplay() {
        this.redisplay = true;
    }

    public void swapBuffers() {
        glfwSwapBuffers(windowHandle);
    }
}
