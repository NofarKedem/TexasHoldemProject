window.onload = function()
{
    refreshUserList();
    setInterval(refreshUserList, 2000);
    checkNumOfUserToStartGame();
    setInterval(checkNumOfUserToStartGame, 2000);


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

function checkNumOfUserToStartGame(){
    GameController gameController = new GameController();
    if(gameController.getnumOfSubscribers()== gameController.getnumOfPlayers)
    {
        startGame();
        clearInterval();
    }
}