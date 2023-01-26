package org.itclinicaltest.validators;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UpperCaseNumbersAndSpecialCharsValidatorTest {

    private Validator validator;

    @Before
    public void setUp() throws Exception {
        this.validator = new UpperCaseNumbersAndSpecialCharsValidator();
    }

    @Test
    public void shouldReturnTrueIfCharIsIsUpperCaseAndItIsInTheRightPosition() {
        // Given
        char letter = 'A';
        int n = 3;
        int position = 2;

        // When
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isTrue();
    }

    @Test
    public void shouldReturnTrueIfCharISANumberAndItIsInTheRightPosition() {
        // Given
        char letter = '1';
        int n = 3;
        int position = 2;

        // When
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isTrue();
    }

    @Test
    public void shouldReturnTrueIfCharIsASpecialCharAndItIsInTheRightPosition() {
        // Given
        char letter = '!';
        int n = 3;
        int position = 2;

        // When
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isTrue();
    }

    @Test
    public void shouldReturnTrueIfCharIsAnotherSpecialCharAndItIsInTheRightPosition() {
        // Given
        char letter = '/';
        int n = 3;
        int position = 2;

        // When
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isTrue();
    }

    @Test
    public void shouldReturnFalseIfCharIsASpecialCharAndItIsInTheWrongPosition() {
        // Given
        char letter = '!';
        int n = 3;
        int position = 7;

        // When
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isFalse();
    }

    @Test
    public void shouldReturnFalseIfCharIsASpecialCharButNotValidAndItIsInTheWrongPosition() {
        // Given
        char letter = '°';
        int n = 3;
        int position = 7;

        // When
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isFalse();
    }

    @Test
    public void shouldReturnTrueIfLetterIsUpperCaseAndPositionIsMultipleOfN() {
        // Given
        char letter = 'A';
        int n = 4;
        int position = 3;

        // When
        boolean response = validator.isValid(letter, position, n);

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
        boolean response = validator.isValid(letter, position, n);

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
        boolean response = validator.isValid(letter, position, n);

        // Then
        assertThat(response).isFalse();
    }

}