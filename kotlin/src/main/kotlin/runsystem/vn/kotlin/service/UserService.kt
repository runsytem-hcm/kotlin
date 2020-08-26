package runsystem.vn.kotlin.service

import runsystem.vn.kotlin.dto.UserDataDto
import runsystem.vn.kotlin.dto.UserDto
import runsystem.vn.kotlin.dto.request.UserRequestDto
import runsystem.vn.kotlin.entity.UserEntity

interface UserService {
    fun getAllUser(): List<UserDto>
    fun getUser(id: Long): UserDto?
    fun addUser(user: UserRequestDto): UserEntity
    fun getList(name: String): List<UserDataDto>
}