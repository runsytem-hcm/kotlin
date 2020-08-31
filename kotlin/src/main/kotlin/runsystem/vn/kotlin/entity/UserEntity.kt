package runsystem.vn.kotlin.entity

import lombok.AllArgsConstructor
import lombok.Data
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "user")
data class UserEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var name: String,
        var address: String,
        var date: LocalDateTime?
) {
    constructor() : this(null, "", "", date = null)
}