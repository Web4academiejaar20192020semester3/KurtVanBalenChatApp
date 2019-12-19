var addFriendRequest = new XMLHttpRequest();
var getFriendsRequest = new XMLHttpRequest();
var statusUpdateRequest = new XMLHttpRequest();
var statusRequest = new XMLHttpRequest();

var body = document.getElementsByTagName("body")[0];
initialise();

function initialise() {
    getFriendsTotal();
    getFriends();
    var addFriendButton = document.getElementById("addFriendButton");
    addFriendButton.onclick = addFriend;
    var changeStatusButton = document.getElementById("updateStatusButton");
    changeStatusButton.onclick = updateStatus;
    getStatus();
}

function getFriendsData() {
    if (addFriendRequest.readyState === 4) {
        if (addFriendRequest.status === 200) {
            var response = JSON.parse(addFriendRequest.responseText);
            if (response.hasOwnProperty("Error")) {
                document.getElementById("friendError").innerHTML = response["Error"];
                return;
            }

            document.getElementById("friendError").innerHTML = "";
            var friendsListBody = document.getElementById("friendsBody");
            var rows = friendsListBody.rows.length;
            for (var i = 0; i < rows; i++) {
                friendListBody.deleteRow(0);
            }
            for (var j = 0; j < response.Person.length; j++) {
                var row = friendListBody.insertRow();
                var col1 = row.insertCell(0);
                var col2 = row.insertCell(1);
                col1.className = "fullName";
                col2.className = "status";
                col1.innerHTML = response.Person[j]["name"];
                col2.innerHTML = response.Person[j]["status"];
                namesClickable();
            }

        }
    }
}

function getFriendsTotal()
{
    var statusses = document.getElementsByClassName("status");
    var online = 0;
    var offline = 0;

    for (var i = 0; i < statusses.length; i++)
    {
        var par = statusses[i].innerHTML
        if( par == "Offline")
        {
            offline = offline +1;
        }
        else
        {
            online = online + 1;
        }
    }
    var totalFriendsListBody = document.getElementById("totalFriendsBody");
    totalFriendsListBody.deleteRow(-1);
    var row = totalFriendsListBody.insertRow();
    var col1 = row.insertCell(0);
    var col2 = row.insertCell(1);
    col1.innerHTML = online;
    col2.innerHTML = offline;
    setTimeout("getFriendsTotal()",5000);

}


function getFriends() {
    getFriendsRequest.open("GET", "Controller?action=getFriends", true);
    getFriendsRequest.send();
    getFriendsRequest.onreadystatechange = getFriendsTimeout;
}

function getFriendsTimeout() {
    if (getFriendsRequest.readyState === 4) {
        if (getFriendsRequest.status === 200) {
            var response = JSON.parse(getFriendsRequest.responseText);
            var friendsListBody = document.getElementById("friendsBody");
            var rows = friendsListBody.rows.length;
            for (var i = 0; i < rows; i++) {
                friendsListBody.deleteRow(0);
            }

            for (var j = 0; j < response.Person.length; j++) {
                var row = friendsListBody.insertRow();
                var col1 = row.insertCell(0);
                var col2 = row.insertCell(1);
                col1.className = "fullName";
                col2.className = "status";
                col1.innerHTML = response.Person[j]["name"];
                col2.innerHTML = response.Person[j]["status"];
                namesClickable();
            }
        }
        setTimeout("getFriends()", 5000);
    }
}
function addFriend()
{
    var friendNameF = document.getElementById("addFriendUser");
    var friendNameValue = friendNameF.value;
    friendNameF.value = "";
    var info = "user=" + encodeURIComponent(friendNameValue);
    addFriendRequest.open("POST", "Controller?action=addFriend", true);
    addFriendRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    addFriendRequest.send(info);
    addFriendRequest.onreadystatechange = getFriendsData;
}

function updateStatus() {
    var statusF = document.getElementById("statusText");
    var statusText = statusF.value;
    statusF.value = "";
    var info = "newStatus=" + encodeURIComponent(statusText);
    statusUpdateRequest.open("POST", "Controller?action=updateStatus", true);
    statusUpdateRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    statusUpdateRequest.send(info);
    statusUpdateRequest.onreadystatechange = getStatusDataGeenTimeout;
}


function getStatus() {
    statusRequest.open("GET", "Controller?action=getStatus", true);
    statusRequest.onreadystatechange = getStatusData;
    statusRequest.send(null);
}

function getStatusData() {
    if (statusRequest.readyState === 4) {
        if (statusRequest.status === 200) {
            var response = statusRequest.responseText;
            var statusF = document.getElementById("currentStatus");
            statusF.innerHTML = response;
            setTimeout("getStatus()", 3000);
        }
    }
}