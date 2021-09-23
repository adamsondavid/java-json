package io.github.adamsondavid.json;

import io.github.adamsondavid.json.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonBinderTest {

    @Test
    public void bindObjectTest() throws NoSuchEntryException {
        PeopleCollection expected = new PeopleCollection(new People("Alice", 20, true), new People("Bob", 12.75f, false));

        JsonObject peopleCollection = new JsonObject.Builder()
                .put("people", new JsonArray.Builder()
                        .add(new JsonObject.Builder()
                                .put("name", "Alice")
                                .put("age", 20.0)
                                .put("adult", true)
                                .build())
                        .add(new JsonObject.Builder()
                                .put("name", "Bob")
                                .put("age", 12.75)
                                .put("adult", false)
                                .build())
                        .build())
                .build();

        PeopleCollection actual = JsonBinder.to(PeopleCollection.class, peopleCollection);

        assertEquals(expected, actual);
    }

    @Test
    public void missingEntryTest() throws NoSuchFieldException {
        NoSuchEntryException exception = assertThrows(NoSuchEntryException.class, () -> {
            JsonObject alice = new JsonObject.Builder()
                    .put("name", "Alice")
                    .put("adult", true)
                    .build();

            JsonBinder.to(People.class, alice);
        });
        assertEquals(People.class.getDeclaredField("age"), exception.getField());
    }
}
