<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <title>Login</title>
    <asset:stylesheet src="application.css"/>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 offset-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title text-center">Log In</h5>
                    <g:render template="/include/alert"/>
                    <g:form action="logIn" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password" value="">
                            </div>
                            <button type="submit" class="btn btn-sm btn-primary btn-block btn-lg">Submit</button>
                            <div class="form-group">
                                <a href="<g:createLink action="signUp"/>">Sign Up</a>
                            </div>
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
