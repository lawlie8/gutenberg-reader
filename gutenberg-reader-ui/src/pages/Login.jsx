import { Button, Form,Input } from "antd";
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import './pages.css'
import { useNavigate } from "react-router-dom";


export default function Login(){

    const navigate = useNavigate();
    const globalReaderStyle = {
        height : 'calc(100%)',
        width : 'calc(100%)',
        backgroundColor : '#ecd8b1',
        left:'0px',
        top:'0px',
        zIndex:'-1',
        position: 'absolute',
    }

    return(
        <div style={globalReaderStyle}>
            <img src={ require("./../utils/assets/ivy-png.png")} style={{height:"calc(99%)"}}></img>
        <div className="main-login">
            <img src="https://icon-library.com/images/cabbage-icon/cabbage-icon-5.jpg" height="40px" width="40px" alt="kobi" style={{paddingBottom:"5px"}} ></img>
            <Form name="normal-login" className="login-form"  initialValues={{ remember: true }} onFinish={AfterLogin}>
                <Form.Item name="username" rules={[{ required: true, message: 'Please input your Username!' }]} style={{backgroundColor:"#001529"}} >
                    <Input prefix={<UserOutlined className="site-form-item-icon" />} placeholder="Username" />
                </Form.Item>
                
                <Form.Item name="password" rules={[{ required: true, message: 'Please input your Password!' }]}>
                <Input prefix={<LockOutlined className="site-form-item-icon" />} type="password" placeholder="Password"/>
                </Form.Item>
                
                <Form.Item>
                <Button type="primary" htmlType="submit" className="login-form-button" style={{float:"right"}}>Log in</Button>
                </Form.Item>
            </Form>
        </div>
        </div>
    );
}

function AfterLogin(){
    console.log("login");
}