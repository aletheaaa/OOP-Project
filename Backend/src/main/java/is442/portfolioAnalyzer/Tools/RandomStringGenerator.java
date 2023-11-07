package is442.portfolioAnalyzer.Tools;

public class RandomStringGenerator {
  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static String generateRandomString(int minLength, int maxLength) throws IllegalArgumentException {
    // Array containing ASCII values of '!', '#', '$', '%','&', '+', '=', '@', '^'
    int[] specialCharArray = new int[] { 33, 35, 36, 37, 38, 43, 61, 64, 94 };

    if (minLength < 1) {
      throw new IllegalArgumentException("minLength cannot be less than 1!");
    }
    int randomLength = getRandomNumber(minLength, maxLength);
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < randomLength; i++) {
      int charSet = getRandomNumber(0, 4);
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
        case 3:
          randomAscii = specialCharArray[getRandomNumber(0, 9)];
          break;
      }

      // 48-57 is ASCII for 0-9, 65-90 is for A-Z, 97-122 is for a-z
      // special characters are stored in the special characters array
      char randomChar = (char) randomAscii;
      sb.append(randomChar);
    }

    // check if string contains at least one digit, one uppercase letter,
    // one lowercase letter and one special character
    // if not, rerun function
    String randomPassword = sb.toString();

    boolean containsDigit = false;
    boolean containsLowercaseLetter = false;
    boolean containsUppercaseLetter = false;
    boolean containsSpecialCharacter = false;

    for (int i = 0; i < randomPassword.length(); i++) {
      if (randomPassword.charAt(i) >= 48 && randomPassword.charAt(i) <= 57) {
        containsDigit = true;
      } else if (randomPassword.charAt(i) >= 65 && randomPassword.charAt(i) <= 90) {
        containsLowercaseLetter = true;
      } else if (randomPassword.charAt(i) >= 97 && randomPassword.charAt(i) <= 122) {
        containsUppercaseLetter = true;
      } else {
        for (int j = 0; j < specialCharArray.length; j++) {
          if (randomPassword.charAt(i) == specialCharArray[j]) {
            containsSpecialCharacter = true;
          }
        }
      }
    }

    if (containsDigit && containsLowercaseLetter && containsUppercaseLetter && containsSpecialCharacter) {
      return randomPassword;
    } else {
      return generateRandomString(minLength, maxLength);
    }
  }

  public static String generateRandomString(int length) {
    return generateRandomString(length, length + 1);
  }

  public static String generateRandomString() {
    return generateRandomString(8, 25);
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