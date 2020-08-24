package runsystem.vn.kotlin

import java.util.*
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [MinimumAgeValidator::class])
@MustBeDocumented
annotation class MinimumAge(
        val message: String = "Please, insert an age above 13 years old",
        val age1: Int = 0,
        val groups: Array<KClass<Any>> = [],
        val payload: Array<KClass<Payload>> = []
)