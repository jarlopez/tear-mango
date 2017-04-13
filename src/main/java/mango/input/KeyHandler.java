package mango.input;

import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class KeyHandler implements GLFWKeyCallbackI {
    private static final Logger log = LoggerFactory.getLogger(KeyHandler.class.getName());

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            log.debug("ESC detected. Alerting GLFW.");
            glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        }
    }
}
