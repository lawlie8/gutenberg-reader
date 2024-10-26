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
export default function BookItem({ title, bookId, author, uploadDate, width }) {

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
        localStorage.setItem('bookId', `${bookId}`);
        localStorage.setItem('title', `${title}`);
        localStorage.setItem('author', `${author}`);

        navigate({
            pathname: "/reader",
            search: `?${createSearchParams({
                bookId: `${bookId}`,
                title: `${title}`,
                author: author
            })}`
        });
    }


    function FetechImageData(bookId) {
        React.useEffect(() => {
            if (bookId !== null) {
                try {
                    axios.get(IMAGE_BLOB_DATA_URL + `${bookId}`, { responseType: 'blob' })
                        .then(res => {
                            setImageData(window.URL.createObjectURL(new Blob([res.data])))
                        })
                } catch (e) {
                    console.log("eror");
                    
                }

            }
        }, [bookId]);
    }




    return (
        <div className="book-item" onClick={() => manageReadLocal(bookId)}>
            <Card
                hoverable
                loading={false}
                style={{ width: width }}
                cover={<img id={bookId} height="300px" width="200px" alt={title} src={imageData} />}
            >
                <Meta title={title} description={parseDescription(title)} />
            </Card>
        </div>

    )
}