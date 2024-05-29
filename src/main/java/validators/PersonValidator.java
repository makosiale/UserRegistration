package validators;

import dtos.ValidatorDto;
import interfaces.Validator;
import models.Person;

public class PersonValidator implements Validator {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SIGNS = "!$&?*@^_-+=.,";
    private static final String LOGIN_ERROR_NO_CREATED =
            "Логин должен состоять только из цифр и латинских букв и иметь длину от 8 до 24 символов";
    private static final String LOGIN_ERROR_CREATED =
            "Такой логин уже существует";
    private static final String EMAIL_ERROR_NO_CREATED =
            "НЕправильный адрес электронной почты";
    private static final String EMAIL_ERROR_CREATED =
            "Пользователь с таким адресом электронной почты уже зарегестрирован";
    private static final String PASSWORD_ERROR_NO_CREATED = "Пароль должен содержать: латинские буквы, " +
            "цифры и спец.символы('!','$','&','?','*','@','^','_','-','+','=','.',',')";
    public String MESSAGE = "";

    @Override
    public boolean IsValid(Object object) {
        ValidatorDto validatorDto = new ValidatorDto();
        boolean isCompare = true;
        Person person = (Person) object;
        String login = person.getLogin();
        String email = person.getEmail();
        String password = person.getPassword();
        if (validatorDto.getPersonByLogin(login) != null) {
            System.out.println("Такой логин есть!");
            isCompare = false;
            MESSAGE = LOGIN_ERROR_CREATED;
        } else if (validatorDto.getPersonByEmail(email) != null) {
            System.out.println("Такая почта есть");
            isCompare = false;
            MESSAGE = EMAIL_ERROR_CREATED;
        } else if (!isLoginValid(login)) {
            System.out.println("Логин невалидный!");
            isCompare = false;
            MESSAGE = LOGIN_ERROR_NO_CREATED;
        } else if (!isEmailValid(email)) {
            System.out.println("Почта невалидная!");
            isCompare = false;
            MESSAGE = EMAIL_ERROR_NO_CREATED;
        } else if (!isPasswordValid(password)) {
            System.out.println("Пароль невалидный!");
            isCompare = false;
            MESSAGE = PASSWORD_ERROR_NO_CREATED;
        }
        return isCompare;
    }

    private boolean isEmailValid(String email) {
        boolean flag = false;
        for (int i = 0; i < email.length(); i++) {
            String sign = Character.toString(email.charAt(i));
            if (sign.equals("@"))
                flag = true;
        }
        return flag;
    }

    private boolean isLoginValid(String login) {
        boolean flag = true;
        if (login.length() < 8 || login.length() > 24) {
            flag = false;
        } else {
            for (int i = 0; i < login.length(); i++) {
                String letter = Character.toString(login.charAt(i));
                if (SIGNS.contains(letter))
                    flag = false;
            }
        }
        return flag;
    }

    private boolean isPasswordValid(String password) {
        boolean containLetter = false;
        boolean containNumber = false;
        boolean containSigns = false;
        for (int i = 0; i < password.length(); i++) {
            String string = Character.toString(password.charAt(i));
            if (ALPHABET.contains(string))
                containLetter = true;
            else if (SIGNS.contains(string))
                containSigns = true;
            else if (NUMBERS.contains(string))
                containNumber = true;
        }
        return (containNumber && containLetter && containSigns);
    }
}
