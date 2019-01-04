<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Sign Up</title>
    <asset:stylesheet src="application.css"/>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center">Sign Up</h5>
                    <g:render template="/include/alert"/>
                    <g:form action="signUp" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Username" name="username" type="text" value="${params.username}" autofocus required>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password" value="${params.passowrd}" required>
                            </div>
                            <button type="submit" class="btn btn-sm btn-primary btn-block btn-lg">Submit</button>
                        </fieldset>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
<asset:javascript src="application.js"/>
</body>
</html>
