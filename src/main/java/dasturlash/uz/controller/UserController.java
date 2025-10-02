package dasturlash.uz.controller;

import dasturlash.uz.Container.ComponentContainer;
import dasturlash.uz.service.CardService;
import dasturlash.uz.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class UserController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ComponentContainer componentContainer;
    @Autowired
    private TransactionService transactionService;

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
                    cardList();
                    break;
                case 3:
                    changeCardStatus();
                    break;
                case 4:
                    deleteCard();
                    break;
                case 5:
                    refill();
                    break;
                case 6:
                    transactionsList();
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
        System.out.println("*** User Menu ***");
        System.out.println("1. Add card.");
        System.out.println("2. Card list.");
        System.out.println("3. Change card status.");
        System.out.println("4. Delete card.");
        System.out.println("5. Refill.");
        System.out.println("6. Transactions list.");
        System.out.println("0. Exit.");
    }

    public int getNumber() {
        System.out.print("Chose number: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        return number;
    }

    public void addCard(){
        System.out.print("Enter card number: ");

        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.next();

        cardService.addCardToProfile(cardNumber);
    }

    public void cardList(){
        Integer profileId = componentContainer.getCurrentProfile().getId();
        cardService.profileCardList(profileId);
    }

    public void changeCardStatus(){
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.next();

        Integer profileId = componentContainer.getCurrentProfile().getId();
        cardService.changeCardStatus(cardNumber,profileId);
    }

    public void deleteCard(){
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.next();

        Integer profileId = componentContainer.getCurrentProfile().getId();
        cardService.deleteCard(profileId, cardNumber);
    }

    public void refill(){
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.next();

        System.out.print("Balance: ");
        Double amount = scanner.nextDouble();

        cardService.profileRefillCard(cardNumber, amount);
    }

    public void transactionsList(){
        Integer profileId = componentContainer.getCurrentProfile().getId();
        transactionService.getTransactionsList(profileId);
    }
}
