package ovs.graph;

import java.util.UUID;

public abstract class Popup {

    public UUID id = UUID.randomUUID();

    /**
     * return true when handled
     */
    public abstract boolean show();
}
