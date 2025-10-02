package dasturlash.uz.service;

import dasturlash.uz.Container.ComponentContainer;
import dasturlash.uz.dto.CardDTO;
import dasturlash.uz.dto.ProfileCardDTO;
import dasturlash.uz.enums.GeneralStatus;
import dasturlash.uz.enums.TransactionType;
import dasturlash.uz.repository.CardRepository;
import dasturlash.uz.repository.ProfileCardRepository;
import dasturlash.uz.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ProfileCardRepository profileCardRepository;
    @Autowired
    private ComponentContainer componentContainer;
    @Autowired
    private TransactionService transactionService;

    public void createCard(String cardNumber, String expiredDate) {
        //todo - Card number and expiredDate Valid

        CardDTO existCard = cardRepository.getByCardNumber(cardNumber);
        if (existCard != null) {
            System.out.println("Card Number exists");
            return;
        }
        CardDTO card = new CardDTO();
        card.setCardNumber(cardNumber);
        card.setExpDate(getCardExpDate(expiredDate));
        card.setBalance(0.0);
        card.setStatus(GeneralStatus.ACTIVE);
        card.setVisible(true);
        card.setCreated_date(LocalDateTime.now());
        int effectedRow = cardRepository.createWithPSS(card);
        if (effectedRow == 1) {
            System.out.println("Card created.");
        } else {
            System.out.println("Card not created");
        }
    }

    public void cardList() {
        List<CardDTO> cardDTOList = cardRepository.getList();
        /*for (CardDTO cardDTO: cardDTOList){
            System.out.println(cardDTO);
        }*/

        System.out.printf("-------------------------------------------------------------------------%n");
        System.out.printf("                              Card List                                  %n");
        System.out.printf("-------------------------------------------------------------------------%n");
        System.out.printf("| %-4s | %-16s | %-12s | %-8s | %-18s |%n", "id", "CardNumber", "ExpiredDate", "Status", "CreatedDate");
        System.out.printf("-------------------------------------------------------------------------%n");

        for (CardDTO cardDTO : cardDTOList) {
            System.out.printf("| %-4d | %-16s | %-12s | %-8s | %-18s |%n",
                    cardDTO.getId(), cardDTO.getCardNumber(), cardDTO.getExpDate(), cardDTO.getStatus(), cardDTO.getCreated_date());
        }
        System.out.printf("-------------------------------------------------------------------------%n");
    }

    public void adminUpdateCard(String cardNumber, String expiredDate) {
        CardDTO exist = cardRepository.getByCardNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(cardNumber);
        LocalDate expDate = getCardExpDate(expiredDate);
        if (expDate == null) {
            return;
        }
        cardDTO.setExpDate(expDate);

        int n = cardRepository.updateCard(cardDTO);
        if (n == 1) {
            System.out.println("Card updated");
        }
    }

    public void profileCardList(Integer profileId) {
        // {card_number, exp_date, balance, created_date}

        List<ProfileCardDTO> profileCardDTOList = profileCardRepository.getProfileCardListByProfileId(profileId);
        if (profileCardDTOList.isEmpty()) {
            System.out.println("Card not found");
            return;
        }
        System.out.printf("-------------------------------------------------------------------------%n");
        System.out.printf("                              Card List                                  %n");
        System.out.printf("-------------------------------------------------------------------------%n");
        System.out.printf("| %-16s | %-12s | %-9s | %-18s |%n", "CardNumber", "ExpiredDate", "balance", "CreatedDate");
        System.out.printf("-------------------------------------------------------------------------%n");
        for (ProfileCardDTO profileCardDTO : profileCardDTOList) {
            System.out.printf("| %-16s | %-12s | %-9s | %-18s |%n",
                    profileCardDTO.getCard().getCardNumber(), profileCardDTO.getCard().getExpDate(), profileCardDTO.getCard().getBalance(), profileCardDTO.getCreatedDate());
        }
        System.out.printf("-------------------------------------------------------------------------%n");
    }

    public void addCardToProfile(String cardNumber) {
        Integer profileId = componentContainer.getCurrentProfile().getId();
        CardDTO cardDTO = cardRepository.getByCardNumber(cardNumber);

        if (cardDTO == null) {
            System.out.println("Card not found");
            return;
        }

        ProfileCardDTO profileCardDTO = new ProfileCardDTO();
        profileCardDTO.setCardId(cardDTO.getId());
        profileCardDTO.setProfileId(profileId);
        profileCardDTO.setVisible(true);
        profileCardDTO.setCreatedDate(LocalDateTime.now());

        int n = profileCardRepository.save(profileCardDTO);
        if (n == 1) {
            System.out.println("Card add completed successfully");
        }
    }

    public LocalDate getCardExpDate(String expiredDate) {
        // expiredDate = 10/29
        try {
            String[] strList = expiredDate.split("/");
            int month = Integer.parseInt(strList[0]);
            int year = Integer.parseInt("20" + strList[1]);
            LocalDate localDate = LocalDate.of(year, month, 1);
            return localDate;
        } catch (RuntimeException e) {
            System.out.println("Card expired  date wrong");
        }
        return null;
    }

    public void changeCardStatus(String cardNumber, Integer profileId) {
        CardDTO cardDTO = cardRepository.getByCardNumber(cardNumber);

        if (cardDTO == null) {
            System.out.println("Card not found");
            return;
        }

        ProfileCardDTO profileCard = profileCardRepository.getProfileCardByCardId(cardDTO.getId());

        if (profileCard.getProfileId() != profileId) {
            System.out.println("This card is not your card");
            return;
        }

        String status;

        if (cardDTO.getStatus() == GeneralStatus.ACTIVE){
            status = "BLOCK";
        } else {
            status = "ACTIVE";
        }

        int n = cardRepository.changeCardStatus(cardDTO.getId(), status);
        if (n == 1) {
            System.out.println("Card status change completed successfully");
        }
    }

    public void deleteCard(Integer profileId, String cardNumber){
        CardDTO cardDTO = cardRepository.getByCardNumber(cardNumber);
        if (cardDTO == null){
            System.out.println("Card not found");
            return;
        }

        ProfileCardDTO profileCard = profileCardRepository.getProfileCardByCardId(cardDTO.getId());
        if (profileCard == null || !profileCard.getProfileId().equals(profileId)){
            System.out.println("This card not belongs to you!");
            return;
        }

        int n = profileCardRepository.deleteProfileCard(profileId, cardDTO.getId());
        if (n != 0){
            System.out.println("Card deleted");
        }
    }

    public void profileRefillCard(String cardNumber, Double amount) {
        CardDTO card = cardRepository.getByCardNumber(cardNumber);
        if (card == null){
            System.out.println("Card not found");
            return;
        }

        if (!card.getVisible() || !card.getStatus().equals(GeneralStatus.ACTIVE)){
            System.out.println("Card is in wrong status");
            return;
        }
        // Refill Card
        double balance = card.getBalance() + amount;
        card.setBalance(balance);
        cardRepository.updateCardBalance(cardNumber, balance);
        // make transaction
        transactionService.createTransaction(card.getId(), null, amount, TransactionType.REFILL);
    }
}
