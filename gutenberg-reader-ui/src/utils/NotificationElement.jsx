import { Alert } from "antd";

export default function NotificationElement({message,description}){

        return(
        <div className="gutenberg-notification">
             <Alert className="global-alert" message={message}
            description={description}
            type="error"
            closable
            showIcon
            />
        </div>
    )
}