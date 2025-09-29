package dasturlash.uz.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileCardDTO {
    private Integer id;
    private Integer cardId;
    private Integer profileId;
    private Boolean visible;
    private CardDTO card;
    private LocalDateTime createdDate;
}
