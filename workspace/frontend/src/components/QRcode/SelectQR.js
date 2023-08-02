import { Select, Option } from "@material-tailwind/react";

export function SelectQR() {
    return (
    <div className="flex w-72 flex-col gap-6">
        <Select color="blue" label="탑승할 버스">
        <Option>1호차</Option>
        <Option>2호차</Option>
        <Option>3호차</Option>
        <Option>4호차</Option>
        </Select>
        <Select color="purple" label="탑승할 정류장">
        <Option>4-1 공항입구 GS25 앞</Option>
        <Option>4-2 운남동 에브리마트 앞</Option>
        <Option>4-3 흑석사거리</Option>
        <Option>4-4 호남파이프 건너편</Option>
        </Select>
        <Select color="teal" label="하자지 선택">
        <Option>SSAFY</Option>
        <Option>집으로 다시가기</Option>
        </Select>
    </div>
);
}