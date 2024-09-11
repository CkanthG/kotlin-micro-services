package com.sree.claims.controller

import com.sree.claims.dto.ClaimDto
import com.sree.claims.service.ClaimService
import jakarta.persistence.Id
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/claims")
class ClaimController(
    val claimService: ClaimService
) {

    @PostMapping
    fun saveClaim(@RequestBody claimDto: ClaimDto): ResponseEntity<ClaimDto> {
        return ResponseEntity.status(HttpStatus.CREATED).body(claimService.saveClaim(claimDto))
    }

    @GetMapping("/{claim-id}")
    fun getClaims(@PathVariable("claim-id") claimId: Long): ResponseEntity<ClaimDto> {
        return ResponseEntity.ok(claimService.getClaim(claimId))
    }

}