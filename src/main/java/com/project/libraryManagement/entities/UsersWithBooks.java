package com.project.libraryManagement.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
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
    public int subscriptionId;
    public String userName;

    @NotBlank(message = "Book Id cannot be blank!")
    public long bookId;

}
