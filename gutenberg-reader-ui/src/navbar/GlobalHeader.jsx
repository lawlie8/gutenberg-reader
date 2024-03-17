import { Flex, Menu, notification } from 'antd';
import { Link, NavLink, Route, useNavigate,Router, Routes } from 'react-router-dom';
import React from "react";
import Reader from '../pages/Reader';
import GutenBergRoute from '../security/GutenBergRoute';
import {LogoutOutlined} from '@ant-design/icons';
import { END_GET_AUTH_LOGIN, END_GET_AUTH_LOGOUT } from '../constants';
import axios from 'axios';
export default function GlobalHeader() {

    const navigate = useNavigate();

    const headerStyle = {
        textAlign: 'center',
        color: '#fff',
        position: 'absolute',
        top: '0px',
        left: '0px',
        margin: '0px',
        width: 'calc(100%)',
    };

    const logoutIconStyle = {
        color:"white",
        zIndex:"111",
        position:"fixed",
        right:"0px",
        top:"14px",
        paddingRight:"10px"
    };


    const items = getNavBarItems().map((item, index) => ({
        key: `${item.path}`,
        label: `${item.name}`,
    }));

    var checkNavigation = ()=>{
        if(window.location.pathname == "/"){
            return "none";
        }else{
            return "flex";
        }
    }

    function logutCurrentUser(){
        axios.post(END_GET_AUTH_LOGOUT,{},{
            headers: {
                'Content-Type': 'multipart/form-data'
              }
        }).then((response) => {
            if(response.status == 200){
                navigate("/");
                notification.success("user logged out")
            }
        }).catch(()=>{     
            notification.error("Logout Failed");
        });
    }
    

    return (
        <div style={{display : checkNavigation()}}>
            <Menu
                theme='dark'
                mode='horizontal'
                defaultSelectedKeys={['1']}
                items={items}
                style={headerStyle}
                onClick={({key}) => navigate(key)}
                >
            </Menu>
            <LogoutOutlined style={logoutIconStyle} onClick={logutCurrentUser} />
        </div>
    );

    
}


function getNavBarItems() {
    const navbarList = [
        {
            name:'Reader',
            path:'/reader'
        },
        {
            name:'Recent Books',
            path:'/recent-books'
        },{
            name:'Categories',
            path:'/categories'
        },
        ]
    return navbarList;
}