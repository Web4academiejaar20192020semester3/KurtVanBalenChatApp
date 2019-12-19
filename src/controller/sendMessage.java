package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import domain.Message;
import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class sendMessage extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Controller.setIsJson(true);
        Person person = (Person) request.getSession().getAttribute("user");
        String message = request.getParameter("message");
        String friend = request.getParameter("nameFriend");
        Person receiver = getPersonService().getPersonFullName(friend);
        Message m = new Message(message, person, receiver);
        person.addNewMessage(m);
        receiver.addNewMessage(m);

        return "";
    }
}
