package com.sree.claims.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sree.claims.dto.ClaimDto
import com.sree.claims.entity.Claim
import com.sree.claims.service.ClaimService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ClaimController::class)
class ClaimControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var claimService: ClaimService
    @Autowired
    lateinit var objectMapper: ObjectMapper
    lateinit var claimDto: ClaimDto
    lateinit var claim: Claim
    val claimId: Long = 1
    val claimName: String = "insurance"
    val market: String = "BR"
    val subMarket: Int = 0
    val claimNumber: String = "CLM001"
    val claimTotalRequested: Long = 200
    val claimTotalPaid: Long = 200
    val claimTotalInitial: Long = 100
    val requestedUser: String = "testuser"

    @BeforeEach
    fun setUp() {
        claimDto = ClaimDto(claimId, claimName, market, subMarket, claimNumber, claimTotalRequested, claimTotalPaid, claimTotalInitial)
        claim = Claim(null, claimName, market, subMarket, claimNumber, claimTotalRequested, claimTotalPaid, claimTotalInitial, requestedUser)
    }

    @Test
    fun testSaveClaim_WithValidData_ShouldReturn201StatusCode() {
        // given
        claimDto.requestedUser = requestedUser
        // Mock the service call to return the same claimDto
        `when`(claimService.saveClaim(claimDto)).thenReturn(claimDto)

        // Serialize claimDto to JSON
        val claimDtoJson = objectMapper.writeValueAsString(claimDto)

        // Perform the POST request
        mockMvc.perform(
            post("/api/v1/claims")
                .contentType(MediaType.APPLICATION_JSON)  // Set content type to JSON
                .content(claimDtoJson)  // Provide the serialized claimDto as the request body
        )
            .andExpect(status().isCreated)  // Expect HTTP 201 status
            .andExpect(content().json(claimDtoJson))  // verify the response body
    }

    @Test
    fun testSaveClaim_WithInValidData_ShouldReturn400StatusCode() {
        // Mock the service call to return the same claimDto
        `when`(claimService.saveClaim(claimDto)).thenReturn(claimDto)

        // Serialize claimDto to JSON
        val claimDtoJson = objectMapper.writeValueAsString(claimDto)

        // Perform the POST request
        mockMvc.perform(
            post("/api/v1/claims")
                .contentType(MediaType.APPLICATION_JSON)  // Set content type to JSON
                .content(claimDtoJson)  // Provide the serialized claimDto as the request body
        )
            .andExpect(status().isBadRequest)  // Expect HTTP 400 status
    }

    @Test
    fun testGetClaims_WithValidData_ShouldReturn200StatusCode() {
        // Mock service
        `when`(claimService.getClaim(claimId)).thenReturn(claimDto)

        // When and Then: Perform GET request and check response
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/claims/{id}", claimId)
                .contentType(MediaType.APPLICATION_JSON)  // Set content type to JSON
        )
            .andExpect(status().isOk)  // Expect HTTP 200 status
            .andExpect(content().json(objectMapper.writeValueAsString(claimDto)))  // verify the response body
    }

    @Test
    fun getAllClaims_WithValidData_ShouldReturn200StatusCode() {
        // Mock service
        `when`(claimService.getAllClaims()).thenReturn(listOf(claimDto))

        // When and Then: Perform GET request and check response
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/claims")
                .contentType(MediaType.APPLICATION_JSON)  // Set content type to JSON
        )
            .andExpect(status().isOk)  // Expect HTTP 200 status
            .andExpect(content().json(listOf(objectMapper.writeValueAsString(claimDto)).toString()))  // verify the response body
    }
}