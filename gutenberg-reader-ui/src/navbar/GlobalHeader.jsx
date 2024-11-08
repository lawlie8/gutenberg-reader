import { Flex, Menu, notification, Input, List, Avatar } from 'antd';
import { Link, NavLink, Route, useNavigate, Router, Routes, useLocation, createSearchParams } from 'react-router-dom';
import React, { useRef } from "react";
import { LogoutOutlined } from '@ant-design/icons';
import { END_GET_AUTH_LOGOUT, NAVBAR_SEARCH_API,IMAGE_BLOB_DATA_URL } from '../constants';
import instance from '../utils/axios';
import './navbar.css'
export default function GlobalHeader() {

    const navigate = useNavigate();
    const [searchData, setSearchData] = React.useState([]);
    const { Search } = Input;


    const [windowSize, setWindowSize] = React.useState(getWindowSize());

    React.useEffect(() => {
      function handleWindowResize() {
        setWindowSize(getWindowSize());
      }
  
      window.addEventListener('resize', handleWindowResize);
  
      return () => {
        window.removeEventListener('resize', handleWindowResize);
      };
    }, []);

    function getWindowSize() {
        const {innerWidth, innerHeight} = window;
        return {innerWidth, innerHeight};
      }

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

    const verticalHeaderStyle = {
        textAlign: 'center',
        color: '#fff',
        position: 'fixed',
        top: '0px',
        left: '0px',
        margin: '0px',
        width: 'calc(25%)',
        height:'calc(100%)',
        zIndex: '23'
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
                instance.get(NAVBAR_SEARCH_API + `${value}`).then(res => {
                    setSearchData(res.data);
                })
            } catch {

            }
        }else {
            setSearchData([])
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
        instance.post(END_GET_AUTH_LOGOUT, {}, {
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


    function manageReadLocal(bookId) {
        localStorage.setItem('bookId',`${bookId}`);
        navigate({
            pathname: "/reader",
            search: `?${createSearchParams({
                bookId: `${bookId}`            
            })}`
        });
        setSearchTerm("");
    }

    return (
        <div style={{ display: checkNavigation() }}>
            <Menu
                theme='dark'
                mode={'horizontal'}
                defaultSelectedKeys={['1']}
                items={items}
                style={headerStyle}
                inlineCollapsed={windowSize.innerWidth < 500 ? true : false}
                onClick={({ key }) => navigate(key)}
            >
            </Menu>


            <Flex className='logout-icon-grp' gap={'middle'}>
            <Search className='navbar-search' placeholder="Search" onSearch={onSearch} enterButton onKeyUp={(e) => setKeyTerm(e)} />
            <div className='global-search-list' style={{ display: checkSearchVisibility() }}>
                <List>
                    {
                        searchData.map((item, index) => (
                            <List.Item className='global-search-list-item' style={{padding:'10px'}} key={index} onClick={() => manageReadLocal(item.bookId)}>
                                <Flex>
                                <img style={{height:'60px',width:'45px',paddingLeft:'10px',paddingRight:'10px'}} src={"data:image/png;base64," + item.imageData} ></img> {item.title}
                                </Flex>
                            </List.Item>
                        ))
                    }
                </List>

            </div>
               <LogoutOutlined onClick={logutCurrentUser} style={{color:'red'}} />
            </Flex>
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