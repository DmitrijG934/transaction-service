package nn.dgord.cachedapp.repository;

import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.owner.TransactionOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OwnerJpaRepository extends JpaRepository<TransactionOwner, UUID> {
    List<TransactionOwner> findAllByDepartment(Department department);
    TransactionOwner findTransactionOwnerByDepartmentAndUserId(Department department, UUID ownerId);
}
