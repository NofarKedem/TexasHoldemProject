window.onload = function()
{
    //refreshUserList();
    //setInterval(refreshUserList, 2000);
    //checkNumOfUserToStartGame();
   // setInterval(checkNumOfUserToStartGame, 2000);
    refreshGameDetails();
    setInterval(refreshGameDetails, 2000);
};

function refreshUserList() {
    $.ajax(
        {
            url: 'userManager',
            data: {
                action: "gameUsers"
            },
            type: 'GET',
            success: refreshUserListCallback
        }
    );
}

function refreshUserListCallback(json) {
    var usersTable = $('.onlinePlayerTable');
    usersTable.empty();
    var userList = json.users;

    userList.forEach(function (user) {

        var tr = $(document.createElement('tr'));

        var td = $(document.createElement('td')).text(user.name);

        td.appendTo(tr);

        tr.appendTo(usersTable);

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

/*
function checkNumOfUserToStartGame(){
    GameController gameController = new GameController();
    if(gameController.getnumOfSubscribers()== gameController.getnumOfPlayers)
    {
        startGame();
        clearInterval();
    }
}
*/
