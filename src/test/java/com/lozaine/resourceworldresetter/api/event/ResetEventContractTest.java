package com.lozaine.resourceworldresetter.api.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lozaine.resourceworldresetter.api.model.FailureSafety;
import com.lozaine.resourceworldresetter.api.model.ResetFailureType;
import com.lozaine.resourceworldresetter.api.model.ResetPhase;
import org.junit.jupiter.api.Test;

class ResetEventContractTest {
    @Test
    void preResetEventCanBeCancelled() {
        ResourceWorldPreResetEvent event = new ResourceWorldPreResetEvent("operation", "resource", "resource_world");
        event.setCancelled(true);
        assertThat(event.isCancelled()).isTrue();
        assertThat(event.getWorldName()).isEqualTo("resource_world");
    }

    @Test
    void successfulPostEventHasNoFailure() {
        ResourceWorldPostResetEvent event = new ResourceWorldPostResetEvent(
                "operation",
                "resource",
                "resource_world",
                ResetPhase.COMPLETE,
                null,
                FailureSafety.NOT_RETRYABLE,
                "Complete");
        assertThat(event.isSuccessful()).isTrue();
        assertThat(event.getFailure()).isEmpty();
    }

    @Test
    void failedPostEventCarriesFailureAndSafety() {
        ResourceWorldPostResetEvent event = new ResourceWorldPostResetEvent(
                "operation",
                "resource",
                "resource_world",
                ResetPhase.FAILED,
                ResetFailureType.VERIFICATION_FAILED,
                FailureSafety.AMBIGUOUS_REVIEW_REQUIRED,
                "Could not verify world identity");
        assertThat(event.isSuccessful()).isFalse();
        assertThat(event.getFailure()).contains(ResetFailureType.VERIFICATION_FAILED);
    }

    @Test
    void postEventRejectsNonTerminalPhase() {
        assertThatThrownBy(() -> new ResourceWorldPostResetEvent(
                        "operation",
                        "resource",
                        "resource_world",
                        ResetPhase.VERIFY,
                        null,
                        FailureSafety.NOT_RETRYABLE,
                        "Still running"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
