package nn.dgord.cachedapp.model.transaction;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_transactions")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "user_transaction_id"))
public class UserTransaction extends Transaction {
    @Override
    public UUID getId() {
        return super.getId();
    }
}
