import Layout from "antd/es/layout/layout";
import React, { useState, useEffect, version } from 'react';
import { Footer } from "antd/es/layout/layout";

const footerStyle = {
    textAlign: 'center',
    color: '#fff',
    backgroundColor: '#001529',
    position:'absolute',
    bottom:'0px',
    left:'0px',
    margin:'0px',
    paddingTop:'5px',
    width: 'calc(100%)',
    height:'20px'
  };
  
export default function GlobalFooter() {


    const [data, setData] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);
  
    useEffect(() => {
      const fetchData = async () => {
        setIsLoading(true);
        try {
          const response = await fetch('http://localhost:8090/application/properties/version');
          if (!response.ok) {
            throw new Error('Failed to fetch data');
          }
          const jsonData = await response.json();
          setData(jsonData);
        } catch (error) {
          setError(error);
        } finally {
          setIsLoading(false);
        }
      };
  
      fetchData();
    }, []);

    return (
            <Layout>
                <Footer style={footerStyle}>
                    Created with ❤ by <a style={{color:'white', textDecoration: 'underline'}} href="https://github.com/lawlie8">lawlie8</a>
                    <span style={{float:"right",position:"absolute",right:"1%",fontWeight:"bold"}}>v.{data}</span>
                </Footer>
            </Layout>
    );
}