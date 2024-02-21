import { Route,Routes } from "react-router-dom";
import Reader from "../pages/Reader";
import Categories from "../pages/Categories";
import RecentBooks from "../pages/RecentBooks";

export default function GutenBergRoute(path = {path}){
    console.log(path);
    return(
        <Routes>
            <Route path="/reader" element={<Reader />}></Route>
            <Route path="/categories" element={<Categories />}></Route>
            <Route path="/recent-books" element={<RecentBooks />}></Route>

        </Routes>
    );
}