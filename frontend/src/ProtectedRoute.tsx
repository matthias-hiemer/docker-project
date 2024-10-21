import {Navigate, Outlet} from "react-router-dom";

type ProtectedRouteType = {
    user: string | undefined
}

function ProtectedRoute(props: ProtectedRouteType) {

    return props.user ? <Outlet /> : <Navigate to="/" />;

}

export default ProtectedRoute
