import axios from "axios";
import { useEffect, useState } from "react";
import { GET_DAILY_RSS_BOOK_DATA } from "../../constants";
import { recentBooksInterface } from "../RecentBookInterface";
import { notification } from "antd";
import BookItem from "./BookItem";

export default  function RecentBooks() {

    const [data, setDailyRssBookData] = useState("recentBooksInterface");

    const globalReaderStyle = {
        height: 'calc(100%)',
        width: 'calc(100%)',
        backgroundColor: '#ecd8b1',
        left: '0px',
        top: '0px',
        zIndex: '-1',
        position: 'absolute',

    }

    const tesint = {
        backgroundColor: '#ecd8b1',
        left: '50%',
        top: '50%',
        zIndex: '-1',
        position: 'absolute',
        transform: 'translate(-50%,-50%)'

    }


    useEffect(()=>{
            axios.get(GET_DAILY_RSS_BOOK_DATA)
            .then((response)=>{
                setDailyRssBookData(response.data.channel);
            }).catch(()=>{
                notification.error("error")
            })
    },[])
    

    return (
        <div style={globalReaderStyle} className="recent-books" >
            <div style={tesint}>
                <div className="book-item">
                    <BookItem data={data}></BookItem>
                </div>
            </div>
        </div>
    );
}