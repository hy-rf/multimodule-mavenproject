package com.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name = "reply")
@Data
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne(optional = true)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonBackReference
    private Post post;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    //@JsonBackReference
    private Reply parentReply;

    @JsonIgnore
    @OneToMany(mappedBy = "parentReply")
    @JsonManagedReference
    private List<Reply> replies;
}
