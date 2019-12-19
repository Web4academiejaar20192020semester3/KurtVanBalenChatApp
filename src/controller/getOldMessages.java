package controller;

import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getOldMessages extends RequestHandler{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Controller.setIsJson(true);
        Person person = (Person) request.getSession().getAttribute("user");
        Person friend1 = getPersonService().getPersonFullName(request.getParameter("friend"));
        String json = person.getOldMessagesFriendAsJSON(friend1);
        if(json.isEmpty())
        {
            return "";
        }
        return json;
    }
}
