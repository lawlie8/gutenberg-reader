import { Button, Form,Input, message, notification } from "antd";
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import './pages.css'
import { useNavigate } from "react-router-dom";
import React, { useCallback, useEffect, useState } from "react";
import axios from 'axios';
import {END_GET_AUTH_LOGIN} from '../constants';
import Alert from "antd/es/alert/Alert";
import useNotification from "antd/es/notification/useNotification";
import NotificationElement from "../utils/NotificationElement";

const globalReaderStyle = {
    height : 'calc(100%)',
    width : 'calc(100%)',
    backgroundColor : '#ecd8b1',
    left:'0px',
    top:'0px',
    zIndex:'-1',
    position: 'absolute',
}



export default function Login(){

    const navigate = useNavigate();
    const [formFlag,setFormFlag] = React.useState(false);
    const [loginState,setState] = React.useState('0');
    const [api, contextHolder] = notification.useNotification();

    function AuthenticateUser(values){
        setFormFlag(false);
        axios.post(END_GET_AUTH_LOGIN,{
            username:values.username,
            password:values.password
        },{
            headers: {
                'Content-Type': 'multipart/form-data'
              }
        }).then((response) => {
            if(response.status == 200){
                navigate("/recent-books");
            }

        }).catch(()=>{     
            setFormFlag(true)
        });
           
    }



    return(
        <div style={globalReaderStyle}>
            {formFlag &&
            <Alert className="global-alert" message="Error"
            description="Login failed"
            type="error"
            closable
            showIcon
            />}
            <img src={ require("./../utils/assets/ivy-png.png")} style={{height:"calc(99%)"}}></img>
            
        <div className="main-login">
            <img className="login-logo-icon" src="https://icon-library.com/images/cabbage-icon/cabbage-icon-5.jpg" height="40px" width="40px" alt="kobi" style={{paddingBottom:"5px"}}></img>
            <Form style={{paddingLeft:'20px',width:"250px"}} name="normal-login" className="login-form"  initialValues={{ remember: true }} onFinish={AuthenticateUser}>
                <Form.Item style={{paddingTop:"10px"}} name="username" rules={[{ required: true, message: 'Please input your Username!' }]} >
                    <Input style={{backgroundColor:"#15334e",color:"white"}} prefix={<UserOutlined className="site-form-item-icon" />}  />
                </Form.Item>
                
                <Form.Item  name="password" rules={[{ required: true, message: 'Please input your Password!' }]}>
                <Input  style={{backgroundColor:"#15334e",color:"white"}} prefix={<LockOutlined className="site-form-item-icon" />} type="password" />
                </Form.Item>
                
                <Form.Item style={{paddingTop:"0px"}} label={formFlag}>
                <Button type="primary" htmlType="submit" className="login-form-button" style={{float:"right",backgroundColor:"#15334e"}}>Log in</Button>
                </Form.Item>
            </Form>
        </div>
        </div>
    );
    

}
