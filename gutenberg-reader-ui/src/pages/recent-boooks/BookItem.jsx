export default function BookItem({data}){

    console.log(data);

    function parseImageData(link){
        var bookId = link.split("/").pop()
        var index = link.indexOf(".org")
        return link.substring(0, index + bookId.length) + `/cache/epub/${bookId}/pg${bookId}.cover.medium.jpg`;
    }

    return (
        <div className="book-item">
            <img src={parseImageData(data.link)}></img>
            <span>{data.title}</span>
        </div>
    )
}