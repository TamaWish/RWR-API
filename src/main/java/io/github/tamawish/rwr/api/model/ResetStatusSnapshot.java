package io.github.tamawish.rwr.api.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable view of the most recent or currently active reset state for a configured world.
 *
 * @param worldId RWR's case-insensitive configuration ID
 * @param worldName provider/Bukkit world name
 * @param phase current or most recent lifecycle phase
 * @param operationId empty only while idle
 * @param message human-readable status detail intended for diagnostics
 */
public record ResetStatusSnapshot(
        String worldId,
        String worldName,
        ResetPhase phase,
        Optional<String> operationId,
        String message) {
    /** Validates and creates a reset status snapshot. */
    public ResetStatusSnapshot {
        worldId = requireText(worldId, "worldId");
        worldName = requireText(worldName, "worldName");
        Objects.requireNonNull(phase, "phase");
        Objects.requireNonNull(operationId, "operationId");
        operationId = operationId.map(value -> requireText(value, "operationId value"));
        message = requireText(message, "message");
        if (phase == ResetPhase.IDLE && operationId.isPresent()) {
            throw new IllegalArgumentException("IDLE status must not have an operation ID");
        }
        if (phase != ResetPhase.IDLE && operationId.isEmpty()) {
            throw new IllegalArgumentException("Non-IDLE status must have an operation ID");
        }
    }

    /**
     * Reports whether a reset is currently running.
     * @return true for an active phase
     */
    public boolean isActive() {
        return phase.isActive();
    }

    /**
     * Reports whether the latest operation has finished.
     * @return true for a terminal phase
     */
    public boolean isTerminal() {
        return phase.isTerminal();
    }

    /**
     * Reports whether the latest operation succeeded.
     * @return true only for complete
     */
    public boolean isSuccessful() {
        return phase.isSuccessful();
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
