package com.lawlie8.gutenbergreader.repositories;

import com.lawlie8.gutenbergreader.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepo extends JpaRepository<Books,Long> {
}
