package io.github.adamsondavid.json;

public class JsonSyntaxException extends JsonException {

  private int lineNumber;
  private int charNumber;

  public JsonSyntaxException(String message, int lineNumber, int charNumber) {
    super(message);
    this.lineNumber = lineNumber;
    this.charNumber = charNumber;
  }

  public int getLineNumber() {
    return lineNumber;
  }

  public int getCharNumber() {
    return charNumber;
  }

}
