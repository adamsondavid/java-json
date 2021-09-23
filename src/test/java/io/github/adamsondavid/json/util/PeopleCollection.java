package io.github.adamsondavid.json.util;

import java.util.Arrays;

public class PeopleCollection{
    private People[] people;

    public PeopleCollection(){}

    public PeopleCollection(People... people){
        this.people = people;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PeopleCollection that = (PeopleCollection) obj;
        return Arrays.equals(people, that.people);
    }
}
