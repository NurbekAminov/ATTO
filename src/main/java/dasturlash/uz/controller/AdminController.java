package dasturlash.uz.controller;

import dasturlash.uz.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class AdminController {
    @Autowired
    private CardService cardService;
    public void start() {
        boolean b = true;
        while (b) {
            showMenu();
            int num = getNumber();
            switch (num) {
                case 1:
                    addCard();
                    break;
                case 2:
                    cardService.cardList();
                    break;
                case 3:
                    updateCard();
                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                case 7:

                    break;
                case 8:

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
        System.out.println("*** Admin Menu ***");
        System.out.println("1. Add card.");
        System.out.println("2. Card List.");
        System.out.println("3. Update Card.");
        System.out.println("4. .");
        System.out.println("5. .");
        System.out.println("6. .");
        System.out.println("7. .");
        System.out.println("8. .");
        System.out.println("0. Exit.");
    }

    public int getNumber() {
        System.out.print("Chose number: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        return number;
    }

    private void addCard(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Card Number: ");
        String cardNumber = scanner.next();

        System.out.print("Enter card expired date(MM/YY): ");
        String expiredDate = scanner.next();

        cardService.createCard(cardNumber, expiredDate);
    }

    private void updateCard(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter card number: ");
        String cardNumber = scanner.next();

        System.out.print("Enter card expired date (MM/YY): ");
        String expiredDate = scanner.next();

        cardService.adminUpdateCard(cardNumber, expiredDate);
    }
}
