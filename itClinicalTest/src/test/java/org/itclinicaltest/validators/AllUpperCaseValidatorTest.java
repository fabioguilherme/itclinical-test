package org.itclinicaltest.validators;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AllUpperCaseValidatorTest {

    private Validator allUpperCaseValidator;

    @Before
    public void setUp() throws Exception {
        this.allUpperCaseValidator = new AllUpperCaseValidator();
    }

    @Test
    public void shouldReturnTrueIfLetterIsUpperCaseAndItIsInTheRightPosition() {
        // Given
        char letter = 'A';
        int n = 3;
        int position = 2;

        // When
        boolean response = allUpperCaseValidator.isValid(letter, position, n);

        // Then
        assertThat(response).isTrue();
    }

    @Test
    public void shouldReturnTrueIfLetterIsUpperCaseAndPositionIsMultipleOfN() {
        // Given
        char letter = 'A';
        int n = 4;
        int position = 3;

        // When
        boolean response = allUpperCaseValidator.isValid(letter, position, n);

        // Then
        assertThat(response).isTrue();
    }

    @Test
    public void shouldReturnFalseIfLetterIsNotUpperCaseAndItIsNotInTheRightPosition() {
        // Given
        char letter = 'a';
        int n = 1;
        int position = 0;

        // When
        boolean response = allUpperCaseValidator.isValid(letter, position, n);

        // Then
        assertThat(response).isFalse();
    }

    @Test
    public void shouldReturnFalseIfLetterIsUpperCaseAndItIsNotInTheRightPosition() {
        // Given
        char letter = 'A';
        int n = 5;
        int position = 3;

        // When
        boolean response = allUpperCaseValidator.isValid(letter, position, n);

        // Then
        assertThat(response).isFalse();
    }
}