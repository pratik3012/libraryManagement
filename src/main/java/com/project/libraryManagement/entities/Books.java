package com.project.libraryManagement.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@ToString
public class Books {

    @Id
    public Long bookId;
    public String bookName;
    public int stockAvailable;
}
