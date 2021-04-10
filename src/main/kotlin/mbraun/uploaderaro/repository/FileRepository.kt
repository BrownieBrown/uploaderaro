package mbraun.uploaderaro.repository

import mbraun.uploaderaro.model.File
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository: JpaRepository<File, String>  {
}