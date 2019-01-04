package file.catalog.web

import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import pe.fcg.kth.id1212.filecatalog.web.Response
import pe.fcg.kth.id1212.filecatalog.web.WebUtil
import pe.fcg.kth.id1212.filecatalog.web.cmd.FileUploadCmd

import javax.servlet.http.HttpServletResponse

class FileController extends BaseController {

    static defaultAction = "list"

    def fileService

    def index() { }

    def list() {
        Response res = fileService.findAll(WebUtil.getSessionContext(session))
        List<File> files = []
        if(res.isSuccessful) {
            files = res.obj as List<File>
        } else {
            flash.errormessage = WebUtil.getStringForErrorCode(res.errorCode)
        }
        [files: files]
    }

    def upload(FileUploadCmd cmd) {
        File file = new File()
        file.name = cmd.file.originalFilename
        file.size = cmd.file.size
        file.content = cmd.file.bytes
        file.readOnly = cmd.readOnly ?: false
        Response res = fileService.upload(file, WebUtil.getSessionContext(session))
        if(res.isSuccessful) {
            flash.successmessage = "File saved successfully"
        } else {
            flash.errormessage = WebUtil.getStringForErrorCode(res.errorCode)
            if(WebUtil.isValidationError(res.errorCode)) {
                flash.errormessage = res.obj
            }
        }
        redirect action: "list"
    }

    def download(long id) {
        Response res = fileService.download(id, WebUtil.getSessionContext(session))
        if(res.isSuccessful) {
            File file = res.obj as File
            response.setHeader("Content-Type", "application/octet-stream")
            response.setHeader("Content-Disposition", "attachment;filename=" + file.name)
            response.contentLength = file.size
            response.outputStream.write(file.content)
            response.outputStream.flush()
            response.outputStream.close()
            response.status = HttpServletResponse.SC_OK
            GrailsWebRequest webRequest = (GrailsWebRequest) RequestContextHolder.currentRequestAttributes()
            webRequest.renderView = false
        } else {
            flash.errormessage = WebUtil.getStringForErrorCode(res.errorCode)
            redirect action: "list"
        }
    }

    def delete(long id) {
        Response res = fileService.delete(id, WebUtil.getSessionContext(session))
        if(res.isSuccessful) {
            flash.successmessage = "File deleted successfully"
        } else {
            flash.errormessage = WebUtil.getStringForErrorCode(res.errorCode)
        }
        redirect action: "list"
    }
}
