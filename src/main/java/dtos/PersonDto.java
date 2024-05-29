package dtos;

import connection.DatabaseConnection;
import daos.PersonDao;
import models.Person;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Queue;

public class PersonDto implements PersonDao {

    private DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
    private Connection connection = databaseConnection.getConnection();

    @Override
    public Queue<Person> getPeople() {
        Queue<Person> people = new ArrayDeque<>();
        String request = "select * from users";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                Person person = new Person();
                person.setLogin(resultSet.getString("login"));
                person.setEmail(resultSet.getString("email"));
                person.setPassword(resultSet.getString("password"));
                people.offer(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    @Override
    public Person getPerson(Person person) {
        return null;
    }

    @Override
    public void addPerson(Person person) {
        String request = "insert into users(login,email,password) values('" + person.getLogin() + "','" +
                person.getEmail() + "','" + person.getPassword() + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePerson(Person person1, Person person2) {
        String request = "update users set login ='" + person2.getLogin() + "', email='" + person2.getEmail() +
                "',password ='" + person2.getPassword() + "' where login ='" + person1.getLogin() + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerson(Person person) {
        String request = "delete from users where login ='" + person.getLogin() + "'";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(request);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
