package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class Users extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("id") != null) {
            Person user = getPersonService().getPerson(request.getParameter("id"));
            ArrayList<Person> array = new ArrayList<>();
            array.add(user);
            return toJSON(array);
        }
        return toJSON((ArrayList<Person>)getPersonService().getPersons());
    }

    private String toJSON(ArrayList<Person> array) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try
        {
            json = mapper.writeValueAsString(array);
            return json;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
