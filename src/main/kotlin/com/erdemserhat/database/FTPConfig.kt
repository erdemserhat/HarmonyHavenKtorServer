package com.erdemserhat.database

import com.erdemserhat.models.FTPModel
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import java.io.File
import java.io.FileInputStream

object FTPConfig {
    private lateinit var FTP_SERVER: String
    private lateinit var FTP_USERNAME: String
    private lateinit var FTP_PASSWORD: String


    fun init(ftpModel: FTPModel) {
        FTP_SERVER = ftpModel.server
        FTP_USERNAME = ftpModel.username
        FTP_PASSWORD = ftpModel.password

    }

    fun uploadFileToFtp(file: File,ftpDirectory: String="/a") {
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