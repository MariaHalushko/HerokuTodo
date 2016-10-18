package com.todo.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mary on 17.10.16.
 */


@Entity
@Table(schema = "public",name="users")
public class User {


    @Id
    @Column(name = "id")
    @SequenceGenerator(name="auto_id_user",
            sequenceName="auto_id_user",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="auto_id_user")
    private Long id;


    @Column(name = "firstName")
    private String firstName;


    @Column(name = "lastName")
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Fetch(FetchMode.SELECT)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTask(List<Task> tasks) {
        this.tasks = tasks;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +'}';
    }
}
