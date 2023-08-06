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

const { setUser } = useUserStore()
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
        <Button className="mt-6" onClick={() => onLogin({username, password, setUser})}>
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
    <Button onClick={() => onUser()}>fdas</Button>
    </Card>
);
}

function onLogin({username, password, setUser}) {
    console.log(username)
    console.log(password)

    if (username === '') {
        alert('아이디를 입력해주세요.')
    }
    else if (password === '') {
        alert('비밀번호를 입력해주세요.')
    }
    else {
        axios.post('/api/auth/login', {
            username: username,
            password: password,
        },
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
        )
        .then((response) => {
            console.log(response)
            localStorage.setItem('accessToken', response.data.accessToken)
            localStorage.setItem('refreshToken', response.data.refreshToken)
        })
        .catch((error) => {
            console.log(error)
        })
    }
}

// function onLogout({name, password, setUser}) {
//     console.log(name)
//     axios.post('http://localhost:3001/logout', {
//         name: name,
//         password: password
//     })
//     .then((response) => {
//         console.log(response)
//         localStorage.removeItem('accessToken')
//         setUser(response.data.user)
//     })
//     .catch((error) => {
//         console.log(error)
//     })
// }


// async function onLogin({ username, password, setUser }) {
//   try {
//     const headers = {
//       'Accept': 'application/json',
//       'Content-Type': 'application/x-www-form-urlencoded'
//     };

//     const requestBody = new URLSearchParams();
//     requestBody.append('username', username);
//     requestBody.append('password', password);

//     const response = await axios.post('/api/auth/login', requestBody, { headers });
//     console.log(response.data);

//     localStorage.setItem('accessToken', response.data.accessToken);
//     setUser(response.data.user);
//   } catch (error) {
//     console.log(error);
//     // ... 에러 처리
//   }
// }

function onUser(){
   const accessToken = localStorage.getItem('accessToken') 
   console.log(accessToken)
   axios.get('/api/users',
//    {
//     description: "adsdadasaffasfwf111sd",
//     lng: 1,
//     lat: 1,
//     busNum: 3,
//     companyId: 1
//    }, 
   {headers: {
    'Accept': 'application/json',
    'Authorization' : `Bearer ${accessToken}`,
   }}
//    {queries:{
//     'Authorization': `Bearer ${accessToken}`
//    }}
   )
   .then((response) => {
    console.log(response)
   })
   .catch((error) => {
    console.log(error)
   })
}

function makeUser() {
    const accessToken = localStorage.getItem('accessToken')
    axios.post('/api/users', {
        username: "ssafy",
        password: "ssafy",
        companyId: 1,
        phoneNumber: "010-0000-0000",
        email: "kkk@gmail.com",
        realName: "kkk"
    },
    {
        headers: {
            'Accept': 'application/json',
            'Authorization': `Bearer ${accessToken}`
        }
    })
    .then((response) => {
        console.log(response)
    })
    .catch((error) => {
        console.log(error)
    })
}