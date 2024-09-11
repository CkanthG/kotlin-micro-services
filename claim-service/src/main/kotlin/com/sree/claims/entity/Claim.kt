package com.sree.claims.entity

import jakarta.persistence.*

@Entity
@Table(name = "claims")
class Claim(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val claimId: Long?,
    val claimName: String? = null,
    val market: String? = null,
    val subMarket: Int? = null,
    val claimNumber: String? = null,
    val claimTotalRequested: Long? = null,
    val claimTotalPaid: Long? = null,
    val claimTotalInitial: Long? = null
)