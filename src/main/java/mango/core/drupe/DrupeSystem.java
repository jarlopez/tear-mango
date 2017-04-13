package mango.core.drupe;

import mango.core.callbacks.CallbackBase;
import mango.game.StateEvent;

public class DrupeSystem {
    private StateEvent returnCode = StateEvent.NotImplemented; // TODO
    private CallbackBase currentCallbacks;

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

    public StateEvent mainLoop(String current) {
        while (returnCode == StateEvent.None) {
            // TODO
        }
        return returnCode;
    }
}
