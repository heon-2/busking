import { Select, Option } from "@material-tailwind/react";
import { useQrStore } from '../../store.js';
import { useEffect, useState } from "react";
import axios from "axios";

export function SelectQR() {

    const { busbus, setBusbus, destinationIndex, setDestinationIndex ,selectedBus, isInBound, inBoundDeparture, inBoundDestination, outBoundDeparture, outBoundDestination ,setSelectedBus, setIsInBound , setInBoundDeparture, setInBoundDestination, setOutBoundDeparture, setOutBoundDestination } = useQrStore();

    const [busList, setBusList] = useState([]);  // 버스 호차 리스트
    const [inBoundStations, setInBoundStations] = useState([]);  // 출근 정류장 리스트
    const [outBoundStations, setOutBoundStations] = useState([]);  // 퇴근 정류장 리스트

    console.log(selectedBus)
    console.log(inBoundDeparture)
    console.log(inBoundDestination)
    console.log(outBoundDeparture)
    console.log(outBoundDestination)
    console.log(busList)
    console.log(isInBound)
    console.log(inBoundStations)
    console.log(outBoundStations)
    console.log(destinationIndex)
    console.log(busbus)



    useEffect(() => {
        getDefaultBusList();
        checkIsInBound();
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

    function checkIsInBound() {
        const now = new Date();
        const currentHour = now.getHours();

        // 오후 7시부터 오전 10시까지 isInBound를 true로 설정
        const isInBoundTime = currentHour >= 19 || currentHour < 10;
        setIsInBound(isInBoundTime);
    }


    function getInBoundStations(selectedBus) {

        axios
        .get("/api/companies/1/buses/" + selectedBus)  
        .then((res) => {
            console.log(res.data);
            const inBoundRoute = res.data.routes[0];
            const stations = inBoundRoute.stations.map(station => station.name);
                                        
            const inBoundStationsWithoutLast = stations.slice(0, -1);
            setBusbus(res.data.id);
            console.log(res.data.id);
            setInBoundStations(inBoundStationsWithoutLast);
            setInBoundDestination(stations[stations.length - 1]);
            const lastStationId = inBoundRoute.stations[inBoundRoute.stations.length - 1];
            const lastStationIndex = inBoundRoute.stations[lastStationId];
            console.log(lastStationIndex)
            setDestinationIndex(lastStationIndex);

        })
        .catch((err) => {
            console.log(err);
        });
    }

    function getOutBoundStations(selectedBus) {

        axios
        .get("/api/companies/1/buses/" + selectedBus)  // 버스 정보 조회  (버스의 정류장과 노선 정보가 다 오려나 ?)
        .then((res) => {
            console.log(res.data);
            const outBoundRoute = res.data.routes[1];
            const stations = outBoundRoute.stations.map(station => station.name);
            const outBoundStationsWithoutFirst = stations.slice(1);
            setBusbus(res.data.id);
            setOutBoundStations(outBoundStationsWithoutFirst);
            setOutBoundDeparture(stations[0]);
            const firstStationId = outBoundRoute.stations[0].id;
            console.log(outBoundDeparture)
            console.log(destinationIndex)
            // setDestinationIndex();

        })
        .catch((err) => {
            console.log(err);
        });
    }




    return (
        <div>    
        {/* 출근 - 출근할 때 하차 정류장은 무조건 SSAFY 
        퇴근 - 퇴근할 때 승차 정류장은 무조건 SSAFY */}
        {isInBound ? (
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
        </div>
        ) : (
        <div className="flex flex-col gap-6 w-72">   
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
        onChange={(e) => {setOutBoundDestination(e);
            const selectedStationIndex = outBoundStations.findIndex(station => station === e);
            setDestinationIndex(selectedStationIndex);
        console.log(outBoundDestination);}}>
        {outBoundStations.map((stationName, index) => (
        <Option key={index} value={stationName}>
        {stationName}
        </Option>
        ))}
        </Select>
        </div>
        )}
        </div>
    );
}