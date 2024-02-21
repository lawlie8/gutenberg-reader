export default function Categories(){

    const globalReaderStyle = {
        height : 'calc(100%)',
        width : 'calc(100%)',
        backgroundColor : '#ecd8b1',
        left:'0px',
        top:'0px',
        zIndex:'-1',
        position: 'absolute',


    }
    const tesint = {

        backgroundColor : '#ecd8b1',
        left:'50%',
        top:'50%',
        zIndex:'-1',
        position: 'absolute',
        transform:'translate(-50%,-50%)'

    }
    return(
        <div style={globalReaderStyle} className="categories">
                    <div style={tesint}>
        <h1>Categories</h1>

        </div>
        </div>
    );
}