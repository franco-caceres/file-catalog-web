package file.catalog.web

import org.springframework.web.client.ResourceAccessException

class BaseController {
    def handleConnectionException(ResourceAccessException e) {
        session.invalidate()
        switch(e.cause.class) {
            case ConnectException.class:
                flash.errormessage = "Your request was not processed because the service is down. No changes were made. Please try again later"
                break
            case SocketException.class:
                flash.errormessage = "Connection to the main service was lost while awaiting a response. Changes may or may not have been made. Please check back later"
                break
            default:
                flash.errormessage = "Unknown connection error. Changes may or may not have been made. Please check back later"
        }
        render view: "/user/login"
    }
}
