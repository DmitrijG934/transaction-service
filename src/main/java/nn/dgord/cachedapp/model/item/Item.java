package nn.dgord.cachedapp.model.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nn.dgord.cachedapp.model.BaseEntity;
import nn.dgord.cachedapp.model.transaction.UserTransaction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "owner_items")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Item extends BaseEntity {
    @Id
    @GeneratedValue
    @Getter
    private UUID itemId;
    @Getter
    @Setter
    private String description;
    @OneToOne(
            targetEntity = UserTransaction.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "user_transaction_id")
    @Getter
    @Setter
    private UserTransaction transactionId;
}
