import Layout from "antd/es/layout/layout";
import React from 'react';
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
    return (
            <Layout>
                <Footer style={footerStyle}>
                    Created with ❤ by <a style={{color:'white', textDecoration: 'underline'}} href="https://github.com/lawlie8">lawlie8</a>
                </Footer>
            </Layout>
    );
}