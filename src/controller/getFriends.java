package controller;

import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getFriends extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Person person = (Person) request.getSession().getAttribute("user");

        Controller.setIsJson(true);
        if(person.getFriends().size() > 0)
        {
            return getPersonService().getFriendsJSON(person.getUserId());
        }
        else {
            return "{\"Person\":[]}";
        }
    }
}
