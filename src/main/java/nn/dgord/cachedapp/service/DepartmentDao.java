package nn.dgord.cachedapp.service;

import lombok.extern.slf4j.Slf4j;
import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.repository.DepartmentJpaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static nn.dgord.cachedapp.CachedAppApplication.DEPARTMENT_CACHE_NAME;

@Slf4j
@Service
public class DepartmentDao implements BaseDao<Department, UUID> {
    private final DepartmentJpaRepository repository;

    public DepartmentDao(DepartmentJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Department> getAll() {
        log.info("Received request to get list of departments.");
        return repository.findAll();
    }

    public List<Department> getAllWithSize(String size) {
        log.info("Received request to get sized list of departments.");
        List<Department> departments = repository.findAll();
        if (!StringUtils.isEmpty(size)) {
            int amount = Integer.parseInt(size);
            if (amount >= 1 && departments.size() >= amount) {
                List<Department> sizedDepartments = new ArrayList<>();
                for (int i = 0; i < amount; i++) {
                    sizedDepartments.add(departments.get(i));
                }
                return sizedDepartments;
            }

        }
        return departments;
    }


    @Override
    @Cacheable(cacheNames = DEPARTMENT_CACHE_NAME)
    public Department getOne(UUID uuid) {
        log.info("Received request to get department by id {}.", uuid);
        return repository.findById(uuid).orElseThrow();
    }

    @Override
    public void delete(UUID uuid) {
        log.info("Received request to delete department by id {}.", uuid);
        repository.deleteById(uuid);
    }

    @Override
    public Department create(Department entity) {
        log.info("Received request to create department: {}.", entity);
        return repository.save(entity);
    }

    @Override
    public Department update(Department updatedEntity, UUID uuid) {
        Optional<Department> optionalDepartment = repository.findById(uuid);
        Department departmentFromDb = optionalDepartment.orElseThrow();
        departmentFromDb.setUpdatedAt(updatedEntity.getUpdatedAt());
        departmentFromDb.setName(updatedEntity.getName());
        log.info("Received request to update department: {} -> {}.", departmentFromDb, updatedEntity);
        return repository.save(departmentFromDb);
    }
}
