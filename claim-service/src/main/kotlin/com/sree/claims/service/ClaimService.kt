package com.sree.claims.service

import com.sree.claims.dto.ClaimDto
import com.sree.claims.entity.Claim
import com.sree.claims.repository.ClaimRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ClaimService(
    var claimRepository: ClaimRepository
) {

    companion object {
        private val log = KotlinLogging.logger {}
    }

    fun saveClaim(claimDto: ClaimDto): ClaimDto {
        log.info { "Request:$claimDto to Save." }
        val claim = Claim(
            null,
            claimDto.claimName,
            claimDto.market,
            claimDto.subMarket,
            claimDto.claimNumber,
            claimDto.claimTotalRequested,
            claimDto.claimTotalPaid,
            claimDto.claimTotalInitial
        )
        val result = claimRepository.save(claim)
        log.info { "Saved Claim Response, claimId : ${result.claimId}" }
        return ClaimDto(
            result.claimId,
            result.claimName,
            result.market,
            result.subMarket,
            result.claimNumber,
            result.claimTotalRequested,
            result.claimTotalPaid,
            result.claimTotalInitial
        )
    }

    fun getClaim(claimId: Long) : ClaimDto {
        val result = claimRepository.findById(claimId).get()
        log.info { "Claim Response : $result by ID : $claimId" }
        return ClaimDto(
            result.claimId,
            result.claimName,
            result.market,
            result.subMarket,
            result.claimNumber,
            result.claimTotalRequested,
            result.claimTotalPaid,
            result.claimTotalInitial
        )
    }
}
