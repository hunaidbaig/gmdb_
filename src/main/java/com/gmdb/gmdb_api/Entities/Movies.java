package com.gmdb.gmdb_api.Entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private Integer year;
    private String gener;
    private Integer runtime;

    @OneToMany(mappedBy = "movies", cascade = CascadeType.ALL)
    private List<Review> reviews;

    public Movies(String title, Integer year, String gener, Integer runtime) {
        this.title = title;
        this.year = year;
        this.gener = gener;
        this.runtime = runtime;
    }
}
