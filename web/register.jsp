<form method="POST" action="Controller?action=register">
    <label for="surname" >Surname: </label><input id="surname" name="surname" type="text" value="Kurt"><br>
    <label for="name" >Name: </label><input id="name" name="name" type="text" value="Van Balen"><br>
    <label for="emailRegister" >Email: </label><input id="emailRegister" name="email" type="text" value="kurt@ucll.be"><br>
    <label for="age" >Age: </label><input id="age" name="age" type="number" value="18"><br>
    <label for="role">Role: </label>
    <select id="role" name="role">
        <option value="lid">Lid</option>
        <option value="bibliothekaris">Bibliothekaris</option>
    </select><br>
    <label for="gender">Role: </label>
    <select id="gender" name="gender">
        <option value="m">Man</option>
        <option value="w">Woman</option>
    </select><br>
    <label for="passwordRegister">password: </label><input id="passwordRegister" name="password" type="password" value="t"><br>
    <label for="passwordRegister2">password: </label><input id="passwordRegister2" name="password2" type="password" value="t"><br>
    <input type="submit" value="Register!">
</form>