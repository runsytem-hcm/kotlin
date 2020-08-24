package runsystem.vn.kotlin

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long> {

    @Query(name = "getName", nativeQuery = true)
    fun getName(@Param("name") name: String): List<UserDataDto>
}
