import {Navigate, Outlet} from "react-router-dom";

type ProtectedRoutesProps = {
    user: string | undefined
}

function ProtectedRoutes(props: ProtectedRoutesProps) {

    const isProcessing = props.user === undefined
    const isAuthenticated = props.user !== undefined && props.user !== ''

    if (isProcessing) {
        return <h1>Loading...</h1>
    }

    return isAuthenticated ? <Outlet/> : <Navigate to="/"/>
}

export default ProtectedRoutes