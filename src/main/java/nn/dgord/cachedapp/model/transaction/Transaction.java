package nn.dgord.cachedapp.model.transaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nn.dgord.cachedapp.model.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class Transaction extends BaseEntity {
    @Id
    @GeneratedValue
    @Getter
    protected UUID id;
}
