package mango;

import mango.core.callbacks.*;
import mango.core.drupe.DrupeSystem;
import mango.game.State;
import mango.game.StateEvent;
import mango.game.StateFunction;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Tron {


    private static DrupeSystem drupe = new DrupeSystem();
    private static Map<String, CallbackBase> callbacks = new HashMap<>();

    private static boolean initialized = false;
    private static State state = State.GAME;
    private static Map<StateEvent, StateFunction> fsm = new HashMap<>();

    static {

        // TODO Move all these string constants into final ones
        callbacks.put("gui", new GUICallbacks(drupe));
        callbacks.put("game", new GameCallbacks(drupe));
        callbacks.put("pause", new PauseCallbacks(drupe));
        callbacks.put("configure", new ConfigureCallbacks(drupe));

        fsm.put(StateEvent.GameEnd, () -> "pause");
        fsm.put(StateEvent.GamePause, () -> "pause");
        fsm.put(StateEvent.GameUnpause, () -> "game");
        fsm.put(StateEvent.Credits, () -> "credits");
        fsm.put(StateEvent.GameEscape, () -> "gui");
        fsm.put(StateEvent.GUIPromptEscape, () -> "gui");
        fsm.put(StateEvent.PauseEscape, () -> "gui");
        fsm.put(StateEvent.GUIPrompt, () -> "configure");
        fsm.put(StateEvent.Quit, () -> "quit");
        fsm.put(StateEvent.GUIEscape, () -> {
            if (initialized)
                return "pause";
            else
                return "gui";
        });
        fsm.put(StateEvent.GameLaunch, () -> {
            initialized = true;
            return "pause";
        });
    }

    private final Logger log = LoggerFactory.getLogger(Tron.class);
    private final String WINDOW_TITLE = "Tear Mango - A Tron Game";
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private String current = "gui";
    private PrintStream errStream = System.err;
    private long windowHandle;

    public static void main(String[] args) {
        new Tron().run();
    }

    public void run() {
        log.debug("LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the windowHandle callbacks and destroy the windowHandle
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        log.info("Initializing game");
        drupe.updateCallbacks(callbacks.get("gui"));

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(errStream).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        log.debug("Configuring GLFW");

        glfwDefaultWindowHints(); // optional, the current windowHandle hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the windowHandle will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the windowHandle will be resizable

        // Create the windowHandle
        windowHandle = glfwCreateWindow(WIDTH, HEIGHT, WINDOW_TITLE, NULL, NULL);

        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW windowHandle");
        }

        // TODO Let Drupe create window
        drupe.setWindowHandle(windowHandle);

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowHandle, (long window, int key, int scancode, int action, int mods) -> {
            drupe.handleKey(window, key, scancode, action, mods);
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the windowHandle size passed to glfwCreateWindow
            glfwGetWindowSize(windowHandle, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the windowHandle
            glfwSetWindowPos(
                    windowHandle,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowHandle);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the windowHandle visible
        glfwShowWindow(windowHandle);

    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
//        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the windowHandle or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(windowHandle)) {

            StateEvent status = drupe.mainLoop(current);
            if (fsm.containsKey(status) && status != StateEvent.Quit) {
                current = fsm.get(status).next();
                log.debug("Current state is " + current);
            } else {
                if (status == StateEvent.Quit) {
                    log.info("Clean exit.");
                } else {
                    log.warn("Unhandled state event: " + status.toString());
                }
                break;
            }
            glfwPollEvents();
        }
    }


}