import React, { useState } from "react";
import axios from "axios";
import {
  Card,
  Typography,
  Button,
  Dialog,
  CardHeader,
  CardBody,
  CardFooter,
  Input,
  IconButton,
} from "@material-tailwind/react";
import {
  ArrowRightIcon,
  ArrowLeftIcon,
} from "@heroicons/react/24/outline";

import { useEffect } from "react";

const TABLE_HEAD = ["이름", "권한", "휴대폰 번호", "이메일" ,"Edit"];






export function UserList() {
  const [TABLE_ROWS, setTABLE_ROWS] = useState([]);
  const [open, setOpen] = React.useState(false);

  const handleOpen = () => { setOpen(!open); };

  const [ username, setName ] = useState('');
  const [ password, setPassword ] = useState('');
  const [ phoneNumber, setPhoneNumber ] = useState('');
  const [ email, setEmail ] = useState('');
  const [ realName, setRealName ] = useState('');
  

  useEffect(() => {
    getUserList();
  }, []);
  
  function getUserList() {
    axios
      .get("/api/users/list/1")
      .then((res) => {
        console.log(res.data);
        setTABLE_ROWS(res.data.list);
      })
      .catch((err) => {
        console.log(err);
      });
  }

  function Pagination() {
    const [active, setActive] = React.useState(1);
  
    const getItemProps = (index) =>
      ({
        variant: active === index ? "filled" : "text",
        color: active === index ? "blue" : "blue-gray",
        onClick: () => setActive(index),
      });
  
    const next = () => {
      if (active === 5) return;
  
      setActive(active + 1);
    };
  
    const prev = () => {
      if (active === 1) return;
  
      setActive(active - 1);
    };
    return (
      <div className="flex items-center gap-4">
        <Button
          variant="text"
          color="blue-gray"
          className="flex items-center gap-2"
          onClick={prev}
          disabled={active === 1}
        >
          <ArrowLeftIcon strokeWidth={2} className="h-4 w-4" /> Previous
        </Button>
        <div className="flex items-center gap-2">
          <IconButton {...getItemProps(1)}>1</IconButton>
          <IconButton {...getItemProps(2)}>2</IconButton>
          <IconButton {...getItemProps(3)}>3</IconButton>
          <IconButton {...getItemProps(4)}>4</IconButton>
          <IconButton {...getItemProps(5)}>5</IconButton>
        </div>
        <Button
          variant="text"
          color="blue-gray"
          className="flex items-center gap-2"
          onClick={next}
          disabled={active === 5}
        >
          Next
          <ArrowRightIcon strokeWidth={2} className="h-4 w-4" />
        </Button>
      </div>
    );
  }


  return (
      <div className="flex flex-col flex-grow pl-4 pr-8 mt-2 p-4">
        <Button onClick={handleOpen} variant="gradient" className="self-end">
        교육생 등록
      </Button>
      <Dialog
        size="xs"
        open={open}
        handler={handleOpen}
        className="bg-transparent shadow-none"
      >
        <Card className="mx-auto w-full max-w-[24rem]">
          <CardHeader
            variant="gradient"
            color="blue"
            className="mb-4 grid h-28 place-items-center"
          >
            <Typography variant="h3" color="white">
              교육생 등록
            </Typography>
          </CardHeader>
          <CardBody className="flex flex-col gap-4">
            <Input label="아이디" value={username} size="lg" onChange={(e) => setName(e.target.value)} />
            <Input label="비밀번호" value={password} size="lg" onChange={(e) => setPassword(e.target.value)}/>
            <Input label="이름" value={realName} size="lg" onChange={(e) => setRealName(e.target.value)}/>
            <Input label="휴대폰 번호" value={phoneNumber} size="lg" onChange={(e) => setPhoneNumber(e.target.value)} />
            <Input label="이메일" value={email} size="lg" onChange={(e) => setEmail(e.target.value)} />
          </CardBody>
          <CardFooter className="pt-0">
            <Button variant="gradient" onClick={() => {handleOpen(); registerUser(username, password, realName, phoneNumber, email)}} fullWidth>
              등록
            </Button>
          </CardFooter>
        </Card>
      </Dialog>
          <Card className="h-full w-full overflow-scroll mt-4">
      <table className="w-full min-w-max table-auto text-left">
        <thead>
          <tr>
            {TABLE_HEAD.map((head) => (
              <th key={head} className="border-b border-blue-gray-100 bg-blue-gray-50 p-4">
                <Typography
                  variant="small"
                  color="blue-gray"
                  className="font-normal leading-none opacity-70"
                >
                  {head}
                </Typography>
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {TABLE_ROWS.map(({ realName, role, phoneNumber, email }, index) => (
            <tr key={realName} className="even:bg-blue-gray-50/50">
              <td className="p-4">
                <Typography variant="small" color="blue-gray" className="font-normal">
                  {realName}
                </Typography>
              </td>
              <td className="p-4">
                <Typography variant="small" color="blue-gray" className="font-normal">
                  {role}
                </Typography>
              </td>
              <td className="p-4">
                <Typography variant="small" color="blue-gray" className="font-normal">
                  {phoneNumber}
                </Typography>
              </td>
              <td className="p-4">
                <Typography variant="small" color="blue-gray" className="font-normal">
                  {email}
                </Typography>
              </td>
              <td className="p-4">
                <Typography as="a" href="#" variant="small" color="blue" className="font-medium">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5">
                <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                </svg>
                </Typography>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </Card>
    <div className="flex justify-center mt-4">
    <Pagination />
    </div>
      </div>
  )
}

function registerUser(username, password, realName, phoneNumber, email) {
  const accessToken = localStorage.getItem('accessToken')
  axios.post('/api/users', {
      username: username,
      password: password,
      companyId: 1,
      phoneNumber: phoneNumber,
      email: email,
      realName: realName,
      role: "EMPLOYEE",
  },
  {
      headers: {
          'Accept': 'application/json',
          'Authorization': `Bearer ${accessToken}`
      }
  })
  .then((response) => {
      console.log(response)
  })
  .catch((error) => {
      console.log(error)
  })
}


