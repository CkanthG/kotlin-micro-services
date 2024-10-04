package com.sree.user.service

import com.sree.user.dto.AddressDTO
import com.sree.user.dto.UserDTO
import com.sree.user.entity.Address
import com.sree.user.entity.User
import com.sree.user.exception.UserNotFoundException
import com.sree.user.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

/**
 * This service is used to handle all users information.
 */
@Service
class UserService(
    private val userRepository: UserRepository
) {

    /**
     * This method is used to save the user information on db.
     * returns saved user record.
     */
    fun createUser(userDTO: UserDTO): UserDTO {
        val user = userDTOtoUserEntityMapping(null, userDTO)
        return userEntityToUserDTO(userRepository.save(user))
    }

    /**
     * This method is find out latest record on db.
     * returns latest user record.
     */
    private fun getMaxUserId(): Int {
        val userWithMaxId = userRepository.findTopByOrderByIdDesc()
        return userWithMaxId?.id ?: 0  // Return 0 if no user is found
    }

    /**
     * This method will get all user records from db.
     */
    fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll().map { user -> userEntityToUserDTO(user) }.toList()
    }

    /**
     * This method will get specific user record from db by its id.
     */
    fun getUserById(id: Int): UserDTO {
        return userRepository.findById(id).map { user -> userEntityToUserDTO(user) }.orElseThrow {
            UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with id: $id")
        }
    }

    /**
     * This method will get specific user record from db by its name.
     */
    fun getUserByName(username: String): UserDTO {
        return userRepository.findByUsername(username).map { user -> userEntityToUserDTO(user) }.orElseThrow {
            UserNotFoundException(HttpStatus.NOT_FOUND, "User not found with username: $username")
        }
    }

    /**
     * This method will update specific user record inside db by its id.
     */
    fun updateUser(id: Int, userDTO: UserDTO): UserDTO {
        getUserById(id)
        val user = userDTOtoUserEntityMapping(id, userDTO)
        return userEntityToUserDTO(userRepository.save(user))
    }

    /**
     * This method is used to delete the user information from db by its id.
     */
    fun deleteById(id: Int) {
        getUserById(id)
        userRepository.deleteById(id)
    }

    /**
     * This method is used to transform user dto to user entity.
     */
    private fun userDTOtoUserEntityMapping(id: Int?, userDTO: UserDTO): User {
        val userId:Int = id ?: (id ?: (getMaxUserId() + 1))
        val user = User()
            user.id = userId
            user.username = userDTO.username ?: ""
            user.email = userDTO.email ?: ""
            user.mobileNumber = userDTO.mobileNumber ?: ""
            user.password = userDTO.password ?: ""
            user.address = Address(
                userDTO.address?.street ?: "",
                userDTO.address?.city ?: "",
                userDTO.address?.country ?: "",
                userDTO.address?.zipcode ?: 0
            )
        return user
    }

    /**
     * This method is used to transform user entity to user dto.
     */
    private fun userEntityToUserDTO(userResponse: User): UserDTO {
        return UserDTO(
            userResponse.id,
            userResponse.username,
            userResponse.email,
            userResponse.mobileNumber,
            userResponse.password,
            AddressDTO(
                userResponse.address?.street ?: "",
                userResponse.address?.city ?: "",
                userResponse.address?.country ?: "",
                userResponse.address?.zipcode ?: 0,
            )
        )
    }
}