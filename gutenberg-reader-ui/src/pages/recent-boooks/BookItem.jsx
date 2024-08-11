import { Avatar, Card, Col, Layout, List, Row } from 'antd';
import './recent-books.css'
import { Content, Footer } from 'antd/es/layout/layout';
import { Image } from 'antd';
import { MoreOutlined,ReadOutlined,FileZipOutlined,LinkOutlined, BookOutlined } from '@ant-design/icons';
import Meta from 'antd/es/card/Meta';
import React from 'react';
export default function BookItem({ title, link, description }) {

    const [downloadWindowFlag,setDownLoadWindowFlag] = React.useState(false);

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
        if(Author === undefined){
            return "Un-known"
        }else if(Author.length > 20){
            return Author?.substring(0,20)
        }
        return Author?.substring(0,Author.length - 1)
    }



    function openDownloadOptionPop(link){
        
        setDownLoadWindowFlag(!downloadWindowFlag)
    }


    var checkNavigation = ()=>{
        if(!downloadWindowFlag){
            return "none";
        }else{
            return "block";
        }
    }


    function manageReadLocal(link){
        console.log("Reading Locally");
        
    }

    function manageReadPlainHtml(link){
        console.log("Reading Plain HTML Only");
        
    }

    function manageFileZipDownload(link){
        var bookId = link?.split("/").pop().split("\"")[0]
        var index = link?.split(".org")[0] + ".org";
        window.location = index.substring(1, index.length) + `/cache/epub/` + bookId + `/pg${bookId}` + `-h.zip`;
    }

    function manageLinkOutSource(link){
        link = link.replaceAll("\"","");
        window.location = link;
    }

//onClick={() => navigateToBookUrl(link)}
    return (
        <div className="book-item" >
            <div className="download-book-icon"  >
                <MoreOutlined onClick={()=> openDownloadOptionPop(link)} style={{fontSize: '18px',color:'black'}}/>
                <div className="download-book-item-option-list" style={{display:checkNavigation()}}>
                    <List>
                        <List.Item><ReadOutlined onClick={()=> manageReadLocal(link)} style={{fontSize: '20px',color:'white',paddingLeft:'2px'}} /></List.Item>
                        <List.Item><BookOutlined onClick={()=> manageReadPlainHtml(link)} style={{fontSize: '20px',color:'white',paddingLeft:'2px'}} /></List.Item>
                        <List.Item><FileZipOutlined onClick={()=> manageFileZipDownload(link)} style={{fontSize: '20px',color:'white',paddingLeft:'2px'}} /></List.Item>
                        <List.Item><LinkOutlined onClick={()=> manageLinkOutSource(link)}  style={{fontSize: '20px',color:'white',paddingLeft:'2px'}} /></List.Item>
                    </List>
                </div>
            </div>
            
  

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