package com.project.libraryManagement.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class UsersWithBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long subscriptionId;
    public String userName;

    @NotNull(message = "Book Id is mandatory!")
    public Long bookId;

}
