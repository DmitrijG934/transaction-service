package nn.dgord.cachedapp.repository;

import nn.dgord.cachedapp.model.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentJpaRepository extends JpaRepository<Department, UUID> {
    Department findByName(String name);
}
