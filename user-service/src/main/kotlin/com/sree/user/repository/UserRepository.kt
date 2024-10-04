package com.sree.user.repository

import com.sree.user.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

/**
 * This interface is used to do operations on users collection.
 */
interface UserRepository: MongoRepository<User, Int> {
    /**
     * This method is used to find out the latest record on db.
     */
    fun findTopByOrderByIdDesc(): User?

    fun findByUsername(username: String): Optional<User>
}