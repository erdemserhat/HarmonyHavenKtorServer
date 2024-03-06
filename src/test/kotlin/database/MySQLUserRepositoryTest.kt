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
    private val userRepository = userDao

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
        assertEquals("Samet Berkant", user?.name)
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
        val loginInformation = UserLogin(
            "sametberkant@example.com",
            "berkant3434"
        )
        userRepository.addUser(newUser)
        val addedUser =userRepository.getUserByLoginInformation(loginInformation)
        assertEquals("Samet Berkant", newUser.name)
        assertEquals("KOCA", newUser.surname)
        assertEquals("sametberkant@example.com", newUser.email)
        assertEquals("berkant3434", newUser.password)
        assertEquals("Male", newUser.gender)
        assertEquals("server/pp/samet.jpg", newUser.profilePhotoPath)

    }

    @Test
    fun `test updateUserById`(){
        val userId = 70;
        val updatedUser = User(
            id=userId,
            name="A",
            surname = "B",
            email = "C",
            password = "D",
            gender = "E",
            profilePhotoPath = "F"
        )

        val latestUser = userDao.updateUserById(userId,updatedUser)
        assertEquals(updatedUser,latestUser?.toUser())



    }

    @Test
    fun `test updateUserByLoginInformation`(){
        val newUser = User(
            id = 0, //No need
            name = "Ahmet",
            surname = "Suat Can",
            email = "ahmet@example.com",
            password = "ahmet2323",
            gender = "Male",
            profilePhotoPath = "server/pp/ahmet.jpg"
        )

        val loginInformation = UserLogin(
            "ahmet@example.com",
            "ahmet2323"
        )

        userRepository.addUser(newUser)
        val addedUser = userRepository.getUserByLoginInformation(loginInformation)
        val updatedUser = addedUser?.copy(
            name="Yavuz Samet",
            surname = "Kan",
            email = "yavuz@example.com",
            password = "yavuz3434",
            gender = "Male",
            profilePhotoPath = "server/pp/yavuz.png",
        )
        if (addedUser != null) {
            if (updatedUser != null) {
                userRepository.updateUserByLoginInformation(loginInformation,updatedUser)
            }
        }

        val updatedUserControl = addedUser?.let { userRepository.getUserById(it.id) }
        assertEquals(updatedUser,updatedUserControl)

    }

}

fun  DBUserEntity.toUser():User{
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