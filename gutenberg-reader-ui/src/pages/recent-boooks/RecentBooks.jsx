import axios from "axios";
import { useEffect, useState } from "react";
import { GET_DAILY_RSS_BOOK_DATA } from "../../constants";


export default function RecentBooks() {

    const [data, setData] = useState([]);

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

    useEffect(() => {
        const requestAPI = async () => {
            try {
                const response = await axios.get(GET_DAILY_RSS_BOOK_DATA,{});
                setData(response.data);
            } catch (err) {
                console.log(err);
            }
        };
    requestAPI();
    },[]);

    return (
        <div style={globalReaderStyle} className="recent-books">
            <div style={tesint}>
                <div className="book-item">
                </div>
            </div>
        </div>
    );
}