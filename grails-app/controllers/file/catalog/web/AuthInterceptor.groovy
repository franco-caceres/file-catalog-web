package file.catalog.web

import pe.fcg.kth.id1212.filecatalog.web.WebUtil


class AuthInterceptor {

    int order = HIGHEST_PRECEDENCE + 100

    AuthInterceptor() {
        matchAll()
                .excludes(controller: "user", action: "logIn")
                .excludes(controller: "user", action: "signUp")
    }

    boolean before() {
        if(WebUtil.getSessionContext(session) == null) {
            flash.message = "Please log in to continue"
            redirect controller:"user", action:"logIn"
            return false
        }
        true }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
