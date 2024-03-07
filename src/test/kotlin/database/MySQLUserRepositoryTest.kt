package database

import com.example.database.DBUserEntity
import com.example.di.DatabaseModule.userDao
import com.example.models.User
import com.example.models.UserLogin
import junit.framework.TestCase.assertEquals
import org.hamcrest.MatcherAssert
import org.junit.Assert.assertThat
import kotlin.test.Test

class MySQLUserRepositoryTest {

    //getAllUsers() function tests

    /**
     * This test controls the user list's size keep on the MySQL database
     * You should enter the PHPMyAdminPanel and fill the expected value
     */
    @Test
    fun `test getAllUsers`(): Unit {
        val users = userDao.getAllUsers()
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
        val user = userDao.getUserById(1)
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
        val user = userDao.getUserByLoginInformation(loginInformation)
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
       assertEquals(1, userDao.addUser(newUser))

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

        val latestUser = userDao.updateUserById(userId, updatedUser)
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

        val latestUser = userDao.updateUserByLoginInformation(userLoginInformation, updatedUser)
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