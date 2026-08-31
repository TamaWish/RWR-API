package io.github.tamawish.rwr.api.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.tamawish.rwr.api.model.FailureSafety;
import io.github.tamawish.rwr.api.model.ResetFailureType;
import io.github.tamawish.rwr.api.model.ResetPhase;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class ResetEventContractTest {
    @Test
    void warningEventCarriesScheduleWithoutAnOperation() {
        Instant scheduledResetAt = Instant.parse("2026-08-29T12:00:00Z");
        ResourceWorldResetWarningEvent event = new ResourceWorldResetWarningEvent(
                "resource", "resource_world", 5, scheduledResetAt);

        assertThat(event.getWorldId()).isEqualTo("resource");
        assertThat(event.getWorldName()).isEqualTo("resource_world");
        assertThat(event.getMinutesRemaining()).isEqualTo(5);
        assertThat(event.getScheduledResetAt()).isEqualTo(scheduledResetAt);
        assertThat(event.isAsynchronous()).isFalse();
        assertThat(event.getHandlers()).isSameAs(ResourceWorldResetWarningEvent.getHandlerList());
    }

    @Test
    void warningEventAllowsZeroMinutes() {
        ResourceWorldResetWarningEvent event = new ResourceWorldResetWarningEvent(
                "resource", "resource_world", 0, Instant.EPOCH);

        assertThat(event.getMinutesRemaining()).isZero();
    }

    @Test
    void warningEventRejectsInvalidValues() {
        assertThatThrownBy(() -> new ResourceWorldResetWarningEvent(
                        " ", "resource_world", 5, Instant.EPOCH))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ResourceWorldResetWarningEvent(
                        "resource", "resource_world", -1, Instant.EPOCH))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ResourceWorldResetWarningEvent(
                        "resource", "resource_world", 5, null))
                .isInstanceOf(NullPointerException.class);
    }

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
