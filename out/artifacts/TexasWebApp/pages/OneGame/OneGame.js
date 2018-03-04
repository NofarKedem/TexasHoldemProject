
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
    checkIfClickButton();
    setInterval(checkIfClickButton, 2000);
    //revealCommunityCards(); just for test - need to put it the correct place
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

        }
    )
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

function refreshPlayerInfoCallBack(json) {

    for(elem in json) {

        document.getElementById("nameForPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].PlayerName;
        document.getElementById("statePlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerState;
        document.getElementById("chipsPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerChips;
        document.getElementById("buysPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerBuys;
        document.getElementById("wonPlayer"+(parseInt(elem) + 1)).innerHTML = json[elem].playerHandsWon;
        document.getElementsByClassName("player"+(parseInt(elem) + 1))[0].style.visibility = "visible";
    }
    revealPlayerCards(); //need to re-think one we will have a hands

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


function revealPlayerCards() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'getPlayerCards'
            },
            type: 'GET',
            success: revealPlayerCardsCallBack,
            error:revealPlayerCardsError,

        }
    )
}

function revealPlayerCardsCallBack(json) {
    var playersData = document.getElementsByClassName("player-data");
    for(elem in playersData) {
        if(playersData[elem].querySelector("ul li span:nth-child(2)").innerHTML == json[2]) {
            var player = playersData[elem].querySelector("ul li span:nth-child(2)");
            updatePlayerCards(player, json);
            break;
        }


    }

}

function revealPlayerCardsError(json) {
    console.log("Reveal Cards Error");

}

function updatePlayerCards(player, cards) {

    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[0].src =
            "cardsImgs/" + cards[0] + ".png";
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[0].style.width = 60+"px";
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[1].src =
            "cardsImgs/" + cards[1] + ".png";
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[1].style.width = 60+"px";
}

function revealCommunityCards() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'getComunityCardsAction'
            },
            type: 'GET',
            success: revealComunityCardsCallBack,
            error: revealComunityCardsError,

        }
    )
}

function revealComunityCardsCallBack(json) {
    var communityCarrds = document.getElementsByClassName("card");
    var i = 0;
    for(card in json){
        communityCarrds[i].src = "cardsImgs/" + json[card] + ".png";
        i++;
    }

}

function revealComunityCardsError(json) {
    console.log("Reveal Community Cards Error");
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