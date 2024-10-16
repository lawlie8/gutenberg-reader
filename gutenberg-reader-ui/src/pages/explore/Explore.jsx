import { Col, List, Menu, Radio, Row, Spin } from "antd";
import './explore.css'
import { FETCH_BOOKS_BY_PAGE } from "../../constants";
import axios from "axios";
import React, { forwardRef, useCallback, useEffect, useMemo, useRef, useState } from "react";
import BookItem from "../recent-boooks/BookItem";
import { useInView } from 'react-intersection-observer';
import Sider from "antd/es/layout/Sider";
import { LoadingOutlined } from "@ant-design/icons";
export default function Explore() {

  const [currentRenderedBooks, setCurrentRenderedBooks] = useState([]);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(false);
  const [type,setType] = useState(['title'])
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
      setLoading(true);

      axios.get(FETCH_BOOKS_BY_PAGE + `${page}/10`, {}).then((response) => {
        response.data.map((booksData) => {
          setCurrentRenderedBooks(oldArray => [...oldArray, booksData])
        })
        setLoading(false)
      }
      )
    } catch (err) {
      console.log(err);
    }

  }, [page]);

  const items = [
    {
      key: 'title',
      label: 'Title',
    },
    {
      key: 'author',
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
          key: 'Finnish',
          label: 'Finnish'
        },
        {
          key: 'Spanish',
          label: 'Spanish'
        },
        {
          key: 'Other',
          label: 'Other'
        }
      ]
    },
    {
      key: 'sort',
      label: 'Sort',
      children: [
        {
          key: 'aZOrdered',
          label: 'A-Z',
        },
        {
          key: 'zAOrdered',
          label: 'Z-A',
        },
        {
          key: 'dateAdded',
          label: 'Date Added',
        },
        {
          key: 'lastModified',
          label: 'Date Modified',
        },
        {
          key: 'yearOrdered',
          label: 'Year',
          children: [
            {
              key: '1996',
              label: '1996',
            },
            {
              key: '1997',
              label: '1997',
            },
            {
              key: '1998',
              label: '1998',
            },
            {
              key: '1999',
              label: '1999',
            },
            {
              key: '2000',
              label: '2001',
            },
            {
              key: '2002',
              label: '2002',
            },
            {
              key: '2003',
              label: '2003',
            },
            {
              key: '2004',
              label: '2004',
            },
            {
              key: '2005',
              label: '2005',
            },
            {
              key: '2006',
              label: '2006',
            },
            {
              key: '2007',
              label: '2007',
            },
            {
              key: '2008',
              label: '2008',
            },
            {
              key: '2009',
              label: '2009',
            },
            {
              key: '2010',
              label: '2010',
            },
            {
              key: '2011',
              label: '2011',
            },
            {
              key: '2012',
              label: '2012',
            },
            {
              key: '2013',
              label: '2013',
            },
            {
              key: '2014',
              label: '2014',
            },
            {
              key: '2015',
              label: '2015',
            },
            {
              key: '2016',
              label: '2016',
            },
            {
              key: '2017',
              label: '2017',
            },
            {
              key: '2018',
              label: '2018',
            },
            {
              key: '2019',
              label: '2019',
            },
            {
              key: '2020',
              label: '2020',
            },
            {
              key: '2021',
              label: '2021',
            },
            {
              key: '2022',
              label: '2022',
            },
            {
              key: '2023',
              label: '2023',
            },
            {
              key: '2024',
              label: '2024',
            },
          ]
        },

      ]
    }
  ]

  const onClickMenu = (e) => {
    localStorage.setItem('type',e.keyPath);
  }

  const siderStyle = {
    overflow: 'auto',
    height: '100vh',
    position: 'fixed',
    insetInlineStart: 0,
    top: 0,
    bottom: 0,
    width: '300px',
    scrollbarWidth: 'thin',
    scrollbarColor: 'unset',
  };


  return (
    <>
      <div style={globalReaderStyle} className="explore" >
        {/* <Menu mode="vertical" style={{ width: '300px', height: '100vh', overflow: "auto", position: 'fixed', backgroundColor: 'white', padding: '0px', margin: '0px', left: '0px', top: '45px', height: 'calc(100% - 40px)', boxShadow: '1px 0px 10px rgb(131, 82, 82)' }} items={items} onClick={onClickMenu} /> */}
        <div id="className" className="explore-main">
          <Row>
            {
              currentRenderedBooks.map((item, index) => (
                <Col style={{padding:'50px'}} ref={currentRenderedBooks.length - 1 === index ? myRef : null}>
                  <BookItem id={index} key={index} title={item?.title} bookId={item?.bookId} description={item?.description} width={'240px'} />
                </Col>

              ))
            }
          </Row>
        </div>
      </div>

      <div style={{display:loading ? 'block' : 'none', position: 'fixed', top: '50%', left: '50%',transform:'translate(-50%.-50%)' }}>
        <Spin indicator={<LoadingOutlined spin />} size="large" />
      </div>
    </>

  );
}