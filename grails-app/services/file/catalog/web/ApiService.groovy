package file.catalog.web

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import org.springframework.beans.factory.annotation.Value
import pe.fcg.kth.id1212.filecatalog.web.SessionContext

@Transactional
class ApiService {
    @Value('${fileCatalog.apiUrl}')
    protected String apiUrl

    RestResponse doGet(String resourceUrl, SessionContext sessionContext) {
        def rest = new RestBuilder()
        rest.get(apiUrl + resourceUrl) {
            auth(sessionContext.authHeader)
        }
    }

    RestResponse doPost(Object obj, String resourceUrl) {
        def rest = new RestBuilder()
        rest.post(apiUrl + resourceUrl) {
            json(obj)
        }
    }

    RestResponse doPost(Object obj, String resourceUrl, SessionContext sessionContext) {
        def rest = new RestBuilder()
        rest.post(apiUrl + resourceUrl) {
            auth(sessionContext.authHeader)
            json(obj)
        }
    }

    RestResponse doDelete(String resourceUrl, SessionContext sessionContext) {
        def rest = new RestBuilder()
        rest.delete(apiUrl + resourceUrl) {
            auth(sessionContext.authHeader)
        }
    }
}
