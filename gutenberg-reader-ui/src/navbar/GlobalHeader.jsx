import { Flex, Menu } from 'antd';
import { Link, NavLink, Route, useNavigate,Router, Routes } from 'react-router-dom';
import React from "react";
import Reader from '../pages/Reader';
import GutenBergRoute from '../security/GutenBergRoute';


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

    const items = getNavBarItems().map((item, index) => ({
        key: `${item.path}`,
        label: `${item.name}`,
    }));

    return (
        <div style={{display:"flex"}}>

            <Menu
                theme='dark'
                mode='horizontal'
                defaultSelectedKeys={['1']}
                items={items}
                style={headerStyle}
                onClick={({key}) => navigate(key)}
                >
            </Menu>
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