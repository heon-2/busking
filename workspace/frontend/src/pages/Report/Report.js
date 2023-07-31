import {
    Checkbox,
    Card,
    List,
    ListItem,
    ListItemPrefix,
    Typography,
    Button
} from "@material-tailwind/react";

export function Report() {
return (
    <div className="bg-gray-100">
        <div>
            <p>상단바 들어갈 자리</p>
            <img src="/ssabus_logo.png" alt="싸버지 logo" className="h-80 mt-10"/>
            <p className="text-3xl my-20 text-blue-400 font-bold">신고하기</p>
    <Card>
    <List>
    <ListItem className="p-0">
        <label
        htmlFor="vertical-list-1"
        className="flex w-full cursor-pointer items-center px-3 py-2"
        >
        <ListItemPrefix className="mr-3">
            <Checkbox
            id="vertical-list-1"
            ripple={false}
            className="hover:before:opacity-0"
            containerProps={{
                className: "p-0",
            }}
            />
        </ListItemPrefix>
        <Typography color="blue-gray" className="font-medium">
            난폭 운전을 합니다.
        </Typography>
        </label>
    </ListItem>
    <ListItem className="p-0">
        <label
        htmlFor="vertical-list-2"
        className="flex w-full cursor-pointer items-center px-3 py-2"
        >
        <ListItemPrefix className="mr-3">
            <Checkbox
            id="vertical-list-2"
            ripple={false}
            className="hover:before:opacity-0"
            containerProps={{
                className: "p-0",
            }}
            />
        </ListItemPrefix>
        <Typography color="blue-gray" className="font-medium">
            예정 시간보다 빨리 출발했습니다.
        </Typography>
        </label>
    </ListItem>
    <ListItem className="p-0">
        <label
        htmlFor="vertical-list-3"
        className="flex w-full cursor-pointer items-center px-3 py-2"
        >
        <ListItemPrefix className="mr-3">
            <Checkbox
            id="vertical-list-3"
            ripple={false}
            className="hover:before:opacity-0"
            containerProps={{
                className: "p-0",
            }}
            />
        </ListItemPrefix>
        <Typography color="blue-gray" className="font-medium">
            운전 중 위험한 행동을 합니다.
        </Typography>
        </label>
    </ListItem>
    <ListItem className="p-0">
        <label
        htmlFor="vertical-list-4"
        className="flex w-full cursor-pointer items-center px-3 py-2"
        >
        <ListItemPrefix className="mr-3">
            <Checkbox
            id="vertical-list-4"
            ripple={false}
            className="hover:before:opacity-0"
            containerProps={{
                className: "p-0",
            }}
            />
        </ListItemPrefix>
        <Typography color="blue-gray" className="font-medium">
            응급 상황 발생 ( 교통사고, 환자 발생 등 )
        </Typography>
        </label>
    </ListItem>
    </List>
</Card>
    <Button className="bg-red-400 mt-10">신 고 하 기</Button>
</div>
</div>
)
}