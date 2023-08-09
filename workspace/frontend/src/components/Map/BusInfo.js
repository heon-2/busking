export function BusInfo(props) {
    return (
        <div className="h-[15vh]">
            <p className="text-[#344A82] text-2xl text-start font-semibold pt-5 pl-5">SSAFY {props.index}호차</p>
            <p className="text-md text-start pt-3 pl-5">현재 버스 탑승 인원 : 25 / 45 명</p>
        </div>
    )
}