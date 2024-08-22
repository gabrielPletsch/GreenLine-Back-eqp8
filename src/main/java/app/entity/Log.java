package app.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private

    Long id;

    @Column(name = "log_time")
    private LocalDateTime logTime;

    @Column(name = "level")
    private String level;

    @Column(name = "logger")
    private String logger;

    @Column (name = "message")
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
