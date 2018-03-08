
var checkNumOfUser = 0;
var betValue;
var raiseValuse;
var stopReadyInterval;
var endHand;
var stopNewHandInterval;
var stopDisplayMoveInterval;
var stopifQuitClickedInterval;
var stopsparkBorderInterval;
var stopButChipsInterval;

window.onload = function()
{
    disableActionMove(true);
    refreshUserList();
    checkIfStartGame();
    refreshGameDetails();
    setInterval(refreshUserList, 2000);
    setInterval(refreshGameDetails, 2000);
    checkNumOfUser = setInterval(checkIfStartGame, 2000);
    ifQuitClicked();
    stopifQuitClickedInterval = setInterval(ifQuitClicked, 2000);
    setInterval(refreshNewHand, 2000);
};

function disableActionMove(bool)
{
    disableMoveButton(bool);
    disableChipAReadyButton(bool);
}
function disableChipAReadyButton(bool)
{
    document.getElementsByClassName("action-button")[6].disabled = bool;
    document.getElementsByClassName("action-button")[7].disabled = bool;
}
function disableQuitButton(bool)
{
    document.getElementsByClassName("action-button")[5].disabled = bool;
}
function disableMoveButton(bool)
{
    document.getElementsByClassName("action-button")[0].disabled = bool;
    document.getElementsByClassName("action-button")[1].disabled = bool;
    document.getElementsByClassName("action-button")[2].disabled = bool;
    document.getElementsByClassName("action-button")[3].disabled = bool;
    document.getElementsByClassName("action-button")[4].disabled = bool;
}
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
        if(user.isMyTurn === true)
        {
            td[0].style.fontWeight = "900"
            td[0].style.color = "red";
        }


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
    document.getElementsByClassName("currentBet")[0].innerHTML = json.currentBet;
    document.getElementsByClassName("totalCashBox")[0].innerHTML = json.cashBox;

}


function checkIfStartGame(){
   $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'ifStartGame'
            },
            type: 'GET',
            success: checkIfStartGameCallBack,
        }
    )
}

function checkIfStartGameCallBack(json)
{
    if(json === true)
    {
        alert("The game start");
        disableQuitButton(true);
        clearInterval(checkNumOfUser);
        refreshPlayerInfo();
        setInterval(refreshPlayerInfo, 2000);
        displayMoveButtonAccordingToMyTurn();
        stopDisplayMoveInterval = setInterval(displayMoveButtonAccordingToMyTurn, 2000);
        checkIfClickButton();
        setInterval(checkIfClickButton, 2000);
        checkIfHandEnd;
        endHand = setInterval(checkIfHandEnd, 2000);
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
        if(json[elem].isQuit == false) {
            document.getElementById("nameForPlayer" + (parseInt(elem) + 1)).innerHTML = json[elem].PlayerName;
            document.getElementById("typePlayer" + (parseInt(elem) + 1)).innerHTML = json[elem].typeOfPlayer;
            document.getElementById("statePlayer" + (parseInt(elem) + 1)).innerHTML = json[elem].playerState;
            document.getElementById("chipsPlayer" + (parseInt(elem) + 1)).innerHTML = json[elem].playerChips;
            document.getElementById("buysPlayer" + (parseInt(elem) + 1)).innerHTML = json[elem].playerBuys;
            document.getElementById("wonPlayer" + (parseInt(elem) + 1)).innerHTML = json[elem].playerHandsWon;
            document.getElementsByClassName("player" + (parseInt(elem) + 1))[0].style.visibility = "visible";
        }
        else
        {
            document.getElementsByClassName("player" + (parseInt(elem) + 1))[0].style.visibility = "hidden";
        }
    }
    revealPlayerCards(); //need to re-think one we will have a hands

}
function refreshPlayerInfoError() {
    console.log("Error: refreshPlayerInfo");
}

function displayMoveButtonAccordingToMyTurn() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'displayMoveButtonAccordingToMyTurn'
            },
            type: 'GET',
            success: displayMoveButtonAccordingToMyToMyTurnCallBack,

        }
    )
}

