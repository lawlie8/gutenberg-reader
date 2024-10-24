import Layout from "antd/es/layout/layout";
import React, { useState, useEffect, version } from 'react';
import { Footer } from "antd/es/layout/layout";
import { END_GET_APPLICATION_PROPERTIES } from "../constants";
import instance from "../utils/axios";

const footerStyle = {
  textAlign: 'center',
  color: '#fff',
  backgroundColor: '#001529',
  position: 'fixed',
  bottom: '0px',
  left: '0px',
  margin: '0px',
  paddingTop: '5px',
  width: 'calc(100%)',
  height: '20px',
  zIndex: '23'
};

export default function GlobalFooter() {
  const [data, setData] = useState(null);



  React.useEffect(() => {
    instance.get(END_GET_APPLICATION_PROPERTIES).then((res) => {
      setData(res.data);
    });
  }, []);

  return (
    <Layout>
      <Footer style={footerStyle}>
        Created with ❤ by <a style={{ color: 'white', textDecoration: 'underline' }} href="https://github.com/lawlie8">lawlie8</a>
        <span style={{ float: "right", position: "absolute", right: "1%", fontWeight: "bold" }}>v.{data}</span>
      </Footer>
    </Layout>
  );
}