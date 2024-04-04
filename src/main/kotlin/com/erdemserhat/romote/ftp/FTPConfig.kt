package com.erdemserhat.romote.ftp

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream

/**
 * Object responsible for configuring and handling FTP connection.
 */
object FTPConfig {
    private lateinit var FTP_SERVER: String
    private lateinit var FTP_USERNAME: String
    private lateinit var FTP_PASSWORD: String

    /**
     * Initializes the FTP connection with the provided [FTPModel].
     * @param ftpModel The FTP configuration model containing connection parameters.
     */
    fun init(ftpModel: FTPModel) {
        FTP_SERVER = ftpModel.server
        FTP_USERNAME = ftpModel.username
        FTP_PASSWORD = ftpModel.password
    }

    /**
     * Uploads a file to the FTP server.
     * @param file The file to be uploaded.
     * @param ftpDirectory The directory on the FTP server where the file will be uploaded.
     */
    fun uploadFileToFtp(file: File, ftpDirectory: String = "/a") {
        val ftpClient = FTPClient()
        ftpClient.connect(FTP_SERVER)
        ftpClient.login(FTP_USERNAME, FTP_PASSWORD)
        ftpClient.enterLocalPassiveMode()
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE)
        ftpClient.changeWorkingDirectory(ftpDirectory)

        val inputStream = FileInputStream(file)
        ftpClient.storeFile(file.name, inputStream)
        inputStream.close()

        ftpClient.logout()
        ftpClient.disconnect()
    }
}
