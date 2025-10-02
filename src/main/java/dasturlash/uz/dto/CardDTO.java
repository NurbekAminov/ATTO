package dasturlash.uz.dto;

import dasturlash.uz.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CardDTO {
    private Integer id;
    private String cardNumber;
    private LocalDate expDate;
    private Double balance;
    private GeneralStatus status;
    private Boolean visible;
    private LocalDateTime created_date;
}
