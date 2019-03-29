package pl.pwr;

import com.sun.istack.internal.*;
import javax.validation.constraints.Size;

public class User {

    @NotNull
    @Size(min = 1, max = 10, message = "Pole login ma nieodpowiednia dlugosc!")
    @DaneOsoboweField(name = "LoginAdnotacja")
    private String login;

    @NotNull
    @Size(min = 1, max = 100, message = "Pole imie ma nieodpowiednią długość!")
    @DaneOsoboweField(name = "NameAdnotacja")
    private String name;

    @NotNull
    @Size(min = 1, max = 100, message = "Pole nazwisko ma nieodpowiednią długość!")
    @DaneOsoboweField(name = "LastNameAdnotacja")
    private String lastName;

    @NotNull
    @Size(min = 1, max = 100, message = "Pole haslo ma nieodpowiednią długość!")
    @DaneOsoboweField(name = "PasswordAdnotacja")
    private String password;

    public User() { }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }
}
