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

export function LoginForm() {

const [ name, setName ] = useState('');
const [password, setPassword] = useState('');

const { setUser } = useUserStore()
return (
    <Card color="transparent" shadow={false}>
    <Typography variant="h4" color="blue-gray">
        Sign In
    </Typography>
    <form className="mt-8 mb-2 w-80 max-w-screen-lg sm:w-96">
        <div className="mb-4 flex flex-col gap-6">
        <Input size="lg" label="Name" value={name} onChange={(e) => setName(e.target.value)}/>
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
        <Button className="mt-6" onClick={() => onLogin({name, password, setUser})}>
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

function onLogin({name, password, setUser}) {
    console.log(name)
    axios.post('http://localhost:3001/login', {
        name: name,
        password: password
    })
    .then((response) => {
        console.log(response)
        localStorage.setItem('accessToken', response.data.accessToken)
        setUser(response.data.user)
    })
    .catch((error) => {
        console.log(error)
    })
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