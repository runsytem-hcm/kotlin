package runsystem.vn.kotlin.mapper

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import runsystem.vn.kotlin.dto.UserDto
import runsystem.vn.kotlin.dto.request.UserRequestDto
import runsystem.vn.kotlin.entity.UserEntity

@Mapper
interface UserMapper {

    @InheritInverseConfiguration
    fun toEntity(dto: UserRequestDto): UserEntity

    fun toDto(entity: UserEntity): UserDto

    fun toListDto(list: List<UserEntity>): List<UserDto>
}