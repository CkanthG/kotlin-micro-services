package com.sree.claims.service

import com.sree.claims.dto.ClaimDto
import com.sree.claims.dto.UserDTO
import com.sree.claims.entity.Claim
import com.sree.claims.repository.ClaimRepository
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import kotlin.jvm.optionals.getOrElse

/**
 * Delegate request to Repository layer and respond back with proper response.
 */
@Service
class ClaimService(
    var claimRepository: ClaimRepository,
    var restClient: RestClient.Builder,
    var environment: Environment
) {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    /**
     * Save claim information into DB.
     * @param claimDto is used to store claim details.
     * @return saved claim details.
     */
    fun saveClaim(claimDto: ClaimDto): ClaimDto {
        val userUrlString = environment.getProperty("application.config.user-service-url")
        val urlString = "$userUrlString/username/${claimDto.requestedUser}"
        // user exist check with user microservice
        userCheck(urlString)
        log.info { "Request:$claimDto to Save." }
        val claim = Claim(
            null,
            claimDto.claimName,
            claimDto.market,
            claimDto.subMarket,
            claimDto.claimNumber,
            claimDto.claimTotalRequested,
            claimDto.claimTotalPaid,
            claimDto.claimTotalInitial,
            claimDto.requestedUser
        )
        val result = claimRepository.save(claim)
        return extractClaimDtoFromClaim(result)
    }

    fun userCheck(urlString: String) {
        val userResponse = restClient
            .baseUrl(urlString)
            .build()
            .get()
            .retrieve()
            .body<UserDTO>()
        log.info { "userResponse : $userResponse" }
    }

    /**
     * Get claim information by claimId.
     * @param claimId used to identify claim.
     * @return matched claim response.
     * @throws EntityNotFoundException if claim information not found.
     */
    fun getClaim(claimId: Long) : ClaimDto {
        log.info { "Requested claim id : $claimId" }
        val result = claimRepository.findById(claimId).getOrElse {
            throw EntityNotFoundException("Claim not found with specified id : $claimId")
        }
        return extractClaimDtoFromClaim(result as Claim)
    }

    /**
     * Generic method to convert claim entity to claim dto.
     * @param claim entity object to convert.
     * @return claim dto converted object.
     */
    private fun extractClaimDtoFromClaim(claim: Claim): ClaimDto {
        return ClaimDto(
            claim.claimId,
            claim.claimName,
            claim.market,
            claim.subMarket,
            claim.claimNumber,
            claim.claimTotalRequested,
            claim.claimTotalPaid,
            claim.claimTotalInitial,
            claim.requestedUser
        )
    }

    /**
     * Get all claims.
     * @return list of claims.
     */
    fun getAllClaims(): List<ClaimDto>? {
        return claimRepository.findAll().map { claim -> extractClaimDtoFromClaim(claim) }
    }
}
