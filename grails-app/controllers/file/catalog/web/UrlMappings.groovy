package file.catalog.web

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "file")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
