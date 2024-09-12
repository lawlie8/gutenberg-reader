import { Flex, Menu, notification, Input, List, Avatar } from 'antd';
import { Link, NavLink, Route, useNavigate, Router, Routes } from 'react-router-dom';
import React from "react";
import { LogoutOutlined } from '@ant-design/icons';
import {END_GET_AUTH_LOGOUT, NAVBAR_SEARCH_API } from '../constants';
import axios from 'axios';
import './navbar.css'
export default function GlobalHeader() {

    const navigate = useNavigate();
    const [searchData, setSearchData] = React.useState([]);
    const { Search } = Input;

    const headerStyle = {
        textAlign: 'center',
        color: '#fff',
        position: 'fixed',
        top: '0px',
        left: '0px',
        margin: '0px',
        width: 'calc(100%)',
        zIndex: '23'
    };

    const logoutIconStyle = {
        color: "white",
        zIndex: "111",
        position: "fixed",
        right: "0px",
        top: "14px",
        paddingRight: "10px"
    };


    const items = getNavBarItems().map((item, index) => ({
        key: `${item.path}`,
        label: `${item.name}`,
    }));

    var checkNavigation = () => {
        if (window.location.pathname == "/") {
            return "none";
        } else {
            return "flex";
        }
    }

    function onSearch(value) {
        if (value.nativeEvent !== undefined) {
            value = value.nativeEvent.explicitOriginalTarget.value;
        }

        if (value.length > 0) {
            try {
                axios.get(NAVBAR_SEARCH_API + `${value}`).then(res => {
                    setSearchData(res.data);
                })
            } catch {

            }
        }

    }

    const [searchTerm, setSearchTerm] = React.useState('')

    React.useEffect(() => {
        const delayDebounceFn = setTimeout(() => {
            onSearch(searchTerm);
        }, 1000)

        return () => clearTimeout(delayDebounceFn)
    }, [searchTerm])

    function logutCurrentUser() {
        axios.post(END_GET_AUTH_LOGOUT, {}, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then((response) => {
            if (response.status == 200) {
                navigate("/");
                notification.success({
                    message: "Logged Out",
                    duration: 1,
                    description: "sucessfully logged out",
                    style: { width: '250px' }
                })
            }
        }).catch(() => {
            notification.error(({
                message: "Error While Logging Out",
                duration: 3
            }));
        });
    }

    var checkSearchVisibility = () => {
        if (searchData.length == 0) {
            return "none";
        } else {
            return "block";
        }
    }

    const escFunction = React.useCallback((event) => {
        if (event.key === "Escape") {
            setSearchData([])
        }
    }, []);

    React.useEffect(() => {
        document.addEventListener("keydown", escFunction, false);
        return () => {
            document.removeEventListener("keydown", escFunction, false);
        };
    }, [escFunction]);

    function setKeyTerm(value) {
        if (value.nativeEvent !== undefined) {
            value = value.nativeEvent.explicitOriginalTarget.value;
            setSearchTerm(value)
        }
    }

    return (
        <div style={{ display: checkNavigation() }}>
            <Menu
                theme='dark'
                mode='horizontal'
                defaultSelectedKeys={['1']}
                items={items}
                style={headerStyle}
                onClick={({ key }) => navigate(key)}
            >
            </Menu>
            <Search className='navbar-search' placeholder="Search" onSearch={onSearch} enterButton onKeyUp={(e) => setKeyTerm(e)} />
            <div className='global-search-list' style={{ display: checkSearchVisibility() }}>
                <List>
                    {
                        searchData.map((item, index) => (
                            <List.Item className='global-search-list-item' key={index}>
                                <img></img> {item.title}
                            </List.Item>
                        ))
                    }
                </List>

            </div>
            <LogoutOutlined style={logoutIconStyle} onClick={logutCurrentUser} />
        </div>
    );


}


function getNavBarItems() {
    const navbarList = [
        {
            name: 'Reader',
            path: '/reader'
        },
        {
            name: 'Recent Books',
            path: '/recent-books'
        }, {
            name: 'Explore',
            path: '/explore'
        },
    ]
    return navbarList;
}