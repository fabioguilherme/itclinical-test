package org.itclinicaltest.models.textprocessor;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcessTypeTest {

    @Test
    public void canGetUpperCaseNumbersSpecialCharUsingProcessName() {
        // Given
        String processName = "special";

        // When
        Optional<ProcessType> processType = ProcessType.getProcessTypeProcessName(processName);

        // Then
        assertThat(processType).isPresent();
        assertThat(processType.get()).isEqualTo(ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR);

    }

    @Test
    public void canGetOnlyUpperCaseUsingProcessName() {
        // Given
        String processName = "upper";

        // When
        Optional<ProcessType> processType = ProcessType.getProcessTypeProcessName(processName);

        // Then
        assertThat(processType).isPresent();
        assertThat(processType.get()).isEqualTo(ProcessType.ONLY_UPPERCASE);

    }

    @Test
    public void shouldReturnAEmptyOptionalWhenNoProcessTypeIsFound() {
        // Given
        String processName = "special1";

        // When
        Optional<ProcessType> processType = ProcessType.getProcessTypeProcessName(processName);

        // Then
        assertThat(processType).isEmpty();
    }
}