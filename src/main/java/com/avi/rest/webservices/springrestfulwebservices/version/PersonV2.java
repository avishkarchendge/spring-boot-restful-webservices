package com.avi.rest.webservices.springrestfulwebservices.version;

public class PersonV2 {

    private Name name;

    public PersonV2() {
    }

    public PersonV2(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }
}
