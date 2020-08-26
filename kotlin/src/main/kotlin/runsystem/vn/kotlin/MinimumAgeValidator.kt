package runsystem.vn.kotlin

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class MinimumAgeValidator: ConstraintValidator<MinimumAge, Int> {
    override fun isValid(value: Int?, context: ConstraintValidatorContext?): Boolean {
        if (value != null) {
            return value > Constants().MINIMUM_AGE
        }
        return true
    }
}