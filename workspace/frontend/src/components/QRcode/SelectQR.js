import { Select, Option } from "@material-tailwind/react";
import { useQrStore } from '../../store.js';
import { useEffect, useState } from "react";
import axios from "axios";

export function SelectQR() {

    const { selectedBus, inBoundDeparture, inBoundDestination, outBoundDeparture, outBoundDestination ,setSelectedBus, setInBoundDeparture, setInBoundDestination, setOutBoundDeparture, setOutBoundDestination } = useQrStore();

    const [busList, setBusList] = useState([]);  // 버스 리스트
    const [inBoundStations, setInBoundStations] = useState([]);  // 출근 정류장 리스트
    const [outBoundStations, setOutBoundStations] = useState([]);  // 퇴근 정류장 리스트

    console.log(selectedBus)
    console.log(inBoundDeparture)
    console.log(inBoundDestination)
    console.log(outBoundDeparture)
    console.log(outBoundDestination)
    console.log(busList)
    console.log(inBoundStations)
    console.log(outBoundStations)



    useEffect(() => {
        getDefaultBusList();
    }, []);
    
    function getDefaultBusList() {
    
        axios
        .get("/api/companies/1/buses")
        .then((res) => {
            console.log(res.data);
            const busNumbers = res.data.map(bus => bus.id.no);
            setBusList(busNumbers);

            
        })
        .catch((err) => {
            console.log(err);
        });
    }

    function getInBoundStations(selectedBus) {

        axios
        .get("/api/companies/1/buses/" + selectedBus)  // 1번 버스 정보 조회  (1번 버스의 정류장과 노선 정보가 다 오려나 ?)
        .then((res) => {
            console.log(res.data);
            const stations = res.data.routes.map(route => route.stations)
                                      .flat()  // 중첩 배열을 평탄화
                                        .map(station => station.name);
            const inBoundStationsWithoutLast = stations.slice(0, -1);
            setInBoundStations(inBoundStationsWithoutLast);
        })
        .catch((err) => {
            console.log(err);
        });
    }

    function getOutBoundStations(selectedBus) {

        axios
        .get("/api/companies/1/buses/" + selectedBus)  // 1번 버스 정보 조회  (1번 버스의 정류장과 노선 정보가 다 오려나 ?)
        .then((res) => {
            console.log(res.data);
            const stations = res.data.routes.map(route => route.stations)
                                      .flat()  // 중첩 배열을 평탄화
                                        .map(station => station.name);
            const outBoundStationsWithoutFirst = stations.slice(1);
            setOutBoundStations(outBoundStationsWithoutFirst);
        })
        .catch((err) => {
            console.log(err);
        });
    }




    return (
        <div>    
        {/* 출근 - 출근할 때 하차 정류장은 무조건 SSAFY */}
        <div className="flex flex-col gap-6 w-72">   
        <Select color="blue" label="탑승할 버스" className="bg-white shadow-lg"
        onChange={(e) => {
        setSelectedBus(e);
        getInBoundStations(e);
        }}>
        {busList.map((busNumber, index) => (
        <Option key={index} value={busNumber}>
        {busNumber}호차
        </Option>
        ))}
        </Select>
        <Select color="purple" label="탑승할 정류장" className="bg-white shadow-lg"
        onChange={(e) => setInBoundDeparture(e)}>
        {inBoundStations.map((stationName, index) => (
        <Option key={index} value={stationName}>
        {stationName}
        </Option>
        ))}
        </Select>
        <Select color="teal" label="하자지 선택" className="bg-white shadow-lg">
        <Option>{inBoundDestination}</Option>
        <Option></Option>
        </Select>

        {/* 퇴근 - 퇴근할 때 탑승 정류장은 무조건 SSAFY */}
        <div>
        {/* <div className="flex flex-col gap-6 w-72">   
        <Select color="blue" label="탑승할 버스" className="bg-white shadow-lg"
        onChange={(e) => {
        setSelectedBus(e);
        getOutBoundStations(e);
        }}>
        {busList.map((busNumber, index) => (
        <Option key={index} value={busNumber}>
        {busNumber}호차
        </Option>
        ))}
        </Select>
        <Select color="purple" label="탑승할 정류장" className="bg-white shadow-lg">
        <Option>{outBoundDeparture}</Option>
        <Option></Option>
        </Select>
        <Select color="purple" label="하차지 선택" className="bg-white shadow-lg"
        onChange={(e) => setOutBoundDestination(e)}>
        {OutBoundStations.map((stationName, index) => (
        <Option key={index} value={stationName}>
        {stationName}
        </Option>
        ))}
        </Select> */}

        </div>
    </div>
    </div>


);
}