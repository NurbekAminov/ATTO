package dasturlash.uz.util;

import dasturlash.uz.dto.ProfileDTO;

public class ProfileValidationUtil {
    public static Boolean isValid(ProfileDTO profile) {
        if (profile.getName().length() < 3 || profile.getName().length() > 20) {
            return false;
        }
        if (profile.getSurname().length() < 3 || profile.getSurname().length() > 20) {
            return false;
        }
        if (profile.getPhone().length() != 12 || !profile.getPhone().startsWith("998")) {
            return false;
        }
        if (profile.getPswd().length() < 4 || profile.getPswd().length() > 12){
            return false;
        }
        return true;
    }
}
