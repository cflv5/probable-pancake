package tr.edu.yildiz.yazilimkalite.librarymanagement.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false)
    private Date startDate;
    
    @Column(nullable = false)
    private Date deadline;

    @OneToOne
    private Book bookId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BorrowingStatus status;
}
