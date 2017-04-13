package mango.core.drupe;

import mango.core.callbacks.CallbackBase;
import mango.game.StateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.*;

public class DrupeSystem {
    private static final Logger log = LoggerFactory.getLogger(DrupeSystem.class.getName());

    private StateEvent returnCode = StateEvent.NotImplemented; // TODO
    private CallbackBase currentCallbacks;

    public static final int DRUPE_KEY_NONE      = 0;
    public static final int DRUPE_KEY_PRESS     = 1;
    public static final int DRUPE_KEY_RELEASE   = 2;

    private static final int DRUPE_NUM_SCANCODES = 10000; // TODO
    private int[] keymap = new int[DRUPE_NUM_SCANCODES];

    private long windowHandle;

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
        while (returnCode == StateEvent.None) {
            // TODO
        }
        return returnCode;
    }

    public void handleKey(long window, int key, int scancode, int action, int mods) {
        int keyState = DRUPE_KEY_NONE;

        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            log.debug("ESC detected. Alerting GLFW.");
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
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
}
