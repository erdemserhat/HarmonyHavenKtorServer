package com.erdemserhat.service.validation

import com.erdemserhat.service.di.DatabaseModule
import com.erdemserhat.models.UserInformationSchema
import java.util.*

/**
 * Provides validation services for user registration data.
 * Validates user information such as name, surname, email, and password.
 * @property user The user registration data to be validated.
 */
class UserInformationValidatorService(
    private val user: UserInformationSchema,
) {
    /**
     * Validates the user registration form.
     * @return A ValidationResult indicating whether the validation is successful or not,
     * along with an error message if validation fails.
     * @param byCheckingExistenceOfUser -> based on the use case you can check use existence status in system,
     * for example if you validate user's updatedment information schema then you must give this parameter false value...
     *
     *
     */
    fun validateForm(
        byCheckingExistenceOfUser: Boolean = true,
        shouldOnlyValidatePassword:Boolean = false
    ): ValidationResult {

        if (shouldOnlyValidatePassword){
            return validatePassword(shouldOnlyValidatePassword = true)

        }

        // Validate name
        if (user.name.length < 2)
            return ValidationResult(
                isValid = false,
                errorMessage = "Name is too short. It must be at least 2 characters long.",
                errorCode = 201
            )

        if (!user.name.matches(Regex("^[a-zA-Z\\s]+$")))
            return ValidationResult(
                isValid = false,
                errorMessage = "Name must contain only letters.",
                errorCode = 202

            )

        // Validate surname
        if (user.surname.length < 2)
            return ValidationResult(
                isValid = false,
                errorMessage = "Surname is too short. It must be at least 2 characters long.",
                errorCode = 203
            )

        if (!user.surname.matches(Regex("^[a-zA-Z\\s]+$")))
            return ValidationResult(
                isValid = false,
                errorMessage = "Surname must contain only letters.",
                errorCode = 204

            )

        // Validate email format
        if (!isValidEmailFormat(user.email))
            return ValidationResult(
                isValid = false,
                errorMessage = "Invalid email format.",
                errorCode = 205


            )


        if (byCheckingExistenceOfUser)
        // Check if the email is already registered
            if (DatabaseModule.userRepository.controlUserExistenceByEmail(user.email))
                return ValidationResult(
                    isValid = false,
                    errorMessage = "There is already a registered user with this email.",
                    errorCode = 206

                )

        return validatePassword()
    }

    /**
     * Validates the password.
     * @return A ValidationResult indicating whether the password meets the criteria or not,
     * along with an error message if validation fails.
     */
    private fun validatePassword(shouldOnlyValidatePassword:Boolean = false): ValidationResult {
        // Validate password length
        if (user.password.length < 8)
            return ValidationResult(
                isValid = false,
                errorMessage = "Password must be at least 8 characters long.",
                errorCode = 207

            )

        // Validate password strength
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}")
        if (!passwordRegex.matches(user.password))
            return ValidationResult(
                isValid = false,
                errorMessage = "Password must contain at least one uppercase letter, one lowercase letter, and one digit.",
                errorCode = 208
            )

        if(shouldOnlyValidatePassword)
            return ValidationResult()



        // Check if password contains name, surname, or email
        val lowerCaseName = user.name.lowercase(Locale.getDefault())
        val lowerCaseSurname = user.surname.lowercase(Locale.getDefault())
        val lowerCaseEmail = user.email.lowercase(Locale.getDefault())

        if (user.password.contains(lowerCaseName))
            return ValidationResult(
                isValid = false,
                errorMessage = "Password cannot contain the username. Please choose a different password.",
                errorCode = 209

            )

        if (user.password.contains(lowerCaseSurname))
            return ValidationResult(
                isValid = false,
                errorMessage = "Password cannot contain the surname. Please choose a different password.",
                errorCode = 210


            )

        if (user.password.contains(lowerCaseEmail))
            return ValidationResult(
                isValid = false,
                errorMessage = "Password cannot contain the email address. Please choose a different password.",
                errorCode = 211
            )

        return ValidationResult(true)
    }
}


