package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Users extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Controller.setIsJson(true);
        if (request.getParameter("id") != null) {
            Person user = getPersonService().getPerson(request.getParameter("id"));
            ArrayList<Person> array = new ArrayList<>();
            array.add(user);
            return toJSON(array);
        }
        return toJSON((ArrayList<Person>)getPersonService().getPersons());
    }

    private String toJSON(ArrayList<Person> array) {
        StringBuffer stringBuffer = new StringBuffer();
        List<Person> users = array;
        Iterator iterator = users.iterator();
        stringBuffer.append("{ \"Person\":[");
        while (iterator.hasNext()) {
            Person user = (Person) iterator.next();

            stringBuffer.append("{");
            stringBuffer.append("\"name\": \"" + user.getName() + "\",");
            stringBuffer.append("\"status\": \"" + user.getStatus() + "\"," );
            stringBuffer.append("\"id\": \"" + user.getUserId() + "\"" );
            stringBuffer.append("}");
            if (iterator.hasNext()){
                stringBuffer.append(",");
            }
        }
        stringBuffer.append("]");
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
