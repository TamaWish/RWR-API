package io.github.tamawish.rwr.api.model;

/** Stable categories for unsuccessful reset attempts. */
public enum ResetFailureType {
    /** The requested RWR configuration ID does not exist. */ UNKNOWN_WORLD_ID,
    /** The configured world is not eligible for reset. */ WORLD_NOT_MANAGED,
    /** That world already has a reset in progress. */ WORLD_BUSY,
    /** A different world currently owns the global heavy-reset lock. */ GLOBAL_RESET_BUSY,
    /** A pre-reset event listener cancelled the operation. */ EVENT_CANCELLED,
    /** The world provider no longer registers the configured world. */ WORLD_NOT_REGISTERED,
    /** The configured world is not currently loaded. */ WORLD_NOT_LOADED,
    /** The provider returned a different world identity after regeneration. */ WORLD_IDENTITY_CHANGED,
    /** Player evacuation is disabled for the target. */ EVACUATION_DISABLED,
    /** RWR could not resolve a safe evacuation destination. */ EVACUATION_DESTINATION_UNAVAILABLE,
    /** Moving players out of the target failed. */ EVACUATION_FAILED,
    /** Players remained in the target after evacuation. */ PLAYERS_REMAINING,
    /** The world provider rejected the regeneration request. */ PROVIDER_REJECTED,
    /** The provider could not delete the old world data. */ WORLD_DELETE_FAILED,
    /** The provider could not create the replacement world. */ WORLD_CREATE_FAILED,
    /** The provider API raised an unexpected exception. */ PROVIDER_API_EXCEPTION,
    /** Independent verification of the regenerated world failed. */ VERIFICATION_FAILED,
    /** RWR could not persist the operation recovery journal. */ JOURNAL_UNAVAILABLE,
    /** Startup recovery found an operation without a terminal record. */ INTERRUPTED_OPERATION
}
