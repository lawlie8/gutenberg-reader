import axios from "axios";
import { useEffect, useState } from "react";
import { GET_DAILY_RSS_BOOK_DATA } from "../../constants";
import './recent-books.css';
import BookItem from "./BookItem";
import { Col, List, Row } from "antd";
export default function RecentBooks() {

    const [data, setData] = useState([]);


    const tesint = {
        backgroundColor: '#ecd8b1',
        left: '50%',
        top: '50%',
        zIndex: '-1',
        position: 'absolute',
        transform: 'translate(-50%,-50%)'

    }

    useEffect(() => {
        const requestAPI = async () => {
            try {
                const response = await axios.get(GET_DAILY_RSS_BOOK_DATA, {});
                setData(response.data);
                console.log(data);

            } catch (err) {
                console.log(err);
            }
        };
        requestAPI();
    }, []);

    return (
        <div className="recent-books">
            <div className="recent-books-container">
                <Row>
                    {
                        data.map((item, index) => (
                            <Col span={6}>
                                <BookItem id={index} key={index} title={item?.title} bookId={item?.bookId} description={item?.description} />
                            </Col>
                        ))
                    }
                    </Row>

            </div>
        </div>
    );
}