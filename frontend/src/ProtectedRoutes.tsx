import {Navigate, Outlet} from "react-router-dom";

type ProtectedRouteType = {
    user: string | undefined
}

function ProtectedRoutes(props: ProtectedRouteType) {

    const isProcessing = props.user === undefined
    const isAuthenticated = props.user !== undefined && props.user !== ''

    if (isProcessing) {
        return <h3>Loading...</h3>
    }

    return isAuthenticated ? <Outlet /> : <Navigate to="/" />;
}

export default ProtectedRoutes
