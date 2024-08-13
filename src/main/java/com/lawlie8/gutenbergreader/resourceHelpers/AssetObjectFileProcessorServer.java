package com.lawlie8.gutenbergreader.resourceHelpers;

import com.lawlie8.gutenbergreader.DTOs.dailyRssDtos.DailyRssBookDto;
import com.lawlie8.gutenbergreader.entities.Books;
import com.lawlie8.gutenbergreader.repositories.BooksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AssetObjectFileProcessorServer {

    @Autowired
    GutenbergResourceService gutenbergResourceService;
    private static final String BOOK_TYPE_GUTENBERG_UPLOAD = "gutenberg_upload";

    @Autowired
    BooksRepo booksRepo;

    public void processDailyDownloadDataIntoDatabase() {

        DailyRssBookDto dailyRssBookDto = gutenbergResourceService.fetchDailyRssBookTitles();
        List<Books> allBooks = new ArrayList<>();
        for(int i = 0;i<dailyRssBookDto.getChannel().getItem().size();i++){
            Books books = new Books();
            books.setTitle(dailyRssBookDto.getChannel().getItem().get(i).getTitle().replace("\"",""));
            books.setBookDescription(dailyRssBookDto.getChannel().getItem().get(i).getDescription().replace("\"",""));
            books.setBookLanguage(parseBookLanguage(dailyRssBookDto.getChannel().getItem().get(i).getDescription().replace("\"","")));
            books.setBookType(BOOK_TYPE_GUTENBERG_UPLOAD);
            books.setBookId(parseBookId(dailyRssBookDto.getChannel().getItem().get(i).getLink()));
            books.setAuthor(parseBookAuthor(dailyRssBookDto.getChannel().getItem().get(i).getTitle()));
            books.setUploadDate(new Date());
            books.setYearOfPublication(Year.now());
            allBooks.add(books);
        }

        booksRepo.saveAll(allBooks);

    }

    private String parseBookLanguage(String language){
        return language.replace("Language: ","");
    }

    private Long parseBookId(String link) {
        int lastSlashIndex = link.lastIndexOf('/');
        String numberString = link.substring(lastSlashIndex + 1);
        return Long.parseLong(numberString.replace("\"",""));
    }

    private String parseBookAuthor(String title) {
        try {
            int indexOfBy = title.indexOf("by");
            if (indexOfBy != -1) {
                return title.substring(indexOfBy + 2).trim().replace("\"","");
            } else {
                return "Unknown";
            }
        } catch (Exception e) {
            return "Unkown";
        }
    }

}
