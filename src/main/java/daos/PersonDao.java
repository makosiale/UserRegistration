package daos;

import models.Person;

import java.util.Queue;

public interface PersonDao {
    Queue<Person> getPeople();
    Person getPerson(Person person);
    void addPerson(Person person);
    void updatePerson(Person person1, Person person2);

    void deletePerson(Person person);
}
