package controller;

import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getNewMessages extends  RequestHandler{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Person person = (Person) request.getSession().getAttribute("user");
        Person friend1 = getPersonService().getPersonFullName(request.getParameter("friend"));
        String newMessagesJSON;
        Controller.setIsJson(true);
        newMessagesJSON = person.getNewMessagesFriendJSON(friend1, person);
        if (newMessagesJSON.isEmpty())
        {
            return "";
        }
            return newMessagesJSON;
        }
    }
