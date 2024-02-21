import { Layout } from "antd";
import React from "react";
import { Link } from "react-router-dom";

export default function GutenbergErrorElement(){

    const errorPageStyle = {
        left:'50%',
        top:'50%',
        position:'absolute',
        transform:'translate(-50%,-50%)'
    }

    return (
        <Layout>
         <div style={errorPageStyle}><Link href="/reader">404 Not Found</Link></div>
        </Layout>
);
}