package runsystem.vn.kotlin

interface UserService {
    fun getAllUser(): List<UserDto>
    fun getUser(id: Long): UserDto?
    fun addUser(user: UserRequestDto): UserEntity
    fun getList(name: String): List<UserDataDto>
}