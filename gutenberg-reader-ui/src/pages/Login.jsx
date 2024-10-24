import { Button, Flex, Form, Input, message, notification, Tag, Row, Col } from "antd";
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import './pages.css'
import { useNavigate } from "react-router-dom";
import React, { useCallback, useEffect, useState } from "react";
import instance from '../utils/axios';
import { CHECK_IF_USER_IS_VALID, END_GET_AUTH_LOGIN, END_GET_AUTH_SIGNUP } from '../constants';
import Alert from "antd/es/alert/Alert";
import { Buffer } from 'buffer';

const globalReaderStyle = {
    height: 'calc(100%)',
    width: 'calc(100%)',
    backgroundColor: '#ecd8b1',
    left: '0px',
    top: '0px',
    zIndex: '-1',
    position: 'absolute',
}



export default function Login() {

    const navigate = useNavigate();
    const [formFlag, setFormFlag] = React.useState(false);
    const [signupOptionButton, setSignupButtonOption] = React.useState(true);
    const [userNameCheckTerm, setUserNameCheckTerm] = React.useState('')
    const [lockedUserNameCheck, setLockedUserNameCheck] = React.useState('')
    const [isUserValid, setIsUserValid] = React.useState()


    //Authenticate User
    function AuthenticateUser(values) {
        setFormFlag(false);
        instance.post(END_GET_AUTH_LOGIN, {
            username: values.username,
            password: values.password
        }, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then((response) => {
            if (response.status == 200) {
                navigate("/recent-books");
            }

        }).catch(() => {
            setFormFlag(true)
        });

    }

    //Create New User
    function CreateNewUser(signupValues) {
        instance.post(END_GET_AUTH_SIGNUP, {
            username: signupValues.signupUsername,
            password: Buffer.from(signupValues.signupPassword).toString('base64')
        }, {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then((response) => {
            if (response.status == 200) {
                navigate("/");
                setSignupButtonOption(true)
                notification.success({
                    message: "User Created",
                    duration: 1,
                    description: "sucessfully signed up",
                    style: { width: '250px' }
                })
            }
        }).catch(() => {
            notification.error({
                message: "Error",
                duration: 1,
                description: "Something Went Wrong",
                style: { width: '250px' }
            })
        });

    }

    //Check User id is available
    React.useEffect(() => {
        const delayDebounceFn = setTimeout(() => {
            if (userNameCheckTerm.length > 0) {
                try {
                    instance.get(CHECK_IF_USER_IS_VALID + `${userNameCheckTerm}`).then(res => {
                        setIsUserValid(res.data);
                        setLockedUserNameCheck(userNameCheckTerm)
                        validateUsername()
                    })
                } catch {
                    console.log("Cannot Check User Id")
                }
            }
        }, 500)

        return () => clearTimeout(delayDebounceFn)
    }, [userNameCheckTerm])

    function toggleSignUpoptions() {
        setSignupButtonOption(!signupOptionButton)
    }

    var loginUpFormDisplay = () => {
        if (!signupOptionButton) {
            return "none";
        } else {
            return "block";
        }
    }

    var signUpFormDisplay = () => {
        if (signupOptionButton) {
            return "none";
        } else {
            return "block";
        }
    }

    function checkForUser(value) {
        if (value.nativeEvent !== undefined) {
            {
                setUserNameCheckTerm(value.target.value)
            }
        }
    }

    function validateUsername() {
        return isUserValid && userNameCheckTerm.length !== 0 && lockedUserNameCheck === userNameCheckTerm
    }



    return (
        <div style={globalReaderStyle}>
            {formFlag &&
                <Alert className="global-alert" message="Error"
                    description="Login failed"
                    type="error"
                    closable
                    showIcon
                />}

            <img src={require("./../utils/assets/ivy-png.png")} style={{ height: "calc(99%)", zIndex: '-10', position: 'fixed' }}></img>

            <div className="main-login" style={{ display: loginUpFormDisplay() }}>
                <img className="login-logo-icon" src={require("./../utils/assets/book.png")} height="40px" width="40px" alt="book" style={{ paddingBottom: "5px" }}></img>
                <h3 className="login-form-headline">Login with User</h3>
                <Form style={{ paddingLeft: '20px', width: "250px" }} name="normal-login" className="login-form" initialValues={{ remember: true }} onFinish={AuthenticateUser}>
                    <Form.Item style={{ paddingTop: "10px" }} name="username" rules={[{ required: true, message: 'Please input your Username!' }]} >
                        <Input style={{ backgroundColor: "#15334e", color: "white" }} prefix={<UserOutlined className="site-form-item-icon" />} />
                    </Form.Item>

                    <Form.Item name="password" rules={[{ required: true, message: 'Please input your Password!' }]}>
                        <Input style={{ backgroundColor: "#15334e", color: "white" }} prefix={<LockOutlined className="site-form-item-icon" />} type="password" />
                    </Form.Item>
                    <Row >
                        <Col span={8}>
                            <Button className="toggle-login-options" onClick={() => toggleSignUpoptions()} type="primary">Switch To Sign-Up</Button>
                        </Col>
                        <Col span={8} offset={8}>
                            <Form.Item style={{ paddingTop: "0px" }} label={formFlag}>
                                <Button type="primary" htmlType="submit" className="login-form-button" style={{ float: "right", backgroundColor: "#15334e" }}>Log in</Button>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
            </div>

            <div className="main-signup" style={{ display: signUpFormDisplay() }}>
                <img className="login-logo-icon" src={require("./../utils/assets/register.png")} height="40px" width="40px" alt="book" style={{ paddingBottom: "5px" }}></img>
                <h3 className="login-form-headline">Register User</h3>
                <Form style={{ paddingLeft: '20px', width: "250px" }} name="normal-signup" className="login-form" initialValues={{ remember: true }} onFinish={CreateNewUser}>
                    <Form.Item style={{ paddingTop: "10px" }} name="signupUsername" rules={[{ required: true, message: 'Select Username!' }]} >
                        <Input style={{ backgroundColor: "#15334e", color: "white" }} prefix={<UserOutlined className="site-form-item-icon" />} onInput={(e) => checkForUser(e)} />
                    </Form.Item>
                    <Form.Item name="signupPassword" rules={[{ required: true, message: 'Please input your Password!' }]}>
                        <Input style={{ backgroundColor: "#15334e", color: "white" }} prefix={<LockOutlined className="site-form-item-icon" />} type="password" />
                    </Form.Item>
                    <Row>
                        <Col span={8}>
                            <Button className="toggle-login-options" onClick={() => toggleSignUpoptions()} type="primary">Switch To Login</Button>
                        </Col>
                        <Col span={8} offset={8}>
                            <Form.Item style={{ paddingTop: "0px" }} label={formFlag}>
                                <Button type="primary" disabled={validateUsername()} htmlType="submit" className="login-form-button" style={{ float: "right", backgroundColor: "#15334e" }}>Signup</Button>
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
                {
                    validateUsername() === true ?
                        <div className="signup-notification">User <Tag color="magenta" bordered={false} style={{ margin: '0px' }}>{userNameCheckTerm}</Tag> Already Exists </div> :
                        userNameCheckTerm !== "" ?
                            <div className="signup-notification">User <Tag color="green" bordered={false} style={{ margin: '0px' }}>{userNameCheckTerm}</Tag> is Available </div> : ""
                }

            </div>
        </div>
    );
}
