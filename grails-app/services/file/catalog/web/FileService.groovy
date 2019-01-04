package file.catalog.web

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestResponse
import org.springframework.http.HttpStatus
import pe.fcg.kth.id1212.filecatalog.web.Response
import pe.fcg.kth.id1212.filecatalog.web.SessionContext
import pe.fcg.kth.id1212.filecatalog.web.WebUtil

@Transactional
class FileService extends ApiService {

    private String resourceUrl = "/files/"

    Response findAll(SessionContext sessionContext) {
        Response response = new Response()
        RestResponse res = doGet(resourceUrl, sessionContext)
        if(res.statusCode == HttpStatus.OK) {
            List<File> files = []
            res.json.context.entity.each {
                File file = new File()
                file.id = it.id
                file.name = it.name
                file.size = it.size
                file.owner = it.user.name
                file.readOnly = it.readOnly
                files.add(file)
            }
            response.obj = files
        } else {
            response.isSuccessful = false
            response.errorCode = res.json.code
        }
        response
    }

    Response upload(File file, SessionContext sessionContext) {
        Response response = new Response()
        RestResponse res = doPost(file, resourceUrl, sessionContext)
        if(res.statusCode != HttpStatus.OK) {
            response.isSuccessful = false
            response.errorCode = res.json.code
            if(WebUtil.isValidationError(response.errorCode)) {
                response.obj = res.json.messages.collect { it.toString() }
            }
        }
        response
    }

    Response download(Long id, SessionContext sessionContext) {
        Response response = new Response()
        RestResponse res = doGet(resourceUrl + id.toString(), sessionContext)
        if(res.statusCode == HttpStatus.OK) {
            File file = new File()
            file.id = res.json.context.entity.id
            file.name = res.json.context.entity.name
            file.size = res.json.context.entity.size
            file.owner = res.json.context.entity.user.name
            file.readOnly = res.json.context.entity.readOnly
            file.content = Base64.getDecoder().decode(res.json.context.entity.content) as byte[]
            response.obj = file
        } else {
            response.isSuccessful = false
            response.errorCode = res.json.code
        }
        response
    }

    Response delete(Long id, SessionContext sessionContext) {
        Response response = new Response()
        RestResponse res = doDelete(resourceUrl + id.toString(), sessionContext)
        if(res.statusCode != HttpStatus.OK) {
            response.isSuccessful = false
            response.errorCode = res.json.code
        }
        response
    }
}
