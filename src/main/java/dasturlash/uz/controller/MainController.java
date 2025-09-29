package dasturlash.uz.controller;

import dasturlash.uz.Container.ComponentContainer;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.service.AuthService;
import dasturlash.uz.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class MainController {
    @Autowired
    private InitService initService;
    @Autowired
    private AuthService authService;

    public void start() {
        initService.initAdmin();

        boolean b = true;
        while (b) {
            showMenu();
            int num = getNumber();
            switch (num) {
                case 1:
                    login();
                    break;
                case 2:
                    registration();
                    break;
                case 3:
                    ///
                    break;
                case 0:
                    System.out.println("System end");
                    b = false;
                    break;
                default:
                    System.out.println("Number wrong. Mazgi");
            }
        }
    }

    public void showMenu() {
        System.out.println("*** Menu ***");
        System.out.println("1. Login.");
        System.out.println("2. Registration.");
        System.out.println("3. Make Payment.");
        System.out.println("0. Exit.");
    }

    public int getNumber() {
        System.out.print("Chose number: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        return number;
    }

    public void login() {
        System.out.print("Enter phone: ");
        String phone = ComponentContainer.scanner.next();

        System.out.print("Enter password: ");
        String pswd = ComponentContainer.scanner.next();

        authService.login(phone, pswd);
    }

    public void registration() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = scanner.next();

        System.out.print("Enter surname: ");
        String surname = scanner.next();

        System.out.print("Enter phone: ");
        String phone = scanner.next();

        System.out.print("Enter password: ");
        String pswd = scanner.next();

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(name);
        profileDTO.setSurname(surname);
        profileDTO.setPhone(phone);
        profileDTO.setPswd(pswd);

        authService.registration(profileDTO);
    }
}
