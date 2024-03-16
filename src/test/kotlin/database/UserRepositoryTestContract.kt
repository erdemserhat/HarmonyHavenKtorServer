package database

import com.erdemserhat.database.userDao.DBUserEntity
import com.erdemserhat.di.DatabaseModule.userRepository
import com.erdemserhat.models.User
import com.erdemserhat.models.rest.client.UserLogin
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class UserRepositoryTestContract {

    //getAllUsers() function tests

    /**
     * This test controls the user list's size keep on the MySQL database
     * You should enter the PHPMyAdminPanel and fill the expected value
     */
    @Test
    fun `test getAllUsers`(): Unit {
        val users = userRepository.getAllUsers()
        println("\u001B[31m----------User List----------\u001B[0m")
        for (user in users) {
            println("\u001B[32m$user.\u001B[0m")
        }
        //assertEquals(2, users.size)

    }


    /**
     * id: 1
     * name: serhat
     * surname: erdem
     * email :me.serhaterdem@gmail.com
     * password :admin
     * gender: Male
     * profilePhotoPath: -
     * You can test this function with the given information
     */
    @Test
    fun `test getUserById`() {
        val user = userRepository.getUserById(1)
        assertEquals("serhat", user?.name)
        assertEquals("erdem", user?.surname)
        assertEquals("me.serhaterdem@gmail.com", user?.email)
        assertEquals("admin", user?.password)
        assertEquals("Male", user?.gender)
        assertEquals("-", user?.profilePhotoPath)
    }

    @Test
    fun `test getUserByLoginInformation`() {
        val loginInformation = UserLogin("me.serhaterdem@gmail.com", "admin")
        val user = userRepository.getUserByLoginInformation(loginInformation)
        assertEquals("serhat", user?.name)
        assertEquals("erdem", user?.surname)
        assertEquals("me.serhaterdem@gmail.com", user?.email)
        assertEquals("admin", user?.password)
        assertEquals("Male", user?.gender)
        assertEquals("-", user?.profilePhotoPath)

    }

    @Test
    fun `test addUser`() {
        val newUser = User(
            id = 0, //No need
            name = "Samet Berkant",
            surname = "KOCA",
            email = "sametberkant@example.com",
            password = "berkant3434",
            gender = "Male",
            profilePhotoPath = "server/pp/samet.jpg"
        )
       assertEquals(1, userRepository.addUser(newUser))

    }

    @Test
    fun `test updateUserById`() {
        val userId = 70;
        val updatedUser = User(
            id = userId,
            name = "A",
            surname = "B",
            email = "C",
            password = "D",
            gender = "E",
            profilePhotoPath = "F"
        )

        val latestUser = userRepository.updateUserById(userId, updatedUser)
        assertEquals(updatedUser, latestUser?.toUser())


    }

    @Test
    fun `test updateUserByLoginInformation`() {
        val userId = 70;
        val updatedUser = User(
            id = userId,
            name = "A",
            surname = "B",
            email = "C",
            password = "D",
            gender = "E",
            profilePhotoPath = "F"
        )

        val userLoginInformation = UserLogin("C", "E")

        val latestUser = userRepository.updateUserByLoginInformation(userLoginInformation, updatedUser)
        assertEquals(updatedUser, latestUser)

    }

    fun DBUserEntity.toUser(): User {
        return User(
            id = this.id,
            name = this.name,
            surname = this.surname,
            email = this.email,
            password = this.password,
            gender = this.gender,
            profilePhotoPath = this.profilePhotoPath
        )
    }
}