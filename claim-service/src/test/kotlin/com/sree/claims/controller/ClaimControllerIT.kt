package com.sree.claims.controller

import com.sree.claims.dto.ClaimDto
import com.sree.claims.entity.Claim
import com.sree.claims.repository.ClaimRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.jupiter.api.AfterEach
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.junit.jupiter.Container

@ExtendWith(SpringExtension::class, MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration")
@ActiveProfiles("test")
class ClaimControllerIT {

    @Autowired
    lateinit var claimRepository: ClaimRepository
    var claimDto: ClaimDto? = null
    lateinit var claim: Claim
    val claimId: Long = 1
    val claimName: String = "insurance"
    val market: String = "BR"
    val subMarket: Int = 0
    val claimNumber: String = "CLM001"
    val claimTotalRequested: Long = 200
    val claimTotalPaid: Long = 200
    val claimTotalInitial: Long = 100
    @LocalServerPort
    protected var testPort: Int = -1

    companion object {
        // Define the PostgreSQL container
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:latest"))

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            // Configure the Database properties to use the container
            postgresContainer.withUsername("postgres")
            postgresContainer.withPassword("password")
            postgresContainer.withDatabaseName("testdb")
            // Configure the DataSource properties to use the container
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
            // Start the container
            postgresContainer.start()
        }
    }

    @BeforeEach
    fun setUp() {
        claimDto = ClaimDto(claimId, claimName, market, subMarket, claimNumber, claimTotalRequested, claimTotalPaid, claimTotalInitial)
        claim = Claim(null, claimName, market, subMarket, claimNumber, claimTotalRequested, claimTotalPaid, claimTotalInitial)
        RestAssured.port = testPort
    }

    @AfterEach
    fun cleanUp() {
        claimRepository.deleteAll()
    }

    fun authenticated(): RequestSpecification {
        return RestAssured.given()
            .accept(ContentType.JSON).apply {
                this.contentType(ContentType.JSON)
            }
    }

    @Test
    fun saveClaim() {
        authenticated()
            .contentType(ContentType.JSON)
            .body(claimDto)
            .post("/api/v1/claims")
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.CREATED.value())
    }

    @Test
    fun getClaims() {
        val claimResponse = claimRepository.save(claim)
        authenticated()
            .get("/api/v1/claims/" + claimResponse.claimId)
            .then()
            .log().ifValidationFails()
            .statusCode(HttpStatus.OK.value())
    }
}