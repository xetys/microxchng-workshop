package com.mycompany.myapp.domain;

/**
 * Created by on 14.02.17.
 *
 * @author David Steiman
 */
public class User {
    private String firstName;
    private String lastName;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