function displayMoveButtonAccordingToMyToMyTurnCallBack(json) {
    document.getElementsByClassName("action-button")[0].disabled = !json.fold;
    document.getElementsByClassName("action-button")[1].disabled = !json.calll;
    document.getElementsByClassName("action-button")[2].disabled = !json.check;
    document.getElementsByClassName("action-button")[3].disabled = !json.bet;
    document.getElementsByClassName("action-button")[4].disabled = !json.raise;
    if(json.enoughChips === false)
    {
        document.getElementById("inputError").innerHTML = "Player does'nt have enough chips,therefor he quit."
    }
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
if(json != false) {
    var playersData = document.getElementsByClassName("player-data");
    for (elem in playersData) {
        if (playersData[elem].querySelector("ul li span:nth-child(2)").innerHTML == json[2]) {
            var player = playersData[elem].querySelector("ul li span:nth-child(2)");
            updatePlayerCards(player, json);
            break;
        }
    }
}
}

function revealPlayerCardsError(json) {
    console.log("Reveal Cards Error");

}

function revealAllPlayersCards() {
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'getAllPlayersCards'
            },
            type: 'GET',
            success: revealAllPlayersCardsCallBack,
            error:revealPlayerCardsError,

        }
    )
}

function revealAllPlayersCardsCallBack(json) {
    var playersData = document.getElementsByClassName("player-data");
    for(var i = 0; i < playersData.length; i++) {
        for(var j = 0; j < json.length; j+=3) {
            if(playersData[i].querySelector("ul li span:nth-child(2)").innerHTML == json[j]) {
                var player = playersData[i].querySelector("ul li span:nth-child(2)");
                revealCards(player, json[j+1], json[j+2]);
            }
        }
    }
}

function revealCards(player, first, second) {
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[0].src =
        "cardsImgs/" + first + ".png";
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[0].style.width = 60+"px";
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[1].src =
        "cardsImgs/" + second + ".png";
    player.parentNode.parentNode.parentNode.parentNode.getElementsByClassName("player-cards")[0].children[1].style.width = 60+"px";
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
    document.getElementsByClassName("action-button")[3].onclick = betClicked;
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
        betValue = document.getElementById("betInput").value;
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
        raiseValuse = document.getElementById("raiseInput").value;
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
        document.getElementById("inputError").innerHTML = json.error;
    }
    else
    {
        document.getElementById("inputError").innerHTML = "";
    }
    /*
    else if(json.isRoundEnd === true)
    {
        //commuinty cards per json.numRound
    }
    */
}

function checkIfHandEnd()
{
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'checkIfHandEnd',
            },
            type: 'GET',
            success: checkIfHandEndCallBack,

        }
    )
}
function checkIfHandEndCallBack(json)
{
    if(json.isAllHandsEnd === true)
    {
        alert("All the hand was end, goodbye!");
        window.location = buildUrlWithContextPath("pages/gamesManager/gamesManager.html");
    }
    else if(json.isHandEnd === true)
    {
        //pop-up to winners
        document.getElementsByClassName("name")[0].innerHTML = "The Winner/s : ";
        document.getElementsByClassName("cardComb")[0].innerHTML = "Winning Details : ";
        document.getElementsByClassName("prize")[0].innerHTML = "Winner/s Prize :";
        for(var i = 0; i < json.winnersDetails.length; i++) {
            if(json.winnersDetails[i].cardCombination.indexOf("Technical victory") > - 1) {
                // if(json.winnersDetails[i].cardCombination.indexOf("All the Human players") > - 1){
                //     // Display All Names
                //     document.getElementsByClassName("name")[0].innerHTML +=  "<br>" + json.winnersDetails[i].name;
                //     // Display Card Comb All Players
                //     document.getElementsByClassName("cardComb")[0].innerHTML +=  "<br>" + json.winnersDetails[i].cardCombination;
                //     // Display Prize
                //     if(i == 0) {
                //         document.getElementsByClassName("prize")[0].innerHTML +=  "<br>" + json.winnersDetails[i].prize;
                //     }
                // }
               // else {
                    // For loop to Display All Names
                    json.winnersDetails.forEach(function (item) {
                        document.getElementsByClassName("name")[0].innerHTML += "<br>" + item.name;
                    });
                    // Display Winning details
                    document.getElementsByClassName("cardComb")[0].innerHTML += json.winnersDetails[i].cardCombination;
                    // Display Prize
                    document.getElementsByClassName("prize")[0].innerHTML += json.winnersDetails[i].prize;
                    i = json.winnersDetails.length;
               // }
            } else {
                // Display All Names
                document.getElementsByClassName("name")[0].innerHTML +=  "<br>" + json.winnersDetails[i].name;
                // Display Card Comb All Players
                document.getElementsByClassName("cardComb")[0].innerHTML +=  "<br>" + json.winnersDetails[i].cardCombination;
                // Display Prize
                if(i == 0) {
                    document.getElementsByClassName("prize")[0].innerHTML +=  "<br>" + json.winnersDetails[i].prize;
                }
            }
        }
        revealAllPlayersCards();
        $( "#dialog" ).dialog();
        alert("end hand");
        clearInterval(endHand);
        clearInterval(stopDisplayMoveInterval);
        disableMoveButton(true);
        disableChipAReadyButton(false);
        disableQuitButton(false);
        stopReadyInterval = setInterval(ifReadyButtonCliked, 2000);
        stopButChipsInterval = setInterval(buyChipsCliked, 2000);
        stopNewHandInterval = setInterval(ifNewHand, 2000);
        if(json.isPlayerHasEnoughChips === false) {
            document.getElementsByClassName("action-button")[7].disabled = true;
        }

    }
    else{
        revealCommunityCards();
    }
}

