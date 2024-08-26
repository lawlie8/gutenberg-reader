import { Col, List, Menu, Radio, Row } from "antd";
import './explore.css'
import { FETCH_BOOKS_BY_PAGE } from "../../constants";
import axios from "axios";
import React, { forwardRef, useCallback, useEffect, useMemo, useRef, useState } from "react";
import BookItem from "../recent-boooks/BookItem";
import { InView, useInView } from 'react-intersection-observer';
import VirtualAndInfiniteScroll from "./VirtualAndInfiniteScroll";
export default function Explore() {

  const [currentRenderedBooks, setCurrentRenderedBooks] = useState([]);
  const [page, setPage] = useState(0);
  const [lastElement, setLastElement] = useState(false);


  const { ref: myRef, inView } = useInView(false);


  useEffect(() => {
    if (inView) {
      setPage(page + 1)
      console.log("inView");
    }
  }, [inView])

  const globalReaderStyle = {
    height: 'calc(100%)',
    width: 'calc(100%)',
    backgroundColor: '#ecd8b1',
    left: '0px',
    top: '0px',
    zIndex: '-1',
    position: 'absolute',
  }


  
  useEffect(() => {
    try {
      axios.get(FETCH_BOOKS_BY_PAGE + `${page}/32`, {}).then((response) => {
        response.data.map((booksData) => {
          setCurrentRenderedBooks(oldArray => [...oldArray, booksData])
        })
      })
    } catch (err) {
      console.log(err);
    }
  

  }, [page]);

  const items = [
    {
      key: 'Title',
      label: 'Title',
    },
    {
      key: 'Author',
      label: 'Author'
    },
    {
      key: 'Language',
      label: 'Language',
      children: [
        {
          key: 'English',
          label: 'English'
        },
        {
          key: 'Finish',
          label: 'Finish'
        },
        {
          key: 'Spanish',
          label: 'Spanish'
        }
      ]
    }
  ]

  const onClickMenu = (e) => {
    console.log(e);

  }
  return (
    <div style={globalReaderStyle} className="explore" >
      <div className="explore-navbar-content">
        <Menu style={{ width: 'calc(15%)', position: 'fixed', backgroundColor: 'white', padding: '0px', margin: '0px', left: '0px', top: '45px', height: 'calc(100% - 40px)', boxShadow: '1px 0px 10px rgb(131, 82, 82)' }} items={items} mode="inline" onClick={onClickMenu} />
      </div>
      <div id="className" className="explore-main">
        <Row>
          {
            currentRenderedBooks.map((item, index) => (
              <Col span={6} ref={currentRenderedBooks.length - 1 === index ? myRef : null}>
                <BookItem id={index} key={index} title={item?.title} bookId={item?.bookId} description={item?.description} />
              </Col>

            ))
          }
        </Row>
      </div>
    </div>
  );
}