package runsystem.vn.kotlin

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface UserMapper {

    @InheritInverseConfiguration
    fun toEntity(dto: UserRequestDto): UserEntity

    fun toDto(entity: UserEntity): UserDto

    fun toListDto(list: List<UserEntity>): List<UserDto>
}