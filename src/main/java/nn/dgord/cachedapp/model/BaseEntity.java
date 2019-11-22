package nn.dgord.cachedapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {
    @Column(nullable = false)
    @JsonFormat(pattern = "Y-MM-dd H:mm:ss")
    @Getter
    @Setter
    protected Date createdAt;
    @JsonFormat(pattern = "Y-MM-dd H:mm:ss")
    @Getter
    @Setter
    protected Date updatedAt;
}
