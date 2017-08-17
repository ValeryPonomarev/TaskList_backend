package models;

import com.avaje.ebean.Finder;
import com.avaje.ebean.annotation.Index;
import models.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lordp on 17.08.2017.
 */
@Entity(name="task")
public class Task extends BaseModel {
    @Id
    public int id;

    @Index
    @Column(name = "number", nullable = false)
    public String number;

    @Column(name = "description")
    public String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public TastStatus status;

    @Column(name = "date_end")
    public Date dateEnd;

    public Task() {
    }

    public static Finder<Integer, Task> find = new Finder<Integer, Task>(Task.class);

    @PrePersist
    void onCreate() {
        status = TastStatus.OPEN;
    }

    public enum TastStatus {
        OPEN, CLOSE
    }
}
