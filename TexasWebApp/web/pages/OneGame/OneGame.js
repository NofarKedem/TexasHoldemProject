var stopInterval;

window.onload = function()
{
    hidePlayers();
    refreshUserList();
    setInterval(refreshUserList, 2000);
    checkNumOfUserToStartGame();
    stopInterval = setInterval(function(){ checkNumOfUserToStartGame() }, 2000);
    startGame();
    refreshGameDetails();
    setInterval(refreshGameDetails, 2000);
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
        clearInterval(stopInterval);
        //startGame();

    }
}
function startGame() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'StartGame'
            },
            type: 'GET',
            success: StartGameCallBack,

        }
    )

}
function hidePlayers() {
   // $("player1:hidden");
}

function StartGameCallBack(json) {
  //  $("player1:hidden").show();
    document.getElementById("nameForPlayer1").innerHTML = json[0].PlayerName;
    document.getElementById("statePlayer1").innerHTML = json[0].playerState;
    document.getElementById("chipsPlayer1").innerHTML = json[0].playerChips;
    document.getElementById("buysPlayer1").innerHTML = json[0].playerBuys;
    document.getElementById("wonPlayer1").innerHTML = json[0].playerHandsWon;

}

function checkIfMyTurn()
{
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
function checkIfMyTurnCallBack(json)
{
    document.getElementsByClassName("action-button")[0].disabled = !json.fold;
    document.getElementsByClassName("action-button")[1].disabled = !json.calll;
    document.getElementsByClassName("action-button")[2].disabled = !json.check;
    document.getElementsByClassName("action-button")[3].disabled = !json.bet;
    document.getElementsByClassName("action-button")[4].disabled = !json.raise;

}