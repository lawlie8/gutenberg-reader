package com.lawlie8.gutenbergreader.reader.DTO;

public interface Constants {
    String IMAGE_TYPE = "picture";
    String ZIP_TYPE = "zip";
    String EPUB_TYPE = "epub";

    String BOOK_TYPE_GUTENBERG_UPLOAD = "gutenberg_upload";
    String GUTENBERG_ZIP_PREFIX = "https://www.gutenberg.org/cache/epub/%s/pg%s-h.zip";
    String GUTENBERG_EBOOK_PREFIX = "https://www.gutenberg.org/ebooks/%s.epub3.images";
    String GUTENBERG_IMAGE_PREFIX = "https://www.gutenberg.org/cache/epub/%s/pg%s.cover.medium.jpg";
}
