package io.github.tamawish.rwr.api.model;

/** Operational state assigned to a world by RWR's validated configuration. */
public enum ManagedWorldState {
    /** Enabled, managed, and eligible for guarded resets. */
    MANAGED,
    /** Present in configuration but disabled. */
    DISABLED,
    /** Explicitly protected from reset operations. */
    PROTECTED,
    /** Configured world no longer resolves through the active world provider. */
    ORPHANED
}
