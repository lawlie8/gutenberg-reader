import { Button, Form,Input } from "antd";
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import './pages.css'
import { useNavigate } from "react-router-dom";

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
    const formFlag = '';
    const authenticateUser = (values) => {
        let username = values.username;
        let password = values.password;
        console.log("dsd");
      };


    return(
        <div style={globalReaderStyle}>
            <img src={ require("./../utils/assets/ivy-png.png")} style={{height:"calc(99%)"}}></img>
        <div className="main-login">
            <img src="https://icon-library.com/images/cabbage-icon/cabbage-icon-5.jpg" height="40px" width="40px" alt="kobi" style={{paddingBottom:"5px"}}></img>
            <Form style={{paddingLeft:'20px',width:"250px"}} name="normal-login" className="login-form"  initialValues={{ remember: true }} onFinish={authenticateUser}>
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
