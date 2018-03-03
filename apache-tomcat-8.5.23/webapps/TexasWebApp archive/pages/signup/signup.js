window.onload = function()
{
    checkLoginStatus();
    setInterval(checkLoginStatus, 2000);
};


function checkLoginStatus() {
    $.ajax
    ({
        url: 'login',
        data: {
            action: "status"
        },
        type: 'GET',
        success: statusCallback
    });
}

function statusCallback(json)
{
    if (json.isConnected && json.gameNumber != -1)
    {
        window.location = "GameRoom.html";
    }
    else if (json.isConnected)
    {
        window.location = "LobbyPage.html";
    }
}

function loginClick()
{
    event.preventDefault();

    var userName = $('#UserNameInput').val();
    if(userName == "") {
        document.getElementById("userNameError").innerHTML = "User Name must contain at least one character";
    } else {
        var computerFlag = $('#computer').is(':checked');

        $.ajax
        ({
            url: 'login',
            data:
                {
                    action: "login",
                    userName: userName,
                    userType: computerFlag
                },
            type: 'GET',
            success: loginCallback
        });
    }

}

function loginCallback(json)
{
    if (json.isConnected)
    {
        //window.location = "pages/gamesManager/gamesManager.html";
        window.location = buildUrlWithContextPath("pages/gamesManager/gamesManager.html");
    }
    else
    {
        document.getElementById("userNameError").innerHTML = json.errorMessage;
        //alert(json.errorMessage);
    }
}


// extract the context path using the window.location data items
function calculateContextPath() {
    var pathWithoutLeadingSlash = window.location.pathname.substring(1);
    var contextPathEndIndex = pathWithoutLeadingSlash.indexOf('/');
    return pathWithoutLeadingSlash.substr(0, contextPathEndIndex)
}

// returns a function that holds within her closure the context path.
// the returned function is one that accepts a resource to fetch,
// and returns a new resource with the context path at its prefix
function wrapBuildingURLWithContextPath() {
    var contextPath = calculateContextPath();
    return function(resource) {
        return "/" + contextPath + "/" + resource;
    };
}

// call the wrapper method and expose a final method to be used to build complete resource names (buildUrlWithContextPath)
var buildUrlWithContextPath = wrapBuildingURLWithContextPath();