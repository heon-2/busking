import {
    Card,
    Input,
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
    const [ password, setPassword ] = useState('');
    
    const { user, accessToken, setUser, setAccessToken, setRefreshToken } = useUserStore()
    const navigate = useNavigate()

    
    
    return (
        <div className="flex flex-col items-center justify-center h-screen bg-blue-50">
            <div>
                <img src="/ssabus_logo.png" alt="logo" className="h-60"/>
            </div>
        <Card color="transparent" shadow={true} className="p-8 bg-white">
            <Typography variant="h4" color="blue-gray">
                로그인
            </Typography>
            <Typography color="gray" className="mt-1 font-normal">
                교육생의 정보를 입력해주세요.
            </Typography>
            <form className="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96">
                <div className="mb-4 flex flex-col gap-6">
                    <Input size="lg" label="아이디" value={username} onChange={(e) => setName(e.target.value)}/>
                    <Input type="password" size="lg" label="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)}/>
                </div>
                <Button className="mt-6" onClick={() => onLogin({ username, password, setUser, setAccessToken, setRefreshToken, navigate })}>
                    Login
                </Button>
            </form>
        </Card>
        </div>
        );
    }
    
    async function onLogin({username, password, setUser, setAccessToken, setRefreshToken, navigate}) {
    
        try {
            const response = await axios.post('https://i9c108.p.ssafy.io/api/auth/login', {
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
    
            const response2 = await axios.get('https://i9c108.p.ssafy.io/api/users', {
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