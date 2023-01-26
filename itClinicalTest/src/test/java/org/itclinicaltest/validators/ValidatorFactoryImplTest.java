package org.itclinicaltest.validators;

import org.itclinicaltest.models.textprocessor.ProcessType;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

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
}