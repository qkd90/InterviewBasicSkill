public class test{
    public static void main(String args[]){
      String s = "race a car";
      System.out.println(isPalindrome(s));  
    }

    public static boolean isPalindrome(String s) {
        StringBuffer sletter = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sletter.append(Character.toLowerCase(ch));
            }
        }
        StringBuffer letters = new StringBuffer(sletter).reverse();
        if(letters.toString().equals(sletter.toString())){
            return true;
        }
        return false;
    }
  }