package mbraun.uploaderaro.controller

import mbraun.uploaderaro.message.ResponseFile
import mbraun.uploaderaro.message.ResponseMessage
import mbraun.uploaderaro.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Controller
@CrossOrigin("http://localhost:8081")
class FileController(@Autowired val fileService: FileService) {

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseMessage> {
        var message: String

        return try {
            fileService.store(file)
            message = "Uploaded the file successfully: ${file.originalFilename}"
            ResponseEntity.status(HttpStatus.OK).body(ResponseMessage(message))
        } catch (e: Exception) {
            message = "Could not upload the file: ${file.originalFilename}!"
            ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseMessage(message))
        }
    }

    @GetMapping("/files")
    fun getListFiles(): ResponseEntity<List<ResponseFile>> {
        val files: List<ResponseFile> = fileService.getAllFiles().map { dbFile ->
            val fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(dbFile.getId())
                .toUriString()

            ResponseFile(
                dbFile.getName(),
                fileDownloadUri,
                dbFile.getType(),
                dbFile.getData().size.toLong()
            )
        }.toList()

        return ResponseEntity.status(HttpStatus.OK).body(files)
    }

    @GetMapping("/files/{id}")
    fun getFile(@PathVariable id: String): ResponseEntity<ByteArray> {
        val fileDB = fileService.getFile(id)

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${fileDB.getName()} ")
            .body(fileDB.getData())
    }

    @DeleteMapping("/files/{id}")
    fun deleteFile(@PathVariable("id") id: String) {
        fileService.deleteById(id)
        // TODO: 10.04.21 Response Entity done right 
    }
}