package com.sree.claims.service

import com.sree.claims.dto.ClaimDto
import com.sree.claims.entity.Claim
import com.sree.claims.repository.ClaimRepository
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.core.env.Environment
import org.springframework.web.client.RestClient
import java.util.*

class ClaimServiceTest {

    @InjectMocks
    lateinit var claimService: ClaimService
    @Mock
    lateinit var claimRepository: ClaimRepository
    @Mock
    lateinit var restClient: RestClient.Builder
    @Mock
    lateinit var environment: Environment
    private lateinit var userUrlString: String
    private lateinit var claimDto: ClaimDto
    private lateinit var claim: Claim
    private val claimId: Long = 1
    private val claimName: String = "insurance"
    private val market: String = "BR"
    private val subMarket: Int = 0
    private val claimNumber: String = "CLM001"
    private val claimTotalRequested: Long = 200
    private val claimTotalPaid: Long = 200
    private val claimTotalInitial: Long = 100
    private val requestedUser: String = "tester"

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userUrlString = "http://user-service/api/v1/users"
        // given
        claimDto = ClaimDto(claimId, claimName, market, subMarket, claimNumber, claimTotalRequested, claimTotalPaid, claimTotalInitial, requestedUser)
        claim = Claim(null, claimName, market, subMarket, claimNumber, claimTotalRequested, claimTotalPaid, claimTotalInitial, requestedUser)
    }

    @Test
    fun testSaveClaim_withValidData_shouldReturnClaimDto() {
        // Given
        val claimService = mock(ClaimService::class.java)
        val claimRepository = mock(ClaimRepository::class.java)
        // When
        doNothing().`when`(claimService).userCheck(anyString())
        `when`(claimRepository.save(claim)).thenReturn(claim)
        `when`(claimService.saveClaim(claimDto)).thenReturn(claimDto)

        // Then: Call the saveClaim method
        val actual = claimService.saveClaim(claimDto)
        assertEquals(claimDto.claimName, actual.claimName)
        assertEquals(claimDto.market, actual.market)
        assertEquals(claimDto.subMarket, actual.subMarket)
        assertEquals(claimDto.claimNumber, actual.claimNumber)
        assertEquals(claimDto.claimTotalRequested, actual.claimTotalRequested)
        assertEquals(claimDto.claimTotalPaid, actual.claimTotalPaid)
        assertEquals(claimDto.claimTotalInitial, actual.claimTotalInitial)
        assertEquals(claimDto.requestedUser, actual.requestedUser)
    }

    @Test
    fun testGetClaim_withValidData_shouldReturnClaimDto() {
        // When
        `when`(claimRepository.findById(claimId)).thenReturn(Optional.of(claim))

        // Then: Call the getClaim method
        val actual = claimService.getClaim(claimId)
        assertEquals(claimDto.claimName, actual.claimName)
        assertEquals(claimDto.market, actual.market)
        assertEquals(claimDto.subMarket, actual.subMarket)
        assertEquals(claimDto.claimNumber, actual.claimNumber)
        assertEquals(claimDto.claimTotalRequested, actual.claimTotalRequested)
        assertEquals(claimDto.claimTotalPaid, actual.claimTotalPaid)
        assertEquals(claimDto.claimTotalInitial, actual.claimTotalInitial)
        assertEquals(claimDto.requestedUser, actual.requestedUser)
    }

    @Test
    fun testGetClaim_withInValidData_shouldThrowEntityNotFoundException() {
        assertThrows<EntityNotFoundException>{
            claimService.getClaim(10000)
        }
    }

    @Test
    fun testGetAllClaims_withValidData_shouldReturnListOfClaimDto() {
        // When
        `when`(claimRepository.findAll()).thenReturn(listOf(claim))

        // Then: Call the getAllClaims method
        val listOfClaims = claimService.getAllClaims()
        val actual = listOfClaims?.first()
        assertEquals(claimDto.claimName, actual?.claimName)
        assertEquals(claimDto.market, actual?.market)
        assertEquals(claimDto.subMarket, actual?.subMarket)
        assertEquals(claimDto.claimNumber, actual?.claimNumber)
        assertEquals(claimDto.claimTotalRequested, actual?.claimTotalRequested)
        assertEquals(claimDto.claimTotalPaid, actual?.claimTotalPaid)
        assertEquals(claimDto.claimTotalInitial, actual?.claimTotalInitial)
        assertEquals(claimDto.requestedUser, actual?.requestedUser)
    }
}