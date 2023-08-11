import { Select, Option } from "@material-tailwind/react";
import { useQrStore } from '../../store.js';
import { useEffect, useState } from "react";
import axios from "axios";

export function SelectQR() {

    const { selectedBus, onStation, offStation ,setSelectedBus, setOnStation, setOffStation } = useQrStore();

    console.log(selectedBus)
    console.log(onStation)
    console.log(offStation)


    useEffect(() => {
        getDefaultBusList();
    }, []);
    
    function getDefaultBusList() {
    
        axios
        .get("/api/companies/1/buses")
        .then((res) => {
            console.log(res.data);
            
        })
        .catch((err) => {
            console.log(err);
        });
    }

    function getOnStationList() {

        axios
        .get("/api/companies/1/buses/1")  // 1번 버스 정보 조회  (1번 버스의 정류장과 노선 정보가 다 오려나 ?)
        .then((res) => {
            console.log(res.data);
            // setOnStation(res.data.stations);
        })
        .catch((err) => {
            console.log(err);
        });
    }




    return (
    <div className="flex w-72 flex-col gap-6">
        <Select color="blue" label="탑승할 버스" className="shadow-lg bg-white">
        <Option>1호차</Option>
        <Option>2호차</Option>
        <Option>3호차</Option>
        <Option>4호차</Option>
        </Select>
        <Select color="purple" label="탑승할 정류장" className="shadow-lg bg-white">
        <Option>4-1 공항입구 GS25 앞</Option>
        <Option>4-2 운남동 에브리마트 앞</Option>
        <Option>4-3 흑석사거리</Option>
        <Option>4-4 호남파이프 건너편</Option>
        </Select>
        <Select color="teal" label="하자지 선택" className="shadow-lg bg-white">
        <Option>{offStation}</Option>
        <Option>_</Option>
        </Select>
    </div>
);
}