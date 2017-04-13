package mango.core.callbacks;

import mango.core.drupe.DrupeSystem;

public class CallbacksManager {
    CallbackBase currentCallbacks;
    CallbackBase previousCallbacks;

    private DrupeSystem drupe;

    public CallbacksManager(DrupeSystem dm) {
        drupe = dm;
    }

    public void updateCallbacks(CallbackBase cb) {
        drupe.updateCallbacks(cb);
    }
}
