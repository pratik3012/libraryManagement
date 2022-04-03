package com.project.libraryManagement.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@ToString
public class Books {

    @Id
    public long bookId;
    public String bookName;
    public int stockAvailable;
}
