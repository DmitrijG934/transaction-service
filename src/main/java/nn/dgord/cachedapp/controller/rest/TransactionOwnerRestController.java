package nn.dgord.cachedapp.controller.rest;

import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.owner.OwnerDto;
import nn.dgord.cachedapp.model.owner.TransactionOwner;
import nn.dgord.cachedapp.service.TransactionOwnerDao;
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
public class TransactionOwnerRestController {
    private final TransactionOwnerDao transactionOwnerDao;

    public TransactionOwnerRestController(TransactionOwnerDao transactionOwnerDao) {
        this.transactionOwnerDao = transactionOwnerDao;
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

        TransactionOwner savedOwner = transactionOwnerDao.create(owner);
        return ResponseEntity.ok(savedOwner.convert());
    }

    @GetMapping("{userId}")
    public OwnerDto getById(@PathVariable(name = "departmentId") final Department department,
                                    @PathVariable(name = "userId") final TransactionOwner transactionOwner) {
        return transactionOwnerDao.getByDepartmentAndUserId(department,
                transactionOwner).convert();
    }

    @DeleteMapping("delete/{ownerId}")
    public ResponseEntity<Object> deleteOwner(
            @PathVariable(name = "departmentId") final Department department,
            @PathVariable final String ownerId
    ) {

        TransactionOwner owner = transactionOwnerDao
                .getByDepartmentAndUserId(department, UUID.fromString(ownerId));
        transactionOwnerDao.delete(owner.getUserId());
        return ResponseEntity.ok(owner.convert());
    }

    private List<OwnerDto> getOwners(Department department, String size) {
        List<TransactionOwner> ownersFromDb =
                transactionOwnerDao.getAllByDepartment(department);
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
