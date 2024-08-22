package com.lawlie8.gutenbergreader.repositories;

import com.lawlie8.gutenbergreader.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BooksRepo extends JpaRepository<Books,Long> {

    @Query(value = "SELECT * FROM books where upload_date = current_date()",nativeQuery = true)
    public List<Books> getAllBooksForToday(Date date);


    @Query(value = "SELECT * FROM books where `title` LIKE :term LIMIT 5",nativeQuery = true)
    public List<Books> searchBooksByName( @Param("term") String term);

}
