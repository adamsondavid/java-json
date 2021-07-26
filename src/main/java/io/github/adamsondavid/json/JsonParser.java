package io.github.adamsondavid.json;

import io.github.adamsondavid.json.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonParser {

  private final BufferedReader reader;
  private String line;
  private int token;
  private int lineNumber;
  private int charNumber;

  public JsonParser(Reader reader) throws IOException {
    this.reader = new BufferedReader(reader);
    this.nextToken();
  }

  public JsonParser(String json) throws IOException {
    this(new StringReader(json));
  }

  private void nextToken() throws IOException {
    if(this.line == null || this.charNumber == this.line.length()) {
      this.line = this.reader.readLine();
      this.lineNumber++;
      this.charNumber = 0;
    }
    this.token = this.line == null ? -1 : this.line.charAt(this.charNumber);
    this.charNumber++;
  }

  private void accept(int token) throws IOException, JsonSyntaxException {
    if(this.token != token) {
      throw this.createSyntaxException("expected '" + (char) token + "'.");
    }
    this.nextToken();
  }

  private void accept(String tokens) throws JsonSyntaxException, IOException {
    for(char c : tokens.toCharArray()) {
      this.accept(c);
    }
  }

  private JsonSyntaxException createSyntaxException(String reason) {
    StringBuilder error = new StringBuilder()
        .append("syntax error on line ")
        .append(this.lineNumber)
        .append(":")
        .append(this.charNumber)
        .append(". unexpected input. ")
        .append(reason)
        .append("\n")
        .append(this.line)
        .append("\n");
    for(int i = 1; i < this.charNumber; i++) {
      if(this.charNumber - i > 5) {
        error.append(" ");
      }else {
        error.append("~");
      }
    }
    error.append("^");
    return new JsonSyntaxException(error.toString(), this.lineNumber, this.charNumber);
  }

  private JsonSyntaxException createSyntaxException() {
    return this.createSyntaxException("");
  }

  public Object next() throws JsonSyntaxException, IOException {
    return this.element();
  }

  public JsonObject nextObject() throws JsonSyntaxException, IOException {
    return (JsonObject) this.next();
  }

  public JsonArray nextArray() throws JsonSyntaxException, IOException {
    return (JsonArray) this.next();
  }

  private Object element() throws JsonSyntaxException, IOException {
    this.ws();
    Object value = this.value();
    this.ws();
    return value;
  }

  private Object value() throws JsonSyntaxException, IOException {
    switch(this.token) {
      case '{': return this.object();
      case '[': return this.array();
      case '"': return this.string();
      case '+':
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9': return this.number();
      case 't': this.accept("true"); return true;
      case 'f': this.accept("false"); return false;
      case 'n': this.accept("null"); return null;
    }
    throw this.createSyntaxException();
  }

  private JsonObject object() throws JsonSyntaxException, IOException {
    this.accept('{');
    JsonObject jsonObject = this.members();
    this.accept('}');
    return jsonObject;
  }

  private JsonObject members() throws JsonSyntaxException, IOException {
    this.ws();
    if(this.token == '}') {
      return new JsonObject();
    }
    Pair<String, Object> member = this.member();
    if(this.token == ',') {
      this.accept(',');
    }
    JsonObject object = this.members();
    object.put(member.getFirst(), member.getSecond());
    return object;
  }

  private Pair<String, Object> member() throws JsonSyntaxException, IOException {
    this.ws();
    String key = this.string();
    this.ws();
    this.accept(':');
    Object value = this.element();
    return new Pair<>(key, value);
  }

  private JsonArray array() throws JsonSyntaxException, IOException {
    this.accept('[');
    JsonArray jsonArray = this.elements();
    this.accept(']');
    return jsonArray;
  }

  private JsonArray elements() throws JsonSyntaxException, IOException {
    this.ws();
    if(this.token == ']') {
      return new JsonArray();
    }
    Object element = this.element();
    if(this.token == ',') {
      this.accept(',');
    }
    JsonArray elements = this.elements();
    elements.add(0, element);
    return elements;
  }

  private String string() throws JsonSyntaxException, IOException {
    this.accept('"');
    String characters = this.characters();
    this.accept('"');
    return characters;
  }

  private String characters() throws JsonSyntaxException, IOException {
    if(this.token == '"') {
      return "";
    }
    char character = this.character();
    return character + this.characters();
  }

  private char character() throws JsonSyntaxException, IOException {
    int token = this.token;
    if(token == '\\') {
      this.accept('\\');
      return this.escape();
    } else if(token >= 0x20 && token <= 0x10FFFF) {
      this.accept(token);
      return (char) token;
    }
    throw this.createSyntaxException();
  }

  private char escape() throws JsonSyntaxException, IOException {
    switch(this.token) {
      case '"': this.accept('"'); return '"';
      case '\\': this.accept('\\'); return '\\';
      case '/': this.accept('/'); return '/';
      case 'b': this.accept('b'); return '\b';
      case 'f': this.accept('f'); return '\f';
      case 'n': this.accept('n'); return '\n';
      case 'r': this.accept('r'); return '\r';
      case 't': this.accept('t'); return '\t';
      case 'u': this.accept('u'); return (char) Integer.parseInt(this.hex() + this.hex() + this.hex() + this.hex(), 16);
    }
    throw this.createSyntaxException();
  }

  private String hex() throws JsonSyntaxException, IOException {
    if(this.token >= '0' && this.token <= '9') {
      return this.digit();
    }
    switch(this.token) {
      case 'A': this.accept('A'); return "a";
      case 'a': this.accept('a'); return "a";
      case 'B': this.accept('B'); return "b";
      case 'b': this.accept('b'); return "b";
      case 'C': this.accept('C'); return "c";
      case 'c': this.accept('c'); return "c";
      case 'D': this.accept('D'); return "d";
      case 'd': this.accept('d'); return "d";
      case 'E': this.accept('E'); return "e";
      case 'e': this.accept('e'); return "e";
      case 'F': this.accept('F'); return "f";
      case 'f': this.accept('f'); return "f";
    }
    throw this.createSyntaxException();
  }

  private double number() throws JsonSyntaxException, IOException {
    String integer = this.integer();
    String fraction = this.fraction();
    String exponent = this.exponent();
    return Double.parseDouble(integer + fraction + exponent);
  }

  private String integer() throws JsonSyntaxException, IOException {
    return this.sign() + this.digits();
  }

  private String digits() throws JsonSyntaxException, IOException {
    if(this.token >= '0' && this.token <= '9') {
      String digit = this.digit();
      String digits = this.digits();
      return digit + digits;
    }
    return "";
  }

  private String digit() throws JsonSyntaxException, IOException {
    if(this.token == '0') {
      this.accept('0');
      return "0";
    } else if(this.token >= '1' && this.token <= '9') {
      return this.onenine();
    }
    throw this.createSyntaxException();
  }

  private String onenine() throws JsonSyntaxException, IOException {
    int number = this.token;
    if(number >= '1' && number <= '9') {
      this.accept(number);
      return "" + (char) number;
    }
    throw this.createSyntaxException();
  }

  private String fraction() throws JsonSyntaxException, IOException {
    if(this.token == '.') {
      this.accept('.');
      return "." + this.digits();
    }
    return "";
  }

  private String exponent() throws JsonSyntaxException, IOException {
    switch(this.token) {
      case 'e': this.accept('e'); return "e" + this.sign() + this.digits();
      case 'E': this.accept('E'); return "e" + this.sign() + this.digits();
    }
    return "";
  }

  private String sign() throws JsonSyntaxException, IOException {
    if(this.token >= '0' && this.token <= '9') {
      return "";
    } else if(this.token == '-') {
      this.accept('-');
      return "-";
    } else if(this.token == '+') {
      this.accept('+');
      return "+";
    }
    throw this.createSyntaxException();
  }

  private void ws() throws JsonSyntaxException, IOException {
    switch(this.token) {
      case '{':
      case '}':
      case '[':
      case ']':
      case '"':
      case ':':
      case 't':
      case 'f':
      case 'n':
      case ',':
      case '+':
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
      case -1: return;
      case ' ': this.accept(' '); this.ws(); return;
      case '\n': this.accept('\n'); this.ws(); return;
      case '\r': this.accept('\r'); this.ws(); return;
      case '\t': this.accept('\t'); this.ws(); return;
    }
    throw this.createSyntaxException();
  }

}
