package dasturlash.uz.service;

import dasturlash.uz.Container.ComponentContainer;
import dasturlash.uz.controller.AdminController;
import dasturlash.uz.controller.UserController;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.MD5Util;
import dasturlash.uz.util.ProfileValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AdminController adminController;
    @Autowired
    private UserController userController;
    @Autowired
    private ComponentContainer componentContainer;

    public void login(String phone, String pswd) {
        ProfileDTO profileDTO = profileRepository.getByPhone(phone);
        if (profileDTO == null) {
            System.out.println("Phone is wrong!");
            return;
        }
        if (!profileDTO.getPswd().equals(MD5Util.getMd5Hash(pswd))) {
            System.out.println("Password is wrong!");
            return;
        }
        if (!profileDTO.getStatus().equals(ProfileStatus.ACTIVE)) {
            System.out.println("Profile in wrong status.");
            return;
        }
        System.out.println("\n----- Welcome to Atto System -----\n");
        componentContainer.setCurrentProfile(profileDTO);
        System.out.println(profileDTO.getRole().name());
        if (profileDTO.getRole().equals(ProfileRole.ADMIN)) {
            adminController.start();
        } else if (profileDTO.getRole().equals(ProfileRole.USER)) {
            userController.start();
        }
    }

    public void registration(ProfileDTO profile){
        // check
        if (!ProfileValidationUtil.isValid(profile)){
            return;
        }
        // check phone
        ProfileDTO existProfile = profileRepository.getByPhone(profile.getPhone());
        if (existProfile != null){
            System.out.println("Phone exists");
            return;
        }
        // save
        profile.setCreatedDate(LocalDateTime.now());
        profile.setRole(ProfileRole.USER);
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setPswd(MD5Util.getMd5Hash(profile.getPswd()));
        profile.setVisible(true);
        int effectedRow = profileRepository.createWithPSS(profile);
        if (effectedRow == 1){
            System.out.println("Registration completed.");
        } else {
            System.out.println("Registration failed");
        }
    }
}
