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
        window.location = "/pages/gamesManager/gamesManager.html";
    }
    else
    {
        document.getElementById("userNameError").innerHTML = json.errorMessage;
        //alert(json.errorMessage);
    }
}