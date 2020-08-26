package runsystem.vn.kotlin.service.impl

import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import runsystem.vn.kotlin.common.Constants
import runsystem.vn.kotlin.config.RestTemplateClient
import runsystem.vn.kotlin.dto.UserDataDto
import runsystem.vn.kotlin.dto.UserDto
import runsystem.vn.kotlin.dto.request.UserRequestDto
import runsystem.vn.kotlin.entity.UserEntity
import runsystem.vn.kotlin.mapper.UserMapper
import runsystem.vn.kotlin.repository.UserRepository
import runsystem.vn.kotlin.service.UserService
import java.lang.Exception
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val restTemplateClient: RestTemplateClient) : UserService {

    override fun getAllUser(): List<UserDto> {
//        val params: MutableMap<String, String> = HashMap()
//        params["1"] = "2"
//        val rest = restTemplateClient.postBody(params, "", String().javaClass)

//        val res = userRepository.getName("Hiệp")
//        val mapper1 = ObjectMapper()
//        println(mapper1.writeValueAsString(res).toString())
        val mapper = Mappers.getMapper(UserMapper::class.java)
        return mapper.toListDto(userRepository.findAll().toList())
    }

    override fun getUser(id: Long): UserDto? {
        val res = userRepository.findById(id)
        val mapper = Mappers.getMapper(UserMapper::class.java)

        if (res.isPresent) {
            return mapper.toDto(res.get())
        }
        return null
    }

    @Transactional(rollbackFor = [Exception::class])
    override fun addUser(user: UserRequestDto): UserEntity {
        val zonedToday = ZonedDateTime.now(ZoneId.of(Constants().CONST_TIME_ZONE))
        val mapper = Mappers.getMapper(UserMapper::class.java)
        val entity = mapper.toEntity(user)
        entity.date = zonedToday.toLocalDateTime()
        return userRepository.save(entity)
    }

    override fun getList(name: String): List<UserDataDto> {
        return userRepository.getName(name)
    }
}