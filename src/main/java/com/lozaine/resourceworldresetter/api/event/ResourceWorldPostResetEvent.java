package com.lozaine.resourceworldresetter.api.event;

import com.lozaine.resourceworldresetter.api.model.FailureSafety;
import com.lozaine.resourceworldresetter.api.model.ResetFailureType;
import com.lozaine.resourceworldresetter.api.model.ResetPhase;
import java.util.Objects;
import java.util.Optional;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Fired synchronously after an RWR reset attempt reaches a terminal outcome. */
public final class ResourceWorldPostResetEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String operationId;
    private final String worldId;
    private final String worldName;
    private final ResetPhase phase;
    private final ResetFailureType failure;
    private final FailureSafety safety;
    private final String message;

    /**
     * Creates a terminal reset event.
     *
     * @param operationId unique reset operation ID
     * @param worldId RWR configuration ID
     * @param worldName provider/Bukkit world name
     * @param phase terminal lifecycle phase
     * @param failure null only for successful completion
     * @param safety retry-safety classification
     * @param message human-readable diagnostic outcome
     */
    public ResourceWorldPostResetEvent(
            String operationId,
            String worldId,
            String worldName,
            ResetPhase phase,
            ResetFailureType failure,
            FailureSafety safety,
            String message) {
        this.operationId = requireText(operationId, "operationId");
        this.worldId = requireText(worldId, "worldId");
        this.worldName = requireText(worldName, "worldName");
        this.phase = Objects.requireNonNull(phase, "phase");
        this.failure = failure;
        this.safety = Objects.requireNonNull(safety, "safety");
        this.message = requireText(message, "message");
        if (!phase.isTerminal()) {
            throw new IllegalArgumentException("Post-reset phase must be terminal");
        }
        if (phase == ResetPhase.COMPLETE && failure != null) {
            throw new IllegalArgumentException("Successful completion must not have a failure type");
        }
        if (phase != ResetPhase.COMPLETE && failure == null) {
            throw new IllegalArgumentException("Unsuccessful completion must have a failure type");
        }
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

    /** Returns the provider/Bukkit world name.
     * @return world name
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * Returns the legacy provider-world accessor.
     * @return the same value as {@link #getWorldName()}
     * @deprecated use {@link #getWorldName()}; this provider-neutral API supports more than Multiverse
     */
    @Deprecated(since = "5.1", forRemoval = false)
    public String getMultiverseWorld() {
        return worldName;
    }

    /** Returns the terminal lifecycle phase.
     * @return terminal phase
     */
    public ResetPhase getPhase() {
        return phase;
    }

    /**
     * Returns the failure category.
     * @return empty for success, otherwise the failure category
     */
    public Optional<ResetFailureType> getFailure() {
        return Optional.ofNullable(failure);
    }

    /** Returns retry-safety guidance.
     * @return safety classification
     */
    public FailureSafety getSafety() {
        return safety;
    }

    /** Returns diagnostic outcome text.
     * @return nonblank diagnostic message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Reports whether the reset completed successfully.
     * @return true only for complete
     */
    public boolean isSuccessful() {
        return phase.isSuccessful();
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
