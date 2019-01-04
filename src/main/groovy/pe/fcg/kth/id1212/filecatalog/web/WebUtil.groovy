package pe.fcg.kth.id1212.filecatalog.web

import javax.servlet.http.HttpSession

class WebUtil {
    private static final String SESSION_CONTEXT = "SESSION_CONTEXT"

    static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    static SessionContext getSessionContext(HttpSession session) {
        return session.getAttribute(SESSION_CONTEXT) as SessionContext
    }

    static void setSessionContext(HttpSession session, SessionContext sessionContext) {
        session.setAttribute(SESSION_CONTEXT, sessionContext)
    }

    static String getStringForErrorCode(String errorCode) {
        switch(errorCode) {
            case "UNAUTHORIZED":
                return "You are not allowed to perform such action"
            case "USER_NOT_OWNER":
                return "Only the owner of the file can perform such action because it is read-only"
            case "USER_NAME_TAKEN":
                return "The provided username is already taken"
            case "USER_NAME_DOES_NOT_EXIST":
                return "The provided username does not exist"
            case "INCORRECT_PASSWORD":
                return "The provided password is incorrect"
            case "NOT_FOUND":
                return "The specified element does not exist or has been deleted"
            default:
                return "An unexpected error occurred"
        }
    }

    static boolean isValidationError(String errorCode) {
        return "VALIDATION" == errorCode
    }
}