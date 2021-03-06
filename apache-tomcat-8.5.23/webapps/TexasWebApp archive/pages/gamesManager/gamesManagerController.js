window.onload = function()
{
    refreshUserList();
    setInterval(refreshUserList, 2000);
    setParams();
    checkUpload();
    setInterval(refreshGamesList, 2000);

};

function getUserName() {
    var result;
    $.ajax
    ({
        async: false,
        url: 'userManager',
        data: {
            action: "getUser"
        },
        type: 'GET',
        success: function (json) {
            result = json.name;
        }
    });
    return result;
}

function setParams() {
    var creatorName = getUserName();
    document.getElementById("fileuploads").setAttribute("action", "upload?action=loadGame&creator=" + creatorName + "");
}


function checkUpload() {
    var url_string = window.location.href;
    var url = new URL( window.location.href);
    if (url_string.indexOf('?') != -1) {
        var isLoaded = url.searchParams.get("isLoaded");
        var errors = url.searchParams.get("error");
        if (isLoaded === "true") {
            alert("Load game Success !!");
            refreshGamesList();
            //clearFileInput();
            window.location = "gamesManager.html";
        }
        else {
            //clearFileInput();
            alert(errors);
            window.location = "gamesManager.html";
        }
    }
}

function refreshUserList() {
    $.ajax(
        {
            url: 'userManager',
            data: {
                action: "users"
            },
            type: 'GET',
            success: refreshUserListCallback
        }
    );
}

function refreshUserListCallback(json) {
    var usersTable = $('.usersTable tbody');
    usersTable.empty();
    var userList = json.users;

    userList.forEach(function (user) {

        var tr = $(document.createElement('tr'));

        var td = $(document.createElement('td')).text(user.name);

        td.appendTo(tr);

        tr.appendTo(usersTable);

    });
}

    function refreshGamesList() {
        $.ajax
        (
            {
                url: 'upload',
                data: {
                    action: 'gameList'
                },
                type: 'GET',
                success: refreshGamesListCallback,
                error:  function() {console.log("No Game List Available")}
            }
        )
    }

    function refreshGamesListCallback(json) {

        var gamesTable = document.querySelector('.gamesTable tbody');
        $(gamesTable).empty();
        var gamesList = json.games;

        gamesList.forEach(function (game) {
            var tr = document.createElement('tr');
            tr.classList.add('game');
            tr.classList.add('enabled');
            var tdGameName = document.createElement('td');
            tdGameName.appendChild(document.createTextNode(game.gameName));
            var tdCreatorName = document.createElement('td');
            tdCreatorName.appendChild(document.createTextNode(game.nameOfUserOwner));
            var tdNumberOfHands = document.createElement('td');
            tdNumberOfHands.appendChild(document.createTextNode(game.numOfHands));
            var tdBuy = document.createElement('td');
            tdBuy.appendChild(document.createTextNode(game.buy));
            var tdNumberOfPlayers = document.createElement('td');
            tdNumberOfPlayers.appendChild(document.createTextNode(game.numOfPlayers));
            var tdNumberOfSubscribers = document.createElement('td');
            tdNumberOfSubscribers.appendChild(document.createTextNode(game.numOfSubscribers));

            var tdSmallBlind = document.createElement('td');
            tdSmallBlind.appendChild(document.createTextNode(game.SmallBlind));
            var tdBigBlind = document.createElement('td');
            tdBigBlind.appendChild(document.createTextNode(game.BigBlind));
            var tdFixBlind = document.createElement('td');
            if(game.fixedBlind === "true"){tdFixBlind.appendChild(document.createTextNode("Fixed Blinds"));}
            else{tdFixBlind.appendChild(document.createTextNode("Not Fixed Blinds"));}

            var tdGameStatus = document.createElement('td');
            if(game.isActive === true) {
                tdGameStatus.appendChild(document.createTextNode("Active"));
            }
            else {
                tdGameStatus.appendChild(document.createTextNode("Not Active"));
            }
            var tdJoinGame = document.createElement('td');
            var tdJoinGamebtn = document.createElement('button');
            tdJoinGamebtn.classList.add('btn');
            tdJoinGamebtn.appendChild(document.createTextNode("Get Started"));
            tdJoinGame.appendChild(tdJoinGamebtn);

            tr.appendChild(tdGameName);
            tr.appendChild(tdCreatorName);
            tr.appendChild(tdNumberOfHands);
            tr.appendChild(tdBuy);
            tr.appendChild(tdNumberOfPlayers);
            tr.appendChild(tdNumberOfSubscribers);
            tr.appendChild(tdSmallBlind);
            tr.appendChild(tdBigBlind);
            tr.appendChild(tdFixBlind);
            tr.appendChild(tdGameStatus);
            tr.appendChild(tdJoinGame);

            gamesTable.appendChild(tr);

            if(game.isActive === true) {
                if(tr.classList.contains('enabled')) {
                    tr.classList.add('disabled');
                    tr.classList.remove('enabled');
                    tr.lastElementChild.firstChild.disabled = true;
                }
            } else {
                if(tr.classList.contains('disabled')) {
                    tr.classList.remove('disabled');
                    tr.classList.add('enabled');
                    tr.lastElementChild.firstChild.disabled = false;
                }
            }
        });

        var tr = $('.tableBody tr.enabled .btn');
        for (var i = 0; i < tr.length; i++) {
            tr[i].onclick = createGameDialog;
        }
    }

function createGameDialog(event) {
    if (event != null) {
        var name = event.currentTarget.parentElement.parentElement.childNodes["0"].childNodes["0"].data;
        $.ajax(
            {
                url: 'getStarted',
                data: {
                    action: "createGameDialog",
                    nameGame: name
                },
                type: 'GET',
                success: joinGameClickedCallback,
                error: error

            }
        );
    }
}

function error(){
    console.log("Fail to Join Game!!!!")
}

function joinGameClickedCallback(json) {
    //window.location = "/pages/OneGame/OneGame.html";
    if(json===true)
        window.location = buildUrlWithContextPath("/pages/OneGame/OneGame.html");
    else
        alert("This user already in this game");

}

function onLogoutClick() {
    $.ajax(
        {
            url: '../../pages/signup/login',
            data: {
                action: "logout"
            },
            type: 'GET',
            success: logoutCallback
        }
    );
}

function logoutCallback(json) {
    didUserCloseWindow = false;
    //window.location = "/";
    window.location = buildUrlWithContextPath("/");

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