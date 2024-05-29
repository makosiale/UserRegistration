package daos;

import models.Person;

public interface ValidatorDao {
    Person getPersonByLogin(String login);
    Person getPersonByEmail(String email);
}
