package file.catalog.web

import grails.converters.JSON
import org.springframework.http.HttpMethod
import pe.fcg.kth.id1212.filecatalog.web.SessionContext
import pe.fcg.kth.id1212.filecatalog.web.WebUtil

class UserController extends BaseController {
    def userService

    def logIn() {
        if(request.method == HttpMethod.GET.name()) {
            if(WebUtil.getSessionContext(session) != null) {
                redirect controller: "file"
            } else {
                render view: "logIn"
            }
        } else {
            def res = userService.logIn(params.username, params.password)
            if(res.isSuccessful) {
                SessionContext sessionContext = new SessionContext()
                sessionContext.username = params.username
                sessionContext.authHeader = res.obj.context.headers.Authorization[0]
                WebUtil.setSessionContext(session, sessionContext)
                redirect controller: "file"
            } else {
                flash.errormessage = WebUtil.getStringForErrorCode(res.errorCode)
                redirect action: "logIn"
            }
        }
    }

    def signUp() {
        if(request.method == HttpMethod.GET.name()) {
            render view: "signUp"
        } else {
            def res = userService.signUp(params.username, params.password)
            if(res.isSuccessful) {
                flash.successmessage = "You can now log in"
                redirect action: "logIn"
            } else {
                flash.errormessage = WebUtil.getStringForErrorCode(res.errorCode)
                if(WebUtil.isValidationError(res.errorCode)) {
                    flash.errormessage = res.obj
                }
                redirect action: "signUp", params: params
            }
        }
    }

    def logOut() {
        session.invalidate()
        redirect action: "logIn"
    }

    def getNotifications() {
        if(WebUtil.getSessionContext(session) != null) {
            def res = userService.getNotifications(WebUtil.getSessionContext(session))
            if(res.isSuccessful) {
                response.status = 200
                def notifications = res.obj as List<Notification>
                def m = [notifications: notifications.collect(mapNotification)]
                render m as JSON
            } else {
                response.status = 500
                render [:] as JSON
            }
        }
    }

    private def mapNotification = { Notification x ->
        def mapping = [:]
        mapping.message = x.message
        mapping.date = x.createdOn.format(WebUtil.DATE_FORMAT)
        mapping
    }
}
