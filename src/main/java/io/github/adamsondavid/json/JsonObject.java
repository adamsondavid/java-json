package io.github.adamsondavid.json;

import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class JsonObject implements Map<String, Object> {

  private Map<String, Object> map;

  public JsonObject() {
    this.map = new HashMap<>();
  }

  public static JsonObject of(Reader reader) throws JsonException, IOException {
    return new JsonParser(reader).nextObject();
  }

  public static JsonObject of(String string) throws JsonException, IOException {
    return new JsonParser(string).nextObject();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JsonObject that = (JsonObject) o;
    return Objects.equals(map, that.map);
  }

  @Override
  public int hashCode() {
    return Objects.hash(map);
  }

  @Override
  public int size() {
    return this.map.size();
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  @Override
  public Object get(Object key) {
    return this.map.get(key);
  }

  @Override
  public Object put(String key, Object value) {
    return this.map.put(key, value);
  }

  @Override
  public Object remove(Object key) {
    return this.map.remove(key);
  }

  @Override
  public void putAll(Map<? extends String, ?> m) {
    this.map.putAll(m);
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public Set<String> keySet() {
    return this.map.keySet();
  }

  @Override
  public Collection<Object> values() {
    return this.map.values();
  }

  @Override
  public Set<Entry<String, Object>> entrySet() {
    return this.map.entrySet();
  }

  public static class Builder {

    private JsonObject jsonObject;

    public Builder() {
      this.jsonObject = new JsonObject();
    }

    public Builder put(String key, Object value) {
      this.jsonObject.put(key, value);
      return this;
    }

    public JsonObject build() {
      return this.jsonObject;
    }

  }

}
