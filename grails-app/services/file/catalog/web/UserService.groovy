package file.catalog.web

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestResponse
import org.springframework.http.HttpStatus
import pe.fcg.kth.id1212.filecatalog.web.Response
import pe.fcg.kth.id1212.filecatalog.web.SessionContext
import pe.fcg.kth.id1212.filecatalog.web.WebUtil

@Transactional
class UserService extends ApiService {

    private String resourceUrl = "/users/"

    Response logIn(String username, String password) {
        Response response = new Response()
        RestResponse res = doPost([username: username, password:password], resourceUrl + "${username}/sessions/")
        if(res.statusCode == HttpStatus.OK) {
            response.obj = res.json
        } else {
            response.isSuccessful = false
            response.errorCode = res.json.code
        }
        response
    }

    Response signUp(String username, String password) {
        Response response = new Response()
        RestResponse res = doPost([username: username, password:password], resourceUrl)
        if(res.statusCode != HttpStatus.CREATED) {
            response.isSuccessful = false
            response.errorCode = res.json.code
            if(WebUtil.isValidationError(response.errorCode)) {
                response.obj = res.json.messages.collect { it.toString() }
            }
        }
        response
    }

    Response getNotifications(SessionContext sessionContext) {
        Response response = new Response()
        RestResponse res = doGet(resourceUrl + "${sessionContext.username}/notifications/", sessionContext)
        if(res.statusCode == HttpStatus.OK) {
            List<Notification> notifications = []
            res.json.context.entity.each {
                def notification = new Notification()
                notification.message = it.message
                notification.createdOn = new Date().parse(WebUtil.DATE_FORMAT, it.createdOn)
                notifications.add(notification)
            }
            response.obj = notifications
        } else {
            response.isSuccessful = false
        }
        return response
    }
}
