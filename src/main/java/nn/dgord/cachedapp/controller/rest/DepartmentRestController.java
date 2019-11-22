package nn.dgord.cachedapp.controller.rest;

import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.error.ApiError;
import nn.dgord.cachedapp.repository.DepartmentJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static nn.dgord.cachedapp.CachedAppApplication.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX + "departments")
public class DepartmentRestController {
    private final DepartmentJpaRepository repository;

    public DepartmentRestController(DepartmentJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Department> listDepartments(
            @RequestParam(required = false, name = "amount") final String size) {
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

    @PostMapping
    public ResponseEntity<Object> createDepartment(@Valid @RequestBody Department department,
                                                   BindingResult result) {
        department.setCreatedAt(new Date());
        if (result.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiError.builder()
                            .code(400)
                            .message("Invalid incoming body: " + result.getTarget())
                            .timestamp(LocalDateTime.now())
                            .build());
        }
        repository.save(department);
        return ResponseEntity.ok(department);
    }

    @PutMapping("{departmentId}")
    public Department updateDepartment(@PathVariable String departmentId,
                                       @RequestBody Department updated) {
        return repository.findById(UUID.fromString(departmentId))
                .map(dep -> {
                    dep.setUpdatedAt(new Date());
                    dep.setName(updated.getName());
                    repository.save(dep);
                    return dep;
                }).orElseThrow();
    }

    @DeleteMapping("{departmentId}")
    public UUID deleteDepartment(@PathVariable final String departmentId) {
        repository.deleteById(UUID.fromString(departmentId));
        return UUID.fromString(departmentId);
    }
}
