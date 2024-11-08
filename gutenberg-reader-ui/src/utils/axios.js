import axios from "axios";
import { BASE_URL } from "../constants";
import { notification } from "antd";

function fetchBaseUrl(){
    console.log('Checking url');
    axios.get("https://www.redirectcheck.org/api/check?url=http%3A%2F%2Freader-api.lawlie8.org").then((response) =>{
        return response.data.data.final_result.final_url
    }).catch((error)=> {
        console.log(error);
    })
}


const instance = axios.create({
    baseURL: BASE_URL,
    headers: new Headers({
        "ngrok-skip-browser-warning": "69420",
      }),
})

instance.interceptors.response.use(
    response => {
        if(response.status === 302 || response.status === 403 || response.status === 404){
            notification.error({
                message:"Error",
                duration:1,
                description:"Something Went Wrong",
                style: { width: '250px' }
            })
        window.location.href = "/"
        }

    return response
    },error =>{
        console.log(error);
        
        if(error.response.status === 403){
            notification.error({
            message:error.response.code,
            duration:1,
            description:"Login Required",
            style: { width: '250px' }
        })
        const delayFucnt = setTimeout(() => {
            window.location.href = "/"
        }, 1000);

    }

    return error

    }
)



export default instance;
