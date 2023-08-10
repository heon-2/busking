import { LoginForm } from "../../components/Auth/LoginForm";
import { Scene } from "../../components/Scene";

export function Login() {
    return (
        <div >
        <div className="">
            <div className="bg-black" style={{ overflow: 'hidden', position: 'relative' }}>
            <Scene />
            </div>
            <div className="">
            </div>
            <div className="main">
                <div className="loginspace">
                    <div className="login-container">
            <LoginForm />
                    </div>
                </div>
            </div>
        </div>
        </div>
    )
}