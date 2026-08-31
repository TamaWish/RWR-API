package io.github.tamawish.rwr.api.model;

/** Public reset lifecycle phase. */
public enum ResetPhase {
    /** No reset has run since this runtime started. */
    IDLE,
    /** RWR is validating that the operation can start safely. */
    PRECHECK,
    /** Players are being moved out of the target world. */
    EVACUATE,
    /** The authoritative world provider is regenerating the world. */
    REGENERATE,
    /** RWR is independently checking the regenerated world. */
    VERIFY,
    /** The reset and verification completed successfully. */
    COMPLETE,
    /** The reset attempt ended with a known failure. */
    FAILED,
    /** A prior operation was recovered as interrupted after startup. */
    INTERRUPTED;

    /**
     * Reports whether a reset operation is currently running.
     * @return true for an active phase
     */
    public boolean isActive() {
        return this == PRECHECK || this == EVACUATE || this == REGENERATE || this == VERIFY;
    }

    /**
     * Reports whether this phase represents a finished operation.
     * @return true for a terminal phase
     */
    public boolean isTerminal() {
        return this == COMPLETE || this == FAILED || this == INTERRUPTED;
    }

    /**
     * Reports whether this phase is successful.
     * @return true only for {@link #COMPLETE}
     */
    public boolean isSuccessful() {
        return this == COMPLETE;
    }
}
