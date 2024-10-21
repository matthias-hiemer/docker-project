import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";

function App() {
    const [username, setUsername] = useState("");

    function login() {
        const host = window.location.host === 'localhost:5173' ? 'http://localhost:8080' : window.location.origin
        window.open(host + '/oauth2/authorization/github', '_self')
        // http://localhost:8080/oauth2/authorization/github
    }

    const loadUser = () => {
        axios.get('/api/auth/me')
            .then(response => {
                const usernameResponse = response.data;
                console.log(usernameResponse)
                setUsername(usernameResponse)
            })
            .catch(error => {
                console.log(error)
            })
    }

    useEffect(() => {
        loadUser()
    }, []);

    return (
        <>
            <button onClick={login}>
                Login with GitHub
            </button>

            {username &&
                <h1>Welcome, {username}</h1>
            }
        </>
    )
}

export default App
