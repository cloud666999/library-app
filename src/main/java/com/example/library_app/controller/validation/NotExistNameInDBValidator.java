package com.example.library_app.controller.validation;


import com.example.library_app.repository.BookRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotExistNameInDBValidator  implements ConstraintValidator<NotExistNameInDB ,String> {
    BookRepository bookRepository;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isBlank(value)) {
            return true;
        }
        return !bookRepository.existByName(value);
    }
}
