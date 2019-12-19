package controller;

import domain.DomainException;
import domain.Person;
import domain.PersonService;
import domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class register extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        List<String> errors = new ArrayList<String>();
        PersonService personService = super.getPersonService();
        Controller.setIsJson(false);
        if(!request.getParameter("password").equals(request.getParameter("password2")))
        {
            errors.add("Passwords do not match");
            return "index.jsp";
        }
        Role role;
        Person person = new Person();
        if("bibliothekaris" == request.getParameter("role"))
        {
            role = Role.BIB;
        }
        else
            {
                role = Role.LID;
            }
        try {
            person.setUserId(request.getParameter("email"));
            person.setFirstName(request.getParameter("surname"));
            person.setLastName(request.getParameter("name"));
            person.setHashedPassword(request.getParameter("password"));
            person.setRole(role);
            person.setAge(request.getParameter("age"));
            person.setGender(request.getParameter("gender"));
        }
        catch (IllegalArgumentException | DomainException e)
        {
            try {
                response.sendRedirect("Controller");
            }
            catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        try
        {
            personService.addPerson(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index.jsp";
    }
}
