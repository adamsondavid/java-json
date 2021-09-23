package io.github.adamsondavid.json;

import io.github.adamsondavid.json.util.People;
import io.github.adamsondavid.json.util.PeopleCollection;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonSerializerTest {

  @Test
  public void serializeObjectTest() {
    String actual = JsonSerializer.toString(new PeopleCollection(new People("Alice", 20, true), new People("Bob", 12.75f, false)));
    String expected = "{\"people\": [{\"name\": \"Alice\", \"age\": 20.0, \"adult\": true}, {\"name\": \"Bob\", \"age\": 12.75, \"adult\": false}]}";

    assertEquals(expected, actual);
  }

}