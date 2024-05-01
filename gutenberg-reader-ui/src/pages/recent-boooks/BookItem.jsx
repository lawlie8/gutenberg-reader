import { Card, Col, Layout, Row } from 'antd';
import './recent-books.css'
import { Content, Footer } from 'antd/es/layout/layout';
import { Image } from 'antd';
import Meta from 'antd/es/card/Meta';
export default function BookItem({ title, link, description }) {

    function parseImageData(link) {
        var bookId = link?.split("/").pop().split("\"")[0]
        var index = link?.split(".org")[0] + ".org";
        return index.substring(1, index.length) + `/cache/epub/` + bookId + `/pg${bookId}` + `.cover.medium.jpg`;
    }

    function parseTitle(title){
        var titleArray = title.split(":");
        return titleArray[0].substring(1,title.length - 1)
    }

    function parseDescription(title){
        let Author = title?.split("by ")[1];
        if(Author == undefined){
            return "Un-known"
        }else if(Author.length > 20){
            return Author?.substring(0,20)
        }
        return Author?.substring(0,Author.length - 1)
    }

    function navigateToBookUrl(link){
        link = link.replaceAll("\"","");
        window.location = link;
    }

    return (
        <div className="book-item" onClick={() => navigateToBookUrl(link)}>
            <Card
                hoverable
                style={{ width: 240 }}
                cover={<img height="300px" width="200px" alt={title} src={parseImageData(link)} />}
            >
                <Meta title={parseTitle(title)} description={parseDescription(title)}/>
            </Card>
        </div>
    )
}