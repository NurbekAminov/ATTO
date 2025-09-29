package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.repository.ProfileRepository;
import dasturlash.uz.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class InitService {
    @Autowired
    private ProfileRepository profileRepository;
    public void initAdmin() {
        String phone = "998911234567";
        ProfileDTO existsAdmin = profileRepository.getByPhone(phone);
        if (existsAdmin != null){
            return;
        }
        ProfileDTO profile = new ProfileDTO();
        profile.setName("Alish");
        profile.setSurname("Aliyev");
        profile.setPhone(phone);
        profile.setPswd(MD5Util.getMd5Hash("12345"));
        profile.setCreatedDate(LocalDateTime.now());
        profile.setStatus(ProfileStatus.ACTIVE);
        profile.setRole(ProfileRole.ADMIN);
        profile.setVisible(true);
        profileRepository.createWithPSS(profile);  // save
    }
}
