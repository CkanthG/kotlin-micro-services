package com.sree.claims.repository

import com.sree.claims.entity.Claim
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Interact with Claim Entity to do DB operations.
 */
interface ClaimRepository : JpaRepository<Claim, Long>  {
}