package pe.fcg.kth.id1212.filecatalog.web.cmd

import grails.validation.Validateable
import org.springframework.web.multipart.MultipartFile

class FileUploadCmd implements Validateable {
    MultipartFile file
    Boolean readOnly
}
