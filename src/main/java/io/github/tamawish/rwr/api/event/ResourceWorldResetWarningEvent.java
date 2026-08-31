package io.github.tamawish.rwr.api.event;

import java.time.Instant;
import java.util.Objects;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Fired synchronously when RWR sends a configured warning for a scheduled reset. */
public final class ResourceWorldResetWarningEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String worldId;
    private final String worldName;
    private final int minutesRemaining;
    private final Instant scheduledResetAt;

    /**
     * Creates a scheduled-reset warning event.
     *
     * <p>Warnings occur before RWR allocates a reset operation ID. A warned reset may not run if
     * its schedule or world configuration changes afterward.
     *
     * @param worldId RWR configuration ID
     * @param worldName provider/Bukkit world name
     * @param minutesRemaining whole warning minutes remaining, including zero
     * @param scheduledResetAt scheduled reset instant represented by this warning
     */
    public ResourceWorldResetWarningEvent(
            String worldId, String worldName, int minutesRemaining, Instant scheduledResetAt) {
        this.worldId = requireText(worldId, "worldId");
        this.worldName = requireText(worldName, "worldName");
        if (minutesRemaining < 0) {
            throw new IllegalArgumentException("minutesRemaining must not be negative");
        }
        this.minutesRemaining = minutesRemaining;
        this.scheduledResetAt = Objects.requireNonNull(scheduledResetAt, "scheduledResetAt");
    }

    /** Returns the RWR world ID.
     * @return configuration ID
     */
    public String getWorldId() {
        return worldId;
    }

    /** Returns the provider/Bukkit world name.
     * @return world name
     */
    public String getWorldName() {
        return worldName;
    }

    /** Returns the whole warning minutes remaining before the scheduled reset.
     * @return nonnegative minutes remaining
     */
    public int getMinutesRemaining() {
        return minutesRemaining;
    }

    /** Returns the scheduled reset instant represented by this warning.
     * @return scheduled reset instant
     */
    public Instant getScheduledResetAt() {
        return scheduledResetAt;
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
