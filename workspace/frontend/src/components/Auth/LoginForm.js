import {
Card,
Input,
Checkbox,
Button,
Typography,
} from "@material-tailwind/react";
import { useState } from "react";
import axios from "axios";
import { useUserStore } from '../../store.js'
import { queries } from "@testing-library/react";
import { useNavigate } from 'react-router-dom';

export function LoginForm() {

const [ username, setName ] = useState('');
const [password, setPassword] = useState('');

const { user, accessToken, setUser, setAccessToken, setRefreshToken } = useUserStore()
const navigate = useNavigate()


return (
    <Card color="transparent" shadow={false}>
    <Typography variant="h4" color="blue-gray">
        Sign In
    </Typography>
    <form className="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96">
        <div className="mb-4 flex flex-col gap-6">
        <Input size="lg" label="Name" value={username} onChange={(e) => setName(e.target.value)}/>
        <Input type="password" size="lg" label="Password" value={password} onChange={(e) => setPassword(e.target.value)}/>
        </div>
        <Checkbox
        label={
            <Typography
            variant="small"
            color="gray"
            className="flex items-center font-normal"
            >
            I agree the
            <a
                href="#"
                className="font-medium transition-colors hover:text-blue-500"
            >
                &nbsp;Terms and Conditions
            </a>
            </Typography>
        }
        containerProps={{ className: "-ml-2.5" }}
        />
        <Button className="mt-6" onClick={() => onLogin({username, password, setUser, setAccessToken, setRefreshToken, navigate})}>
        Login
        </Button>
        {/* <Button className="mt-6" onClick={() => onLogout({name, password, setUser})}>
        LogOut
        </Button> */}
        <Typography color="gray" className="mt-4 text-center font-normal">
        Already have an account?{" "}
        <a
            href="#"
            className="font-medium text-blue-500 transition-colors hover:text-blue-700"
        >
            Sign Up
        </a>
        </Typography>
    </form>
    </Card>
);
}

async function onLogin({username, password, setUser, setAccessToken, setRefreshToken, navigate}) {

    try {
        const response = await axios.post('/api/auth/login', {
            username: username,
            password: password,
        },
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
        console.log(response)
        setAccessToken(response.data.accessToken)
        setRefreshToken(response.data.refreshToken)
        localStorage.setItem('accessToken', response.data.accessToken)
        localStorage.setItem('refreshToken', response.data.refreshToken)
        
        const accessToken = localStorage.getItem('accessToken')

        const response2 = await axios.get('/api/users', {
            headers: {
                'Accept': 'application/json',
                'Authorization' : `Bearer ${accessToken}`,
            }
        })
        console.log(response2.data)
        await setUser(response2.data)
        if (response2.data.role === 'EMPLOYEE') {
            navigate('/map')
        } else if (response2.data.role === 'COMPANY_ADMIN') {
            navigate('/admin')
        } else {
            navigate('/knightselect')
        }

    } catch(error) {
        console.log(error)
    }
}


// function makeUser() {
//     const accessToken = localStorage.getItem('accessToken')
//     axios.post('/api/users', {
//         username: "ssafy",
//         password: "ssafy",
//         companyId: 1,
//         phoneNumber: "010-0000-0000",
//         email: "kkk@gmail.com",
//         realName: "kkk"
//     },
//     {
//         headers: {
//             'Accept': 'application/json',
//             'Authorization': `Bearer ${accessToken}`
//         }
//     })
//     .then((response) => {
//         console.log(response)
//     })
//     .catch((error) => {
//         console.log(error)
//     })
// }

// function onUser(){
//    const accessToken = localStorage.getItem('accessToken') 
//    console.log(accessToken)
//    axios.get('/api/users',
// //    {
// //     description: "adsdadasaffasfwf111sd",
// //     lng: 1,
// //     lat: 1,
// //     busNum: 3,
// //     companyId: 1
// //    }, 
//    {headers: {
//     'Accept': 'application/json',
//     'Authorization' : `Bearer ${accessToken}`,
//    }}
// //    {queries:{
// //     'Authorization': `Bearer ${accessToken}`
// //    }}
//    )
//    .then((response) => {
//     console.log(response)
//    })
//    .catch((error) => {
//     console.log(error)
//    })
// }