package nn.dgord.cachedapp.model.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private UUID id;
    private String name;
    private Date registerDate;
    private Boolean isActive;
    private Set<TransactionOwner.Priority> priority;
    private UUID departmentId;
}
