package controller;

import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class updateStatus extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Person person = (Person) request.getSession().getAttribute("user");
        Controller.setIsJson(true);

        if (request.getParameter("newStatus") != null)
        {
            getPersonService().getPerson(person.getUserId()).setStatus(request.getParameter("newStatus"));
        }
        return "null";
    }
}
