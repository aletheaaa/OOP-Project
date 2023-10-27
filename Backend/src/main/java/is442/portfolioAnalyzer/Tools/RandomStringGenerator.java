package is442.portfolioAnalyzer.Tools;

public class RandomStringGenerator {
  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static String generateRandomString(int minLength, int maxLength) throws IllegalArgumentException {
    if (minLength < 1) {
      throw new IllegalArgumentException("minLength cannot be less than 1!");
    }
    int randomLength = getRandomNumber(minLength, maxLength);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < randomLength; i++) {
      int charSet = getRandomNumber(0, 3);
      int randomAscii = 0;
      switch (charSet) {
        case 0:
          randomAscii = getRandomNumber(0, 10) + 48;
          break;
        case 1:
          randomAscii = getRandomNumber(0, 26) + 65;
          break;
        case 2:
          randomAscii = getRandomNumber(0, 26) + 97;
          break;
      }

      char randomChar = (char) randomAscii; // 48-57 is ASCII for 0-9, 65-90 is for A-Z, 97-122 is for a-z
      sb.append(randomChar);
    }

    return sb.toString();
  }

  public static String generateRandomString(int length) {
    return generateRandomString(length, length + 1);
  }

  public static String generateRandomString() {
    return generateRandomString(10, 20);
  }

  // Main method created for Unit Testing
  // public static void main(String[] args) {
  // System.out.println("Random String 1: " + generateRandomString());
  // System.out.println("Random String 1: " + generateRandomString());
  // System.out.println("Random String 1: " + generateRandomString());
  // System.out.println("Random String 2: " + generateRandomString(5));
  // System.out.println("Random String 2: " + generateRandomString(5));
  // System.out.println("Random String 2: " + generateRandomString(5));
  // System.out.println("Random String 3: " + generateRandomString(5, 15));
  // System.out.println("Random String 3: " + generateRandomString(5, 15));
  // System.out.println("Random String 3: " + generateRandomString(5, 15));
  // }
}