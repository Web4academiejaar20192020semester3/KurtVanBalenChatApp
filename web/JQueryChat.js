$(document).ready(function () {namesClickable();
    setTimeout(function () {setInterval(function () {pollNewMessages();}, 3000);}, 4000);
    var user = document.getElementById("username").innerHTML;
    /*setTimeout(function () {setInterval(function () {getTotalFriends(user);}, 3000);}, 4000);*/
});


function namesClickable() {
    $(".fullName").off("click");
    $(".fullName").on("click", function (){openChatWindow(this.innerHTML);});
}

function openChatWindow(nameFriend) {
    if (!ChatWindowAvailable(nameFriend)) {
        var ChatWindow = document.createElement("div");
        ChatWindow.className = "ChatWindow";
        ChatWindow.setAttribute("id", nameFriend + "Chat");
        var navibar = document.createElement("div");
        navibar.className = "ChatWindowNavBar";
        ChatWindow.appendChild(navibar);
        var friendNameF = document.createElement("span");
        friendNameF.innerHTML = nameFriend;
        navibar.appendChild(friendNameF);
        var closeButton = document.createElement("div");
        closeButton.className = "closeButton";
        closeButton.innerHTML = "X";
        closeWindowButton(closeButton);
        navibar.appendChild(closeButton);
        var messagesF = document.createElement("div");
        messagesF.className = "messagesF";
        ChatWindow.appendChild(messagesF);
        var inputDiv = document.createElement("div");
        inputDiv.className = "inputDiv";
        ChatWindow.appendChild(inputDiv);
        var inputField = document.createElement("textarea");
        inputField.className = "inputField";
        inputDiv.appendChild(inputField);
        var sendButton = document.createElement("button");
        sendButton.className = "sendButton";
        sendButton.innerHTML = "Send!";
        sendButton.addEventListener("click", function () {
            sendMessage(sendButton, nameFriend);
        });
        inputDiv.appendChild(sendButton);
        $("#chatWindowContainer").append(ChatWindow);
        getOldMessages(nameFriend);
    }
}

function sendMessage(sendButton, nameFriend) {
    var textF = sendButton.parentNode.getElementsByClassName("inputField")[0];
    var messageText = textF.value;
    textF.value = "";
    var messageObject = new Object;
    messageObject.message = messageText;
    messageObject.receiver = nameFriend;
    var messageObjectJSON = JSON.stringify(messageObject);
    console.log(messageObjectJSON);
    $.ajax({
        url: "Controller?action=sendMessage",
        type: "POST",
        data:{
            'message': messageText,
            'nameFriend': nameFriend
        }/*data: "m=" + messageObjectJSON*/,
        dataType: "json"
    });
}
/*function getTotalFriends(user) {
    $.ajax({
        url: "Controller?action=getTotalFriends&user=" + user + "" , type: "GET",
        success: function (json) {console.log(json);updateFriendsList(json)}
    });
}
function updateFriendsList(json)
{
    console.log(json);
    var jsonObject = JSON.parse(json);
    var friendsListJQueryBody = document.getElementById("friendsListJQueryBody");
    friendsListJQueryBody.deleteRow(-1);
    var row = friendsListJQueryBody.insertRow();
    var col1 = row.insertCell(0);
    var col2 = row.insertCell(1);
    col1.innerHTML = jsonObject.online;
    col2.innerHTML = jsonObject.offline;
}*/
function getOldMessages(name) {

    $.ajax({
        url: "Controller?action=getOldMessages&friend=" + name,type: "GET",
        success: function (json) {console.log(json);receiveMessage(json, name);}
    });
}

function receiveMessage(json, nameFriend) {
    var messages = $.parseJSON(json);
    for (var i = 0; i < messages.Message.length; i++) {
        addWindowMessage(messages.Message[i], nameFriend);
    }

}
function receiveNewMessages(json, nameFriend) {
    var tempJsonMessageObj = JSON.parse(json);
    if ((tempJsonMessageObj.Message.length != 0)) {
        if (tempJsonMessageObj.Message[0]["sender"] === nameFriend || tempJsonMessageObj.Message[0]["receiver"] === nameFriend) {
            openChatWindow(nameFriend);
            receiveMessage(json, nameFriend);
        }
    }
}

function ChatWindowAvailable(name) {
    var ChatWindows = document.getElementsByClassName("ChatWindow");
    var fullName = name + "Chat";
    for (var i = 0; i < ChatWindows.length; i++) {
        if (ChatWindows[i].getAttribute("id") === fullName) {
            return true;
        }
    }
    return false;
}


function closeWindowButton(closeButton) {

    closeButton.addEventListener("click", function () {closeChat(closeButton); });
}

function closeChat(closeButton) {
    var parentParent = closeButton.parentNode.parentNode;
    parentParent.parentNode.removeChild(parentParent);

}
function pollNewMessage(nameFriend) {

    $.ajax({
        url: "Controller?action=getNewMessages&friend=" + nameFriend,type: "GET",
        success: function (json) {
            receiveNewMessages(json, nameFriend);
        }
    });
}
function addWindowMessage(messageObject, nameFriend) {

    var message = messageObject["message"];
    var receiver = messageObject["receiver"];
    var sender = messageObject["sender"];
    var messageSpace = document.getElementById(nameFriend + "Chat").getElementsByClassName("messagesF")[0];
    var messageDiv = document.createElement("div");
    if (sender === nameFriend) {
        messageDiv.className = "messageFromFriend";
    } else {
        messageDiv.className = "messageFromMe";
    }
    messageDiv.className += " messageDiv";
    messageDiv.innerHTML = sender + ": " + message;
    messageSpace.appendChild(messageDiv);
    $(messageSpace).animate({scrollTop: messageSpace.scrollHeight}, 600);
}

function pollNewMessages() {
    var friendsListNames = document.getElementById("friendsBody").getElementsByClassName("fullName");
    for (var i = 0; i < friendsListNames.length; i++) {
        var nameFriend = friendsListNames[i].innerHTML;
        pollNewMessage(nameFriend);
    }
}