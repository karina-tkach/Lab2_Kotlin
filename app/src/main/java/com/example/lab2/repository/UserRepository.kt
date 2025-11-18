package com.example.lab2.repository

import com.example.lab2.model.User

class UserRepository {
    fun getUser(): User {
        return User(
            name = "Jane Doe",
            email = "jane.doe@example.com",
            photoUrl = "https://www.mcso.us/sites/default/files/styles/portrait_tall_xs/public/2024-02/cold%20case%20john%20doe.png.webp?itok=FaIf6NJ5"
        )
    }
}
