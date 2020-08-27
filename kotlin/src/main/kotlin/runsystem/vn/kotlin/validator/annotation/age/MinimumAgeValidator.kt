package runsystem.vn.kotlin.validator.annotation.age

import runsystem.vn.kotlin.common.constants.CommonConstants
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class MinimumAgeValidator: ConstraintValidator<MinimumAge, Int> {
    override fun isValid(value: Int?, context: ConstraintValidatorContext?): Boolean {
        if (value != null) {
            return value > CommonConstants().CONST_MINIMUM_AGE
        }
        return true
    }
}