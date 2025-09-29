package dasturlash.uz.Container;

import dasturlash.uz.dto.ProfileDTO;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ComponentContainer {
    public static Scanner scanner = new Scanner(System.in);
    private ProfileDTO currentProfile;

    /*public int getNumber() {

    }*/

    public ProfileDTO getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(ProfileDTO profileDTO) {
        this.currentProfile = profileDTO;
    }
}
