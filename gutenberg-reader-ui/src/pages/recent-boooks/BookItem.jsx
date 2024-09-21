import { Avatar, Card, Col, Layout, List, Row } from 'antd';
import './recent-books.css'
import { useNavigate, createSearchParams, Link } from "react-router-dom";
import { Content, Footer } from 'antd/es/layout/layout';
import { Image } from 'antd';
import { MoreOutlined, ReadOutlined, FileZipOutlined, LinkOutlined, BookOutlined } from '@ant-design/icons';
import Meta from 'antd/es/card/Meta';
import React from 'react';
import axios from 'axios';
import { IMAGE_BLOB_DATA_URL, ZIP_BLOB_DATA_URL, EPUB_BLOB_DATA_URL } from '../../constants';
export default function BookItem({ title, bookId, author, uploadDate,width }) {

    const [downloadWindowFlag, setDownLoadWindowFlag] = React.useState(false);
    const [imageData, setImageData] = React.useState(null);

    const navigate = useNavigate();
    
    FetechImageData(bookId)

    


    function parseDescription(title) {
        let Author = title?.split("by ")[1];
        if (Author === undefined) {
            return "Un-known"
        } else if (Author.length > 20) {
            return Author?.substring(0, 20)
        }
        return Author?.substring(0, Author.length - 1)
    }


    function openDownloadOptionPop(link) {
        setDownLoadWindowFlag(!downloadWindowFlag)
    }


    var checkNavigation = () => {
        if (!downloadWindowFlag) {
            return "none";
        } else {
            return "block";
        }
    }


    function manageReadLocal(bookId) {
        let author = parseDescription(title);
        localStorage.setItem('bookId',`${bookId}`);
        localStorage.setItem('title',`${title}`);
        localStorage.setItem('author',`${author}`);

        navigate({
            pathname: "/reader",
            search: `?${createSearchParams({
                bookId: `${bookId}`,
                title: `${title}`,
                author: author
            })}`
        });
    }

    function manageFileEpubDownload(bookId) {
        axios.get(EPUB_BLOB_DATA_URL + `${bookId}`, { responseType: 'blob' })
            .then(res => {
                const url = window.URL.createObjectURL(new Blob([res.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `asset_${bookId}.epub`);
                document.body.appendChild(link);
                link.click();
            })
    }

    function manageFileZipDownload(bookId) {
        axios.get(ZIP_BLOB_DATA_URL + `${bookId}`, { responseType: 'blob' })
            .then(res => {
                const url = window.URL.createObjectURL(new Blob([res.data]));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', `asset_${bookId}.zip`);
                document.body.appendChild(link);
                link.click();
            })
    }

    function manageLinkOutSource(bookId) {
        window.location = `https://www.gutenberg.org/ebooks/${bookId}`;
    }

    function FetechImageData(bookId) {
            React.useEffect(() => {
                if(bookId!==null){
                axios.get(IMAGE_BLOB_DATA_URL + `${bookId}`, { responseType: 'blob' })
                    .then(res => {
                        setImageData(window.URL.createObjectURL(new Blob([res.data])))
                    })
    }}, [bookId]);
        } 
    



return (
    <div className="book-item" >
        <div className="download-book-icon">
            <MoreOutlined onClick={() => openDownloadOptionPop(bookId)} style={{ fontSize: '18px', color: 'black' }} />
            <div className="download-book-item-option-list" style={{ display: checkNavigation() }}>
                <List>
                    <List.Item><ReadOutlined onClick={() => manageReadLocal(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} /></List.Item>
                    <List.Item><BookOutlined onClick={() => manageFileEpubDownload(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} /></List.Item>
                    <List.Item><FileZipOutlined onClick={() => manageFileZipDownload(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} /></List.Item>
                    <List.Item><LinkOutlined onClick={() => manageLinkOutSource(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} /></List.Item>
                </List>
            </div>
        </div>



        <Card
            hoverable
            loading={false}
            style={{width: width}}
            cover={<img id={bookId} height="300px" width="200px" alt={title} src={imageData} />}
        >
            <Meta title={title} description={parseDescription(title)} />
        </Card>
    </div>

)
}