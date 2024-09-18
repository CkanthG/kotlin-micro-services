package com.sree.user.service

import com.sree.user.dto.AddressDTO
import com.sree.user.dto.UserDTO
import com.sree.user.entity.Address
import com.sree.user.entity.User
import com.sree.user.exception.UserNotFoundException
import com.sree.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class UserServiceTest {

    @InjectMocks
    lateinit var userService: UserService
    @Mock
    lateinit var userRepository: UserRepository
    private lateinit var userDTO: UserDTO
    private lateinit var updateUserDTO: UserDTO
    private lateinit var addressDTO: AddressDTO
    private lateinit var user: User
    private var id:Int = 1
    private var userName:String = "sreekanth"
    private var email:String = "sree@gmail.com"
    private var mobileNumber:String = "9876543210"
    private var password:String = "sree"
    private var street:String = "Alt-Tempelhof"
    private var city:String = "Berlin"
    private var country:String = "Germany"
    private var zipcode:Int = 12099

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // given
        addressDTO = AddressDTO(street, city, country, zipcode)
        userDTO = UserDTO(id, userName, email, mobileNumber, password, addressDTO)
        updateUserDTO = UserDTO(0, userName, email, mobileNumber, password, addressDTO)
        user = User()
        user.username =userName
        user.email = email
        user.mobileNumber = mobileNumber
        user.password = password
        user.address = Address(street, city, country, zipcode)
    }

    @Test
    fun test_createUser_withValidData_shouldSuccess() {
        // given
        user.id = id
        // when
        `when`(userRepository.findTopByOrderByIdDesc()).thenReturn(user)
        `when`(userRepository.save(any(User::class.java))).thenReturn(user)
        // then
        val actual = userService.createUser(userDTO)
        assertEquals(user.username, actual.username)
        assertEquals(user.email, actual.email)
        assertEquals(user.mobileNumber, actual.mobileNumber)
        assertEquals(user.password, actual.password)
        // verify
        verify(userRepository, times(1)).findTopByOrderByIdDesc()
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun test_getAllUsers_withValidData_shouldSuccess() {
        // when
        `when`(userRepository.findAll()).thenReturn(listOf(user))
        // then
        val actual = userService.getAllUsers()
        assertEquals(1, actual.size)
        // verify
        verify(userRepository, times(1)).findAll()
    }

    @Test
    fun test_getUserById_withValidData_shouldSuccess() {
        // given
        user.id = id
        // when
        `when`(userRepository.findById(id)).thenReturn(Optional.of(user))
        // then
        val actual = userService.getUserById(id)
        assertEquals(id, actual.id)
        assertEquals(user.username, actual.username)
        assertEquals(user.email, actual.email)
        assertEquals(user.mobileNumber, actual.mobileNumber)
        assertEquals(user.password, actual.password)
        // verify
        verify(userRepository, times(1)).findById(id)
    }

    @Test
    fun test_getUserById_withInValidData_shouldThrowsUserNotFoundException() {
        assertThrows<UserNotFoundException> {
            userService.getUserById(100)
        }
    }

    @Test
    fun test_updateUser_withValidData_shouldSuccess() {
        // given
        user.id = id
        // when
        `when`(userRepository.findById(id)).thenReturn(Optional.of(user))
        `when`(userRepository.save(any(User::class.java))).thenReturn(user)
        // then
        val actual = userService.updateUser(id, updateUserDTO)
        assertEquals(id, actual.id)
        assertEquals(id, actual.id)
        assertEquals(user.username, actual.username)
        assertEquals(user.email, actual.email)
        assertEquals(user.mobileNumber, actual.mobileNumber)
        assertEquals(user.password, actual.password)
        // verify
        verify(userRepository, times(1)).findById(id)
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun test_updateUser_withInValidData_shouldThrowsUserNotFoundException() {
        assertThrows<UserNotFoundException> {
            userService.updateUser(100, updateUserDTO)
        }
    }

    @Test
    fun test_deleteById_withValidData_shouldSuccess() {
        // given
        user.id = id
        // when
        `when`(userRepository.findById(id)).thenReturn(Optional.of(user))
        doNothing().`when`(userRepository).deleteById(id)
        // then
        userService.deleteById(id)
        // verify
        verify(userRepository, times(1)).findById(id)
        verify(userRepository, times(1)).deleteById(id)
    }

    @Test
    fun test_deleteById_withInValidData_shouldThrowsUserNotFoundException() {
        assertThrows<UserNotFoundException> {
            userService.getUserById(100)
        }
    }
}