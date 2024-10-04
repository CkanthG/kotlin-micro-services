package com.sree.user.controller

import com.sree.user.dto.AddressDTO
import com.sree.user.dto.UserDTO
import com.sree.user.service.UserService
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration")
@ActiveProfiles("test")
class UserServiceApplicationIT {

    @LocalServerPort
    protected var testPort: Int = -1
    @Autowired
    private lateinit var userService:UserService
    private var USER_URL:String = "/api/v1/users"
    private lateinit var userDTO: UserDTO
    private lateinit var updateUserDTO: UserDTO
    private lateinit var addressDTO: AddressDTO
    private var id:Int = 1
    private var userName:String = "sreekanth"
    private var email:String = "sree@gmail.com"
    private var mobileNumber:String = "9876543210"
    private var password:String = "sree"
    private var street:String = "Alt-Tempelhof"
    private var city:String = "Berlin"
    private var country:String = "Germany"
    private var zipcode:Int = 12099


    companion object {
        // Define the MongoDB container with a latest image
        @Container
        var mongoDBContainer: MongoDBContainer = MongoDBContainer("mongo:latest")

        // Dynamically register MongoDB properties for Spring
        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoDBContainer.replicaSetUrl }
            mongoDBContainer.start()
        }

    }

    @BeforeEach
    fun setUp() {
        RestAssured.port = testPort
        addressDTO = AddressDTO(street, city, country, zipcode)
        userDTO = UserDTO(id, userName, email, mobileNumber, password, addressDTO)
        updateUserDTO = UserDTO(0, userName, email, mobileNumber, password, addressDTO)
    }

    fun authenticated(): RequestSpecification {
        return RestAssured.given()
            .accept(ContentType.JSON).apply {
                this.contentType(ContentType.JSON)
            }
    }

    @Test
    fun test_createUser_withValidData_shouldReturn_201() {
        authenticated()
            .contentType(ContentType.JSON)
            .body(userDTO)
            .post(USER_URL)
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.CREATED.value())
    }

    @Test
    fun test_updateUser_withValidData_shouldReturn_200() {
        val user = userService.createUser(userDTO)
        authenticated()
            .contentType(ContentType.JSON)
            .body(updateUserDTO)
            .put("$USER_URL/${user.id}")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
    }

    @Test
    fun test_getAllUser_withValidData_shouldReturn_200() {
        userService.createUser(userDTO)
        authenticated()
            .get(USER_URL)
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
    }

    @Test
    fun test_getUserById_withValidData_shouldReturn_200() {
        val user = userService.createUser(userDTO)
        authenticated()
            .get("$USER_URL/${user.id}")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
    }

    @Test
    fun test_deleteUserById_withValidData_shouldReturn_204() {
        val user = userService.createUser(userDTO)
        authenticated()
            .delete("$USER_URL/${user.id}")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.NO_CONTENT.value())
    }

}