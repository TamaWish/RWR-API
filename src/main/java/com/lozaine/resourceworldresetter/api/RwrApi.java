package com.lozaine.resourceworldresetter.api;

import com.lozaine.resourceworldresetter.api.model.ManagedWorldSnapshot;
import com.lozaine.resourceworldresetter.api.model.ResetStatusSnapshot;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.bukkit.Server;

/**
 * Read-only integration surface for ResourceWorldResetter.
 *
 * <p>Implementations are registered with Bukkit's services manager only after RWR has enabled
 * successfully. Callers must not retain a service instance across plugin disable/reload boundaries.
 * Snapshot values are immutable. Implementations must support concurrent reads, but no method promises
 * that the returned data remains current after the call completes.
 *
 * <p>World-ID lookup is case-insensitive. Blank world IDs are rejected with {@link
 * IllegalArgumentException}.
 */
public interface RwrApi {
    /**
     * Finds the currently registered RWR API service.
     *
     * @param server the Bukkit server
     * @return the service, or empty when RWR is absent, disabled, or not yet ready
     */
    static Optional<RwrApi> find(Server server) {
        Objects.requireNonNull(server, "server");
        return Optional.ofNullable(server.getServicesManager().load(RwrApi.class));
    }

    /**
     * Returns an immutable snapshot list of all worlds configured in RWR.
     *
     * @return configured worlds in RWR's stable configuration order
     */
    List<ManagedWorldSnapshot> managedWorlds();

    /**
     * Looks up a configured world by case-insensitive RWR world ID.
     *
     * @param worldId RWR's configured world ID, not necessarily a Bukkit folder name
     * @return the current immutable snapshot, or empty when the ID is unknown
     */
    Optional<ManagedWorldSnapshot> managedWorld(String worldId);

    /**
     * Returns the current reset status for a configured world.
     *
     * @param worldId case-insensitive RWR world ID
     * @return the status snapshot, or empty when the ID is unknown
     */
    Optional<ResetStatusSnapshot> resetStatus(String worldId);
}
