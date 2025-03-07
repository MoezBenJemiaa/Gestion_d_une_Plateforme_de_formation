public class All_exeptions  extends Exception{

    All_exeptions(String ch){
        super(ch);
    }

    
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        int atIndex = email.indexOf("@");
        int dotIndex = email.lastIndexOf(".");

        return (atIndex > 0 && dotIndex > atIndex + 1 && dotIndex < email.length() - 1);

    }
}
