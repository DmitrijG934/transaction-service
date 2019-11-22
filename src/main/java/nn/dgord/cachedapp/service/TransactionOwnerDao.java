package nn.dgord.cachedapp.service;

import lombok.extern.slf4j.Slf4j;
import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.owner.TransactionOwner;
import nn.dgord.cachedapp.repository.OwnerJpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static nn.dgord.cachedapp.CachedAppApplication.TRANSACTION_OWNER_CACHE_NAME;

@Slf4j
@Service
public class TransactionOwnerDao implements BaseDao<TransactionOwner, UUID> {
    private final OwnerJpaRepository repository;

    public TransactionOwnerDao(OwnerJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TransactionOwner> getAll() {
        log.info("Received request to get list of transaction owners.");
        return repository.findAll();
    }

    public List<TransactionOwner> getAllByDepartment(Department department) {
        log.info("Received request to get list of transaction owners by department: {}.", department);
        return repository.findAllByDepartment(department);
    }

    @Cacheable(cacheNames = TRANSACTION_OWNER_CACHE_NAME,
            key = "#transactionOwner.name")
    public TransactionOwner getByDepartmentAndUserId(Department department, TransactionOwner transactionOwner) {
        log.info("Received request to get transaction owners by id: {}.", transactionOwner.getUserId());
        return repository.findTransactionOwnerByDepartmentAndUserId(department, transactionOwner.getUserId());
    }

    @Cacheable(cacheNames = TRANSACTION_OWNER_CACHE_NAME)
    public TransactionOwner getByDepartmentAndUserId(Department department, UUID userId) {
        log.info("Received request to get transaction owners by id: {}.", userId);
        return repository.findTransactionOwnerByDepartmentAndUserId(department, userId);
    }

    @Cacheable(cacheNames = TRANSACTION_OWNER_CACHE_NAME)
    @Override
    public TransactionOwner getOne(UUID uuid) {
        log.info("Received request to get list of transaction owners by id: {}.", uuid);
        return repository.getOne(uuid);
    }

    @Override
    public void delete(UUID uuid) {
        log.info("Received request to delete transaction owner by id: {}.", uuid);
        repository.deleteById(uuid);
    }

    @Override
    public TransactionOwner create(TransactionOwner entity) {
        log.info("Received request to create transaction owner: {}.", entity);
        return repository.save(entity);
    }

    @Override
    public TransactionOwner update(TransactionOwner updatedEntity, UUID uuid) {
        TransactionOwner ownerFromDb = getOne(uuid);
        log.info("Received request to update transaction owner: {} -> {}.", ownerFromDb, updatedEntity);
        BeanUtils.copyProperties(updatedEntity, ownerFromDb, "userId");
        return repository.save(ownerFromDb);
    }
}
