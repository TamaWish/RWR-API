package io.github.tamawish.rwr.api.model;

import java.util.Objects;

/**
 * Immutable public view of one world configured in RWR.
 *
 * @param id RWR's case-insensitive configuration ID
 * @param worldName provider/Bukkit world name
 * @param displayName administrator-defined display name
 * @param state validated operational state
 * @param resetCapable whether the current configuration permits guarded resets
 */
public record ManagedWorldSnapshot(
        String id,
        String worldName,
        String displayName,
        ManagedWorldState state,
        boolean resetCapable) {
    /** Validates and creates a world snapshot. */
    public ManagedWorldSnapshot {
        id = requireText(id, "id");
        worldName = requireText(worldName, "worldName");
        displayName = requireText(displayName, "displayName");
        Objects.requireNonNull(state, "state");
        if (resetCapable && state != ManagedWorldState.MANAGED) {
            throw new IllegalArgumentException("Only a MANAGED world can be reset-capable");
        }
    }

    private static String requireText(String value, String name) {
        Objects.requireNonNull(value, name);
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
