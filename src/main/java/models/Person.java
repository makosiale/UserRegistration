package models;

public class Person {
    private String login;
    private String email;
    private String password;

    public Person(String login, String email, String password) {
        this.login=login;
        this.email=email;
        this.password=password;
    }

    public Person() {

    }

    @Override
    public String toString() {
        return "Person{" +
                "login=' " + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getLogin(){
        return login;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public void setLogin(String login){
        this.login= this.login;
    }

    public void setEmail(String email){
        this.email= this.email;
    }

    public void setPassword(String password){
        this.password=password;
    }
}
