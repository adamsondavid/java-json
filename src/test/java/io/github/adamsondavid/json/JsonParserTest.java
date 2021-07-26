package io.github.adamsondavid.json;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class JsonParserTest {

  @Test
  public void parseObjectTest() throws JsonException, IOException {
    JsonObject actual = JsonObject.of(
        """
        {
          "people": [
            {
              "name": "Alice",
              "age": 20,
              "adult": true
            },
            {
              "name": "Bob",
              "age": 12.75,
              "adult": false
            }
          ]
        }
        """
    );

    JsonObject expected = new JsonObject.Builder()
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

    assertEquals(expected, actual);
  }

  @Test
  public void missingColonTest() {
    JsonSyntaxException exception = assertThrows(JsonSyntaxException.class, () -> {
      JsonObject actual = JsonObject.of(
          """
          {"name": "Peter", "age" 21}
          """
      );
    });
    assertEquals(1, exception.getLineNumber());
    assertEquals(25, exception.getCharNumber());
  }

}