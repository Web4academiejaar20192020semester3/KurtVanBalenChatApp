package controller;

import domain.DomainException;
import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class addFriend extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Controller.setIsJson(true);
        Person person = (Person) request.getSession().getAttribute("user");
        String friend = request.getParameter("user");
        try
        {
            person.addFriend(getPersonService().getPerson(friend));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "null";
    }
}
