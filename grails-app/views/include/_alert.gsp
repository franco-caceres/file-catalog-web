<g:if test="${flash.message != null}">
    <g:if test="${flash.message instanceof List}">
        <div class="alert alert-info" role="alert">
            <ul>
                <g:each in="${flash.message}" var="msg">
                    <li>${msg}</li>
                </g:each>
            </ul>
        </div>
    </g:if>
    <g:else>
        <div class="alert alert-info" role="alert">
            ${flash.message}
        </div>
    </g:else>
</g:if>
<g:if test="${flash.successmessage != null}">
    <g:if test="${flash.successmessage instanceof List}">
        <div class="alert alert-success" role="alert">
            <ul>
                <g:each in="${flash.successmessage}" var="msg">
                    <li>${msg}</li>
                </g:each>
            </ul>
        </div>
    </g:if>
    <g:else>
        <div class="alert alert-success" role="alert">
            ${flash.successmessage}
        </div>
    </g:else>
</g:if>
<g:if test="${flash.errormessage != null}">
    <g:if test="${flash.errormessage instanceof List}">
        <div class="alert alert-danger" role="alert">
            <ul style="margin-bottom: 0">
                <g:each in="${flash.errormessage}" var="msg">
                    <li>${msg}</li>
                </g:each>
            </ul>
        </div>
    </g:if>
    <g:else>
        <div class="alert alert-danger" role="alert">
            ${flash.errormessage}
        </div>
    </g:else>
</g:if>