import { LoginForm } from "../../components/Auth/LoginForm";

export function Login() {
    return (
        <div >
            
            <div className="relative overflow-hidden">
            
                <div aria-hidden="true" className="absolute flex transform -translate-x-1/2 -top-96 left-1/2">
                    <div className="bg-gradient-to-r from-indigo-50 to-purple-100 blur-3xl w-[25rem] h-[44rem] rotate-[-60deg] transform -translate-x-[10rem] dark:from-indigo-900/50 dark:to-purple-900"></div>
                    <div className="bg-gradient-to-tl from-blue-50 via-blue-100 to-blue-50 blur-3xl w-[90rem] h-[50rem] rounded-fulls origin-top-left -rotate-12 -translate-x-[15rem] dark:from-indigo-900/70 dark:via-indigo-900/70 dark:to-blue-900/70"></div>
                </div>

                
        
            
            <div className="">
            </div>
            <LoginForm />
            </div>
            
        </div>

    )
}