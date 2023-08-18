import { useQrStore } from "../../store"
import { useEffect } from "react";
export function BusInfo(props) {
    const { currentPeople } = useQrStore();

    useEffect(() => {
        // store에 담겨있는 currentPeople이 변할 때마다 렌더링
    }, [currentPeople]);
    return (
        <div className="h-[15vh]">
            <p className="text-[#344A82] text-2xl text-start font-semibold pt-5 pl-5">SSAFY {props.index}호차</p>
            <p className="text-md text-start pt-3 pl-5">현재 버스 탑승 인원 : {currentPeople[props.index-1]}명 / 45 명</p>
        </div>
    )
}