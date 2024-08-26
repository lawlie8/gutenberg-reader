import { Route,Routes } from "react-router-dom";
import Reader from "../pages/Reader";
import RecentBooks from "../pages/recent-boooks/RecentBooks";
import Login from "../pages/Login";
import Explore from "../pages/explore/Explore";
export default function GutenBergRoute(path = {path}){
    return(
        <Routes>
            <Route path="/reader" element={<Reader />}></Route>
            <Route path="/explore" element={<Explore />}></Route>
            <Route path="/recent-books" element={<RecentBooks />}></Route>
            <Route path="/" element={<Login />}></Route>

        </Routes>
    );
}