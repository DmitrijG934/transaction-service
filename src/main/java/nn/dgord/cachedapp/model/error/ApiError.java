package nn.dgord.cachedapp.model.error;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {
    private String message;
    private int code;
    private LocalDateTime timestamp;
}
