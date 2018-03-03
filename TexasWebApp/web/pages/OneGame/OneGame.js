var checkNumOfUser = 0;

window.onload = function()
{
    hidePlayers();
    refreshUserList();
    checkNumOfUserToStartGame();
    refreshPlayerInfo();
    refreshGameDetails();
    setInterval(refreshUserList, 2000);
    setInterval(refreshGameDetails, 2000);
    setInterval(refreshPlayerInfo, 2000);
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
function hidePlayers() {
   // $("player1:hidden");
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

function refreshPlayerInfoError() {
    console.log("Error: refreshPlayerInfo");
}