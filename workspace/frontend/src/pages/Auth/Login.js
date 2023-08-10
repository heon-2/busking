import { LoginForm } from "../../components/Auth/LoginForm";
import { Scene } from "../../components/Scene";

export function Login() {
    return (
        <>
        <Scene/>
        <div className="">
        </div>
        <div className="main">
            <div className="loginspace">
                <div className="login-container">
            <LoginForm />
            </div>
            </div>
        </div>
        </>
    )
}