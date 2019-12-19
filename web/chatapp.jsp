<p>
    <span id="currentStatus"></span> -
    <input type="text" id="statusText"></input>
    <input type="button" value="Update!" id="updateStatusButton"/>
</p>
<input id="addFriendUser" type="text" placeholder="Email"></input>
<input type="button" value="Send friendrequest!" id="addFriendButton" />
<h2>Friendlist</h2>
<table id="totalFriendsTable">
    <thead id="totalFriendsHead">
    <tr>
        <th>Online</th>
        <th>Offline</th>
    </tr>
    </thead>
    <tbody id="totalFriendsBody">
    </tbody>
</table>

<table id="friendsTable">
    <thead id="friendsHead">
    <tr>
        <th>Friends</th>
        <th>Status</th>
    </tr>
    </thead>

    <tbody id="friendsBody">
    </tbody>
</table>
<div id="chatWindowContainer"></div>


<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="JQueryChat.js"></script>
<script src="chat.js"></script>