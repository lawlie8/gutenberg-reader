import { Menu } from 'antd';


export default function GlobalHeader() {

    const headerStyle = {
        textAlign: 'center',
        color: '#fff',
        position: 'absolute',
        top: '0px',
        left: '0px',
        margin: '0px',
        width: 'calc(100%)',
    };

    const items = getNavBarItems().map((item, index) => ({
        key: index + 1,
        label: `${item}`,
    }));

    return (
            <Menu
                theme='dark'
                mode='horizontal'
                defaultSelectedKeys={['1']}
                items={items}
                style={headerStyle}>
            </Menu>
    );
}

function getNavBarItems() {
    const navbarList = ['Reader','Recent Books', 'Categories'];
    return navbarList;
}