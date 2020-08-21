package com.developia.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String isbn;
    private String name;
    private String author;
    private BigDecimal price;
    private String description;
    private Integer pageSize;
    private String imageName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public BigDecimal getRating() {

        BigDecimal totalRating = BigDecimal.ZERO;
        for (Review review : reviews)
            totalRating = totalRating.add(review.getRating());

        if (reviews.size() == 0) return null;

        return totalRating.divide(BigDecimal.valueOf(reviews.size()));
    }
}
