
var checkNumOfUser = 0;
var betValue;
var raiseValuse;
window.onload = function()
{
    refreshUserList();
    checkNumOfUserToStartGame();
    refreshGameDetails();
    setInterval(refreshUserList, 2000);
    setInterval(refreshGameDetails, 2000);
    checkNumOfUser = setInterval(checkNumOfUserToStartGame, 2000);
};

function refreshUserList() {
    $.ajax(
        {
            url: 'OneGame',
            data: {
                action: "gameUsers"
            },
            type: 'GET',
            success: refreshGameUserListCallback,
            error:  function() {console.log("No Game Users list Available")}
        }
    );
}

function refreshGameUserListCallback(json) {
    var gameUsersTable = $('.onlineGamePlayerTable tbody');
    gameUsersTable.empty();
    var userListFromGame = json.users;

    userListFromGame.forEach(function (user) {

        var tr = $(document.createElement('tr'));

        var td = $(document.createElement('td')).text(user.name);

        td.appendTo(tr);

        tr.appendTo(gameUsersTable);

    });
}

function refreshGameDetails() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'GameDetails'
            },
            type: 'GET',
            success: refreshGameDetailsCallback,
            error:onError
        }
    )
}
function onError() {
    alert("ff");
}
function refreshGameDetailsCallback(json) {
    document.getElementsByClassName("handNumber")[0].innerHTML = json.currHand;
    document.getElementsByClassName("roundNumber")[0].innerHTML = json.currRound;
    document.getElementsByClassName("bigBlind")[0].innerHTML = json.numOfChipsForBig;
    document.getElementsByClassName("smallBlind")[0].innerHTML = json.numOfChipsForsmall;

}


function checkNumOfUserToStartGame(){
   $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'ifThereAreEnoughUser'
            },
            type: 'GET',
            success: checkNumOfUserToStartGameCallBack,
        }
    )
}

function checkNumOfUserToStartGameCallBack(json)
{
    if(json === true)
    {
        alert("The game start");
        clearInterval(checkNumOfUser);
        refreshPlayerInfo();
        setInterval(refreshPlayerInfo, 2000);
    }
}
function refreshPlayerInfo() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'StartGame'
            },
            type: 'GET',

            success: refreshPlayerInfoCallBack,
            error:refreshPlayerInfoError,
        }
    )

}


function StartGameCallBack(json) {
  //  $("player1:hidden").show();
    document.getElementById("nameForPlayer1").innerHTML = json[0].PlayerName;
    document.getElementById("statePlayer1").innerHTML = json[0].playerState;
    document.getElementById("chipsPlayer1").innerHTML = json[0].playerChips;
    document.getElementById("buysPlayer1").innerHTML = json[0].playerBuys;
    document.getElementById("wonPlayer1").innerHTML = json[0].playerHandsWon;

}


function refreshPlayerInfoCallBack(json) {
  //  $("player1:hidden").show();

    for(elem in json) {

        document.getElementById("nameForPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].PlayerName;
        document.getElementById("statePlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerState;
        document.getElementById("chipsPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerChips;
        document.getElementById("buysPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerBuys;
        document.getElementById("wonPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerHandsWon;
        document.getElementsByClassName("player"+(parseInt(elem) + 1))[0].style.visibility = "visible";
    }


}
function checkIfMyTurn() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'checkIfMyTurn'
            },
            type: 'GET',
            success: checkIfMyTurnCallBack,

        }
    )
}
function checkIfMyTurnCallBack(json) {
    document.getElementsByClassName("action-button")[0].disabled = json.fold;
    document.getElementsByClassName("action-button")[1].disabled = !json.calll;
    document.getElementsByClassName("action-button")[2].disabled = !json.check;
    document.getElementsByClassName("action-button")[3].disabled = !json.bet;
    document.getElementsByClassName("action-button")[4].disabled = !json.raise;

}

function refreshPlayerInfoError() {
    console.log("Error: refreshPlayerInfo");
}

function checkIfClickButton()
{
    document.getElementsByClassName("action-button")[0].onclick = foldClicked;
    document.getElementsByClassName("action-button")[1].onclick = callClicked;
    document.getElementsByClassName("action-button")[2].onclick = checkClicked;
    betValue = document.getElementById("betInput").value;
    document.getElementsByClassName("action-button")[3].onclick = betClicked;
    raiseValuse = document.getElementById("raiseInput").value;
    document.getElementsByClassName("action-button")[4].onclick = raiseClicked;

}

function foldClicked(event)
{
    if(event != null) {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'buttonActionClicked',
                    move: '1',
                    amount: 0
                },
                type: 'GET',
                success: statusCallBack,

            }
        )
    }
}

function callClicked(event)
{
    if(event != null) {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'buttonActionClicked',
                    move: '3',
                    amount: 0
                },
                type: 'GET',
                success: statusCallBack,

            }
        )
    }
}

function checkClicked(event)
{
    if(event != null) {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'buttonActionClicked',
                    move: '4',
                    amount: 0
                },
                type: 'GET',
                success: statusCallBack,

            }
        )
    }
}

function betClicked(event)
{
    if(event != null) {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'buttonActionClicked',
                    move: '2',
                    amount:betValue

                },
                type: 'GET',
                success: statusCallBack,

            }
        )
    }
}

function raiseClicked(event)
{
    if(event != null) {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'buttonActionClicked',
                    move: '5',
                    amount:raiseValuse
                },
                type: 'GET',
                success: statusCallBack,

            }
        )
    }
}
function statusCallBack(json) {
    if(json.isValidAmount === false)
    {
        document.getElementById("inputError").value = json.error;
    }
}