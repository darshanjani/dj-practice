import React from 'react';
import DashboardTopMenu from './components/DashboardTopMenu';

const App = ({store}) => {
    return (
        <DashboardTopMenu store={store} />
    );
}

export default App;