package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorFactoryImplTest {


    private ValidatorFactory validatorFactory;

    @Before
    public void setUp() throws Exception {
        this.validatorFactory = new ValidatorFactoryImpl();
    }

    @Test
    public void shouldReturnUppercaseValidatorWhenOnlyUsingUppercaseType() {
        // Given
        ProcessType type = ProcessType.ONLY_UPPERCASE;

        // When
        Optional<Validator> validatorOptional = validatorFactory.getValidator(type);

        // Then
        assertThat(validatorOptional).isPresent();
        assertThat(validatorOptional.get()).isInstanceOf(AllUpperCaseValidator.class);
    }

    @Test
    public void shouldReturnUppercaseNumberAndSpecialCharValidatorWhenOnlyUsingUppercaseType() {
        // Given
        ProcessType type = ProcessType.UPPERCASE_NUMBERS_SPECIAL_CHAR;

        // When
        Optional<Validator> validatorOptional = validatorFactory.getValidator(type);

        // Then
        assertThat(validatorOptional).isPresent();
        assertThat(validatorOptional.get()).isInstanceOf(UpperCaseNumbersAndSpecialCharsValidator.class);
    }
}