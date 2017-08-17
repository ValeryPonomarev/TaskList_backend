package models.base;

import com.avaje.ebean.Model;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lordp on 17.08.2017.
 */
@MappedSuperclass
public abstract class BaseModel extends Model {
    @Column(name="create_time", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Formats.DateTime(pattern = "yyyy-MM-dd hh:mm:ss")
    public Date createTime;

    @Column(name = "update_time", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Formats.DateTime(pattern = "yyyy-MM-dd hh:mm:ss")
    public Date updateTime;

    @Column(name = "uuid", nullable = false, columnDefinition = "DEFAULT NEWID()")
    public String uuid;

    @PrePersist
    void createdAt() {
        this.createTime = this.updateTime = new Date();
        this.uuid = UUID.randomUUID().toString();
    }

    @PreUpdate
    void updatedAt() {
        this.updateTime = new Date();
    }
}
