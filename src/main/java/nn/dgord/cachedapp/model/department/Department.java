package nn.dgord.cachedapp.model.department;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nn.dgord.cachedapp.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "departments")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Department extends BaseEntity {
    @Id
    @GeneratedValue
    private UUID departmentId;
    @Column(
            name = "department_name",
            nullable = false,
            unique = true
    )
    private String name;
}
