package com.lozaine.resourceworldresetter.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class SnapshotContractTest {
    @Test
    void managedWorldRequiresMeaningfulValues() {
        assertThatThrownBy(() -> new ManagedWorldSnapshot(null, "world", "World", ManagedWorldState.MANAGED, true))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new ManagedWorldSnapshot(" ", "world", "World", ManagedWorldState.MANAGED, true))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ManagedWorldSnapshot("id", "world", "World", ManagedWorldState.PROTECTED, true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void resetStatusEnforcesOperationIdentity() {
        assertThatThrownBy(() -> new ResetStatusSnapshot(
                        "resource", "resource", null, Optional.empty(), "Idle"))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> new ResetStatusSnapshot(
                        "resource", "resource", ResetPhase.IDLE, Optional.of("operation"), "Idle"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new ResetStatusSnapshot(
                        "resource", "resource", ResetPhase.REGENERATE, Optional.empty(), "Running"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void phaseHelpersClassifyLifecycle() {
        assertThat(ResetPhase.IDLE.isActive()).isFalse();
        assertThat(ResetPhase.PRECHECK.isActive()).isTrue();
        assertThat(ResetPhase.EVACUATE.isActive()).isTrue();
        assertThat(ResetPhase.REGENERATE.isActive()).isTrue();
        assertThat(ResetPhase.VERIFY.isActive()).isTrue();
        assertThat(ResetPhase.COMPLETE.isTerminal()).isTrue();
        assertThat(ResetPhase.COMPLETE.isSuccessful()).isTrue();
        assertThat(ResetPhase.FAILED.isTerminal()).isTrue();
        assertThat(ResetPhase.FAILED.isSuccessful()).isFalse();
        assertThat(ResetPhase.INTERRUPTED.isTerminal()).isTrue();
    }

    @Test
    void optionalOperationIdRemainsImmutable() {
        ResetStatusSnapshot status = new ResetStatusSnapshot(
                "resource", "resource", ResetPhase.PRECHECK, Optional.of("operation"), "Checking");
        assertThat(status.operationId()).contains("operation");
        assertThat(status.isActive()).isTrue();
    }
}
