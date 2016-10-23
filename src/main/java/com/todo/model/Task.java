package com.todo.model;

import com.google.gson.annotations.Expose;
import com.todo.model.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "public", name = "tasks")
public class Task {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "auto_id_task",
            sequenceName = "auto_id_task",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "auto_id_task")
    @Expose
    private Long id;

    @Column(name = "name")
    @Expose
    private String name;

    @Column(name = "comment")
    @Expose
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Expose
    private Status status;

    @Column(name = "startDate")
    @Expose
    private Date startDate;

    @Column(name = "estimationMinutes")
    @Expose
    private Integer estimationMinutes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            updatable = false,
            insertable = false
    )
    private User user;


    public Task() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getEstimationMinutes() {
        return estimationMinutes;
    }

    public void setEstimationMinutes(Integer estimationMinutes) {
        this.estimationMinutes = estimationMinutes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
