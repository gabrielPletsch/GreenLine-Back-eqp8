package app.auditing;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "audit_log", schema = "greenline")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Getter @Setter
    @Column(nullable = false)
    private String operation;

    @Getter @Setter
    @Column(nullable = false,updatable = false)
    private LocalDateTime createDate;

    @Getter @Setter
    @CreatedBy
    @Column(nullable = false,updatable = false)
    private Long createdBy;


    public Audit(String operation, Long userId) {
        this.operation = operation;
        this.createdBy = userId;
    }
}