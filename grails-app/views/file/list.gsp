<%@ page import="pe.fcg.kth.id1212.filecatalog.web.WebUtil" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>File Catalog</title>
    <asset:stylesheet src="application.css"/>
</head>
<body>
<div id="wrapper">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <header style="text-align: center; margin-bottom: 50px;">
                <h1>File Catalog</h1>
                <g:form controller="user" action="logOut" method="post" style="display: block;">
                    <div class="row">
                        <div class="col-md-12">
                            <a href="javascript:void(0)" onclick="$(this).closest('form').submit()" class="float-right">Log Out (${WebUtil.getSessionContext(session).username})</a>
                        </div>
                    </div>
                </g:form>
                <hr/>
                <g:render template="/include/alert"/>
            </header>
            <main>
                <div class="row" style="margin-bottom: 20px;">
                    <div class="col-md-12">
                        <button style="margin-bottom: 42px;" type="button" class="btn btn-secondary float-right" data-toggle="modal" data-target="#fileUploadModal">
                            Upload
                        </button>
                    </div>
                    <div class="col-md-12">
                        <table class="table">
                            <thead class="thead-light">
                            <tr>
                                <th width="30%" class="text-center">Name</th>
                                <th width="15%" class="text-center">Size</th>
                                <th width="25%" class="text-center">Owner</th>
                                <th width="15%" class="text-center">Read Only</th>
                                <th width="15%"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${files}" var="file">
                                <tr>
                                    <td width="30%" class="text-left"><a href="<g:createLink action="download" id="${file.id}"/>">${file.name}</a></td>
                                    <td width="20%" class="text-center">${file.size}B</td>
                                    <td width="30%" class="text-center">${file.owner}</td>
                                    <td width="20%" class="text-center">${file.readOnly ? "Yes" : "No"}</td>
                                    <td>
                                        <g:form action="delete" id="${file.id}" method="post">
                                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                        </g:form>
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="card" style="height: 200px; overflow-y: scroll;">
                            <div class="card-body">
                                <h5 class="card-title">Notifications</h5>
                                <ul class="list-group list-group-flush" id="notification-list">

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="fileUploadModal" class="modal fade" tabindex="-1" role="dialog">
                    <div class="modal-dialog modal-dialog-centered" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Upload</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <g:form action="upload" name="fileUploadForm" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="file">File</label>
                                        <input type="file" class="form-control-file" id="file" name="file">
                                    </div>
                                    <div class="form-check">
                                        <label class="form-check-label">
                                            <g:checkBox name="readOnly" class="form-check-input"/>
                                            Read only
                                        </label>
                                    </div>
                                </g:form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" onclick="$('#fileUploadForm').submit()">Submit</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
</div>
<asset:javascript src="application.js"/>
<g:javascript>
    $(document).ready(function() {
       setInterval(function() {
        $.ajax({
            type: 'GET',
            url: '<g:createLink controller="user" action="getNotifications"/>',
            data: '',
            processData : false,
            contentType : false
        }).done(function(data) {
            for(var i = data.notifications.length - 1; i >= 0; i--) {
                var value = data.notifications[i];
                var html = "<li class='list-group-item'>" + value.date + ": " + value.message + "</li>";
                $("#notification-list").prepend(html);
            }
        });
       }, 2000);
    });
</g:javascript>
</body>
</html>