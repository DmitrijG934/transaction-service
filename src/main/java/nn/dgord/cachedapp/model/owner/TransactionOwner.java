package nn.dgord.cachedapp.model.owner;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nn.dgord.cachedapp.model.BaseEntity;
import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.item.Item;
import nn.dgord.cachedapp.model.transaction.UserTransaction;
import nn.dgord.cachedapp.utils.Convertible;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "transaction_owner")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TransactionOwner extends BaseEntity implements Convertible<OwnerDto> {
    @Id
    @GeneratedValue
    private UUID userId;
    @JsonFormat(pattern = "Y-MM-dd H:mm:ss")
    @Column(
            name = "owner_name",
            nullable = false,
            unique = true
    )
    private String name;

    @NotNull
    private Date registerDate;
    private Boolean isActive;

    @ElementCollection(targetClass = Priority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "owner_priority", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(value = EnumType.STRING)
    private Set<Priority> priority = new HashSet<>();

    @OneToMany(targetEntity = UserTransaction.class)
    @JoinColumn
    private Set<UserTransaction> performedTransactions = new HashSet<>();

    @OneToMany(targetEntity = Item.class)
    @JoinColumn
    private Set<Item> items = new HashSet<>();

    @ManyToOne(
            targetEntity = Department.class,
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
            }
    )
    @JoinColumn(name = "department_id")
    // TODO: implement cascade delete for 'departments' table
    private Department department;

    @Override
    public OwnerDto convert() {
        return OwnerDto.builder()
                .departmentId(this.getDepartment().getDepartmentId())
                .id(this.getUserId())
                .isActive(this.getIsActive())
                .name(this.getName())
                .priority(this.getPriority())
                .registerDate(this.getRegisterDate())
                .build();
    }

    public enum Priority {
        PRIMARY, OPTIONAL
    }
}
