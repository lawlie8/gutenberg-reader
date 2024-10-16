import axios from "axios";
import { useEffect, useState } from "react";
import { GET_DAILY_RSS_BOOK_DATA } from "../../constants";
import './recent-books.css';
import BookItem from "./BookItem";
import { Col, Divider, Flex, List, Row, Spin } from "antd";
import { LoadingOutlined } from "@ant-design/icons";
export default function RecentBooks() {

    const [data, setData] = useState([]);
    const [loading,setLoading] = useState(false)



    useEffect(() => {
        const requestAPI = async () => {
            try {
                setLoading(true)
                const response = await axios.get(GET_DAILY_RSS_BOOK_DATA, {});
                setData(response.data);
                setLoading(false)
            } catch (err) {
                console.log(err);
            }
        };
        requestAPI();
    }, []);

    return (
        <div className="recent-books">
            <div className="recent-books-container">
                <Row gutter={24}>
                    {
                        data.map((item, index) => (
                            <Col style={{padding:'30px'}}>
                                <BookItem id={index} key={index} title={item?.title} bookId={item?.bookId} description={item?.description} width={'250px'} />
                            </Col>
                        ))
                    }
                    </Row>

            </div>
            <div style={{display:loading ? 'block' : 'none', position: 'fixed', top: '50%', left: '50%',transform:'translate(-50%.-50%)' }}>
        <Spin indicator={<LoadingOutlined spin />} size="large" />
      </div>
    </div>
    );
}