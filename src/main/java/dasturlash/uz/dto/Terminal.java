package dasturlash.uz.dto;

import dasturlash.uz.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Terminal {
    private String code;
    private String address;
    private GeneralStatus status;
    private Boolean visible;
    private LocalDateTime created_date;
}
