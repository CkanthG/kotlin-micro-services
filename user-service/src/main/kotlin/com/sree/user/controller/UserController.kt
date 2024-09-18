package com.sree.user.controller

import com.sree.user.dto.UserDTO
import com.sree.user.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This controller class is accept all user related endpoints and will give response back.
 */
@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    /**
     * This method is used to save the user information.
     * @param userDTO is used to send information to save.
     * @return userDTO the saved user information.
     */
    @PostMapping
    fun createUser(@RequestBody @Valid userDTO: UserDTO): ResponseEntity<UserDTO> {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO))
    }

    /**
     * This method is used to update the user information.
     * @param id is used to identify the user to update.
     * @param userDTO is used to send information to update.
     * @return userDTO the updated user information.
     */
    @PutMapping("/{id}")
    fun updateUser(@RequestBody @Valid userDTO: UserDTO, @PathVariable(name = "id") id: Int): ResponseEntity<UserDTO> {
        return ResponseEntity.ok(userService.updateUser(id, userDTO))
    }

    /**
     * This method is used to fetch all user's information.
     * @return list of user's present in the db.
     */
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        return ResponseEntity.ok(userService.getAllUsers())
    }

    /**
     * This method is used to fetch user information by id.
     * @param id is used to identify the user to fetch.
     * @return matched user information.
     */
    @GetMapping("/{id}")
    fun getUserById(@PathVariable(name = "id") id: Int): ResponseEntity<UserDTO> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    /**
     * This method is used to delete user information by id.
     * @param id is used to identify the user to delete.
     * deletes the matched user information.
     */
    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable(name = "id") id: Int): ResponseEntity<Nothing> {
        userService.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}