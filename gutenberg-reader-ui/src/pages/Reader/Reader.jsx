import { Col, List, Row, Spin } from "antd";
import './reader.css';
import instance from "../../utils/axios"
import React, { useEffect,useState } from "react";
import { FileZipOutlined, LinkOutlined, BookOutlined, LoadingOutlined } from '@ant-design/icons';
import { EPUB_BLOB_DATA_URL, EPUB_BYTE_DATA_URL, ZIP_BLOB_DATA_URL } from "../../constants";
import BookItem from "../recent-boooks/BookItem";
import { ReactReader } from 'react-reader';
export default function Reader() {

    let bookId = localStorage.getItem("bookId");
    let title = localStorage.getItem("title");
    let author = localStorage.getItem("author");

    const [location, setLocation] = useState(0);
    const [epubData, setEpubData] = useState(null);
    const [loading, setLoading] = useState(false);


    const [windowSize, setWindowSize] = React.useState(getWindowSize());

    React.useEffect(() => {
        function handleWindowResize() {
            setWindowSize(getWindowSize());
        }

        window.addEventListener('resize', handleWindowResize);

        return () => {
            window.removeEventListener('resize', handleWindowResize);
        };
    }, []);

    function getWindowSize() {
        const { innerWidth, innerHeight } = window;
        return { innerWidth, innerHeight };
    }


    useEffect(() => {
        try {
            setLoading(true)
            instance.get(EPUB_BYTE_DATA_URL + `${bookId}`, { responseType: 'blob' })
                .then((res) => {
                    setEpubData(res.data);
                });
            setLoading(false)
        } catch (e) {
            console.log(e)
        }

    }, [bookId]);

    function checkGlobalDisplay() {
        if (bookId === null || windowSize.innerWidth < 500) {
            return "none"
        }
        else {
            return "block"
        }
    }

    function manageFileEpubDownload(bookId) {
        instance.get(EPUB_BLOB_DATA_URL + `${bookId}`, { responseType: 'blob' })
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
        instance.get(ZIP_BLOB_DATA_URL + `${bookId}`, { responseType: 'blob' })
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


    return (
        <>

            <div style={{ display: bookId !== null ? 'none' : 'block', left: '50%', top: '50%', position: 'absolute', transform: 'translate(-50%,-50%)' }}>
                Not Reading Anything
            </div>

            <div className="global">
                <Row className="global-row">
                    <Col span={windowSize.innerWidth < 500 ? 0 : 5} style={{ borderRight: '1px solid black', padding: '50px', backgroundColor: '#2c2c2c' }}>
                        <List style={{ position: 'relative', left: '50%', top: '0%', display: 'inline-block', transform: 'translate(-50%,-0%)', overflow: 'none' }}>
                            <List.Item style={{ border: 'none' }}>
                                <BookItem bookId={bookId} title={title} author={author} width={'240px'}></BookItem>
                            </List.Item>
                            <List.Item style={{ border: 'none', color: 'white' }}>
                                Title  : {title}
                            </List.Item>
                            <List.Item style={{ border: 'none', color: 'white' }}>
                                Author  : {author}
                            </List.Item>
                            <List.Item style={{ border: 'none', color: 'white' }}>
                                <span>Download Epub</span>
                                <BookOutlined onClick={() => manageFileEpubDownload(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} />
                            </List.Item>
                            <List.Item style={{ border: 'none', color: 'white' }}>
                                <span>Download Zip</span>
                                <FileZipOutlined onClick={() => manageFileZipDownload(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} />
                            </List.Item>
                            <List.Item style={{ border: 'none', color: 'white' }}>
                                <span>Go To Source</span>
                                <LinkOutlined onClick={() => manageLinkOutSource(bookId)} style={{ fontSize: '20px', color: 'white', paddingLeft: '2px' }} />
                            </List.Item>
                        </List>
                    </Col>
                    <Col span={windowSize.innerWidth < 500 ? 24 : 19} style={{ overflowY: 'hidden' }} >
                        <div className="pages">
                            <ReactReader
                                url={window.URL.createObjectURL(new Blob([epubData]))}
                                location={location}
                                locationChanged={(epubcfi) => setLocation(epubcfi)}
                                epubInitOptions={{
                                    openAs: 'epub',
                                }}
                            />

                        </div>



                        {/* <div className="book-nav">
                            <div className="book-nav-buttons">
                                <Row >
                                    <Col span={4}>
                                        <Tooltip title="Previous Page">
                                            <StepBackwardOutlined style={{ fontSize: '20px', padding: '3px', color: 'white' }} />
                                        </Tooltip>

                                    </Col>
                                    <Col span={16}>
                                        <span style={{ fontSize: '15px', padding: '3px', color: 'white', position: 'absolute', left: '50%', transform: 'translate(-50%)' }} >
                                            121 / 232
                                        </span>
                                    </Col>
                                    <Col span={4}>
                                        <Tooltip title="Next Page">
                                            <StepForwardOutlined style={{ fontSize: '20px', padding: '3px', color: 'white', float: 'right' }} />
                                        </Tooltip>
                                    </Col>
                                </Row>
                            </div>
                        </div> */}




                    </Col>
                </Row>

            </div>
            <div style={{ display: loading ? 'block' : 'none', position: 'fixed', top: '50%', left: '50%', transform: 'translate(-50%.-50%)',zIndex:'11' }}>
                <Spin indicator={<LoadingOutlined spin />} size="large" />
            </div>
        </>

    );
}