function ifReadyButtonCliked()
{
    document.getElementsByClassName("action-button")[7].onclick = readyClicked;
}
function readyClicked(event)
{
    if(event != null) {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'readyClicked',
                },
                type: 'GET',
                success: readyClickedCallBack,

            }
        )
    }
}

function readyClickedCallBack(json)
{
    disableChipAReadyButton(true);
    disableQuitButton(true);
    clearInterval(stopReadyInterval);
    clearInterval(stopButChipsInterval);
}

function refreshNewHand()
{
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'refreshNewHand',
            },
            type: 'GET',
            success: refreshNewHandCallBack,

        }
    )
}

function refreshNewHandCallBack(json)
{
    if(json) {
        window.location.href = window.location.href;
    }
}

function ifNewHand()
{
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'ifNewHand',
            },
            type: 'GET',
            success: ifNewHandCallBack,

        }
    )
}
function ifNewHandCallBack(json)
{
    /*
    if(json.isAllHandsEnd === true)
    {
        alert("All the hand was end, goodbye!");
        window.location = buildUrlWithContextPath("pages/gamesManager/gamesManager.html");
    }*/
    if(json.isPlayerHasEnoughChips === true)
        document.getElementsByClassName("action-button")[7].disabled = false;
    if(json.isEnoughPlayer === false)
    {
        alert("Not enough player to continue, goodbey!");
        window.location = buildUrlWithContextPath("pages/gamesManager/gamesManager.html");
    }
    else if(json.isNewHand === true)
    {
        clearInterval(stopNewHandInterval);
        alert("New Hand Start");
        displayMoveButtonAccordingToMyTurn();
        stopDisplayMoveInterval = setInterval(displayMoveButtonAccordingToMyTurn, 2000);
        checkIfHandEnd;
        endHand = setInterval(checkIfHandEnd, 2000);
    }
}
function ifQuitClicked()
{
    document.getElementsByClassName("action-button")[5].onclick = quitClicked;
}

function quitClicked()
{
    $.ajax
    (
        {
            url: 'OneGameDetails',
            data: {
                action: 'quitClicked',
            },
            type: 'GET',
            success: quitClickedReturnActiveCallBack,

        }
    )
}
function quitClickedReturnActiveCallBack(json)
{
    clearInterval(stopifQuitClickedInterval);
    window.location = buildUrlWithContextPath("pages/gamesManager/gamesManager.html");
}
function wrapBuildingURLWithContextPath() {
    var contextPath = calculateContextPath();
    return function(resource) {
        return "/" + contextPath + "/" + resource;
    };
}

// call the wrapper method and expose a final method to be used to build complete resource names (buildUrlWithContextPath)
var buildUrlWithContextPath = wrapBuildingURLWithContextPath();

function calculateContextPath() {
    var pathWithoutLeadingSlash = window.location.pathname.substring(1);
    var contextPathEndIndex = pathWithoutLeadingSlash.indexOf('/');
    return pathWithoutLeadingSlash.substr(0, contextPathEndIndex)
}

function buyChipsCliked()
{
    document.getElementsByClassName("action-button")[6].onclick = ChipsCliked;
}
function ChipsCliked(event) {
    if(event != null)
    {
        $.ajax
        (
            {
                url: 'OneGameDetails',
                data: {
                    action: 'ChipsCliked',
                },
                type: 'GET',
                success: ChipsClikedCallBack,

            }
        )
    }
}
function ChipsClikedCallBack(json) {
    alert("Your purchase was successful");
}
