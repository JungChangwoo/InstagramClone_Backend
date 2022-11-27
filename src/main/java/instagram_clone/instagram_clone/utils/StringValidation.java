package instagram_clone.instagram_clone.utils;

public class StringValidation {

    public static boolean isEmptyOrNull(String str){
        if (str != null && !str.isBlank()){
            return false;
        } else {
            return true;
        }
    }
}
