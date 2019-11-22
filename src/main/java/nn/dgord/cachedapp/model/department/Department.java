package nn.dgord.cachedapp.model.department;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nn.dgord.cachedapp.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseEntity {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private UUID departmentId;
    @Column(
            name = "department_name",
            nullable = false,
            unique = true
    )
    @Getter
    @Setter
    private String name;
}
