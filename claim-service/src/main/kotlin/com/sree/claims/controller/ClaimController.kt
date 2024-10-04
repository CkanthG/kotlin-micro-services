package com.sree.claims.controller

import com.sree.claims.dto.ClaimDto
import com.sree.claims.service.ClaimService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * It will accept request from UI and supply corresponding response.
 */
@RestController
@RequestMapping("/api/v1/claims")
class ClaimController(
    val claimService: ClaimService
) {

    /**
     * This will accept claimDto object and read the object and save data into DB.
     * @param claimDto used to accept claim request.
     * @return saved claim response.
     */
    @PostMapping
    fun saveClaim(@Valid @RequestBody claimDto: ClaimDto): ResponseEntity<ClaimDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(claimService.saveClaim(claimDto))
    }

    /**
     * This will accept claim-id and finds the matched claim in DB.
     * @param claimId used to identify claim.
     * @return matched claim response.
     */
    @GetMapping("/{claim-id}")
    fun getClaims(@PathVariable("claim-id") claimId: Long): ResponseEntity<ClaimDto> {
        return ResponseEntity.ok(claimService.getClaim(claimId))
    }

    /**
     * This will find all claims in DB.
     * @return list of claim response.
     */
    @GetMapping
    fun getAllClaims(): ResponseEntity<List<ClaimDto>> {
        return ResponseEntity.ok(claimService.getAllClaims())
    }

}