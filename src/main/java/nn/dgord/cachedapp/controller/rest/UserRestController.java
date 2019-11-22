package nn.dgord.cachedapp.controller.rest;

import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.owner.OwnerDto;
import nn.dgord.cachedapp.model.owner.TransactionOwner;
import nn.dgord.cachedapp.repository.OwnerJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static nn.dgord.cachedapp.CachedAppApplication.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX + "owner/{departmentId}")
public class UserRestController {
    private final OwnerJpaRepository ownerJpaRepository;

    public UserRestController(OwnerJpaRepository ownerJpaRepository) {
        this.ownerJpaRepository = ownerJpaRepository;
    }

    @GetMapping
    public List<OwnerDto> listOwnersById(
            @PathVariable(name = "departmentId") Department department,
            @RequestParam(required = false, name = "amount") final String size) {
        return getOwners(department, size);
    }

    @PostMapping
    public ResponseEntity<Object> createOwner(
            @PathVariable(name = "departmentId") final Department department,
            @RequestBody TransactionOwner owner) {
        owner.setCreatedAt(new Date());
        owner.setRegisterDate(new Date());
        owner.setDepartment(department);

        TransactionOwner savedOwner = ownerJpaRepository.save(owner);
        return ResponseEntity.ok(savedOwner.convert());
    }

    @DeleteMapping("delete/{ownerId}")
    public ResponseEntity<Object> deleteOwner(
            @PathVariable(name = "departmentId") final Department department,
            @PathVariable final String ownerId
    ) {

        TransactionOwner owner = ownerJpaRepository
                .findTransactionOwnerByDepartmentAndUserId(department, UUID.fromString(ownerId));
        ownerJpaRepository.delete(owner);
        return ResponseEntity.ok(owner.convert());
    }

    private List<OwnerDto> getOwners(Department department, String size) {
        List<TransactionOwner> ownersFromDb =
                ownerJpaRepository.findAllByDepartment(department);
        if (!StringUtils.isEmpty(size)) {
            int amount = Integer.parseInt(size);
            if (amount >= 1 && ownersFromDb.size() >= amount) {
                List<OwnerDto> sizedOwnersList = new ArrayList<>();
                for (int i = 0; i < amount; i++) {
                    sizedOwnersList.add(ownersFromDb.get(i).convert());
                }
                return sizedOwnersList;
            }

        }
        return ownersFromDb
                .stream()
                .map(TransactionOwner::convert)
                .collect(Collectors.toList());
    }
}
