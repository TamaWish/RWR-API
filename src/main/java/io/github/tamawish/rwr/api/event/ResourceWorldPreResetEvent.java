package io.github.tamawish.rwr.api.event;

import java.util.Objects;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Fired synchronously immediately before RWR begins a guarded reset. */
public final class ResourceWorldPreResetEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String operationId;
    private final String worldId;
    private final String worldName;
    private boolean cancelled;

    /**
     * Creates a pre-reset event.
     * @param operationId unique reset operation ID
     * @param worldId RWR configuration ID
     * @param worldName provider/Bukkit world name
     */
    public ResourceWorldPreResetEvent(String operationId, String worldId, String worldName) {
        this.operationId = requireText(operationId, "operationId");
        this.worldId = requireText(worldId, "worldId");
        this.worldName = requireText(worldName, "worldName");
    }

    /** Returns the operation ID.
     * @return unique operation ID
     */
    public String getOperationId() {
        return operationId;
    }

    /** Returns the RWR world ID.
     * @return configuration ID
     */
    public String getWorldId() {
        return worldId;
    }

    /**
     * Returns the provider/Bukkit world name, which may differ from the RWR world ID.
     * @return world name
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * Compatibility alias retained from RWR 5.0's event surface.
     *
     * @return the same value as {@link #getWorldName()}
     * @deprecated use {@link #getWorldName()}; this provider-neutral API supports more than Multiverse
     */
    @Deprecated(since = "5.1", forRemoval = false)
    public String getMultiverseWorld() {
        return worldName;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Returns Bukkit's handler registry for this event type.
     * @return handler registry
     */
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
