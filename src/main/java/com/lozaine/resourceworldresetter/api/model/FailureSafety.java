package com.lozaine.resourceworldresetter.api.model;

/** Indicates whether retrying after an unsuccessful operation is safe. */
public enum FailureSafety {
    /** The provider lifecycle did not enter an ambiguous state and a retry is permitted. */
    SAFE_TO_RETRY,
    /** The provider may have partially changed the world; an administrator must review it. */
    AMBIGUOUS_REVIEW_REQUIRED,
    /** Repeating the same request is not expected to succeed. */
    NOT_RETRYABLE
}
