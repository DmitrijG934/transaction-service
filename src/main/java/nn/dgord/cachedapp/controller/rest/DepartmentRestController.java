package nn.dgord.cachedapp.controller.rest;

import nn.dgord.cachedapp.model.department.Department;
import nn.dgord.cachedapp.model.error.ApiError;
import nn.dgord.cachedapp.service.DepartmentDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static nn.dgord.cachedapp.CachedAppApplication.API_PREFIX;

@RestController
@RequestMapping(API_PREFIX + "departments")
public class DepartmentRestController {
    private final DepartmentDao departmentDao;

    public DepartmentRestController(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @GetMapping
    public List<Department> listDepartments(
            @RequestParam(required = false, name = "amount") final String size) {
        return departmentDao.getAllWithSize(size);
    }

    @GetMapping("{departmentId}")
    public Department getDepartmentById(@PathVariable final String departmentId) {
        return departmentDao.getOne(UUID.fromString(departmentId));
    }

    @PostMapping
    public ResponseEntity<Object> createDepartment(@Valid @RequestBody Department department,
                                                   BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiError.builder()
                            .code(400)
                            .message("Invalid incoming body: " + result.getTarget())
                            .timestamp(LocalDateTime.now())
                            .build());
        }
        department.setCreatedAt(new Date());
        departmentDao.create(department);
        return ResponseEntity.ok(department);
    }

    @PutMapping("{departmentId}")
    public Department updateDepartment(@PathVariable String departmentId,
                                       @RequestBody Department updated) {
        updated.setUpdatedAt(new Date());
        return departmentDao.update(updated, UUID.fromString(departmentId));
    }

    @DeleteMapping("{departmentId}")
    public UUID deleteDepartment(@PathVariable final String departmentId) {
        departmentDao.delete(UUID.fromString(departmentId));
        return UUID.fromString(departmentId);
    }
}
