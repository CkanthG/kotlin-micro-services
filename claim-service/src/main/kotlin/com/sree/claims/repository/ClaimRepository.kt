package com.sree.claims.repository

import com.sree.claims.entity.Claim
import org.springframework.data.jpa.repository.JpaRepository

interface ClaimRepository : JpaRepository<Claim, Long>  {
}