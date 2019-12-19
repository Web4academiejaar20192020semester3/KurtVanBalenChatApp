var socket;
var path = "localhost:8080/VanBalenKurt_ChatApp_Web_exploded/";

initialiseSocket();

function initialiseSocket() {
    socket = new WebSocket("ws://" + path + "blog");
    socket.onmessage = function (event)
    {
        getReaction(event);
    };
    socket.onopen = function (event){}
    socket.onclose = function (event) {}
    socket.onerror = function (event) {}
    var reactionButtons = document.getElementsByClassName("reactionButton");
    for (var i = 0; i < reactionButtons.length; i++)
    {
        reactionButtons[i].topic = reactionButtons[i].parentNode.parentNode.id;
        reactionButtons[i].onclick = function (event){
            sendReaction(event);
        };
    };
}
function sendReaction(event){
    var topic = event.target.topic;
    var sendLeft = {};
    sendLeft["topic"] = topic;
    var idName = topic + "Name";
    var NameTextF = document.getElementById(idName)
    sendLeft["name"] = NameTextF.value;
    NameTextF.value = "";
    var idText = topic + "Text";
    var textF = document.getElementById(idText);
    sendLeft["text"] = textF.value;
    textF.value = "";
    var idRating = topic + "Rating";
    var ratingF = document.getElementById(idRating)
    sendLeft["rating"] = ratingF.value;
    ratingF.value="1";
    var jsonString = JSON.stringify(sendLeft);
    socket.send(jsonString);
}

function getReaction(event) {
    var reponseJSON = event.data;
    var reponseData = JSON.parse(reponseJSON);
    addReactionD(reponseData);
}

function addReactionD(data) {
    var div = document.createElement("div");
    div.className = "commentDiv";
    var pName = document.createElement("p");
    pName.innerHTML = data["name"] + " said:"
    pName.className = "nameP";
    div.appendChild(pName);
    var pText = document.createElement("p");
    pText.innerHTML = data["text"];
    pText.className = "textP";
    div.appendChild(pText);
    var pRating = document.createElement("p");
    pRating.innerHTML = "Rating: " + data["rating"];
    pRating.className = "ratingP"
    div.appendChild(pRating);
    var topicAComments = data["topic"] + "Comments";
    document.getElementById(topicAComments).appendChild(div);
}