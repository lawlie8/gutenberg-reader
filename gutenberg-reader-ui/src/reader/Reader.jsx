export default function Reader(){

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
        <div style={globalReaderStyle} className="global-reader"></div>
    );
}