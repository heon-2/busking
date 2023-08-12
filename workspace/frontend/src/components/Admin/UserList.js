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

import { useEffect } from "react";

const TABLE_HEAD = ["이름", "권한", "휴대폰 번호", "이메일" ,"Edit"];




export function UserList() {
  const [TABLE_ROWS, setTABLE_ROWS] = useState([]);
  const [open, setOpen] = React.useState(false);
  const [editopen, setEditOpen] = React.useState(false);

  const handleOpen = () => { setOpen(!open); };
  const editModalOpen = () => { setEditOpen(!editopen); };

  const [ username, setName ] = useState('');
  const [ password, setPassword ] = useState('');
  const [ phoneNumber, setPhoneNumber ] = useState('');
  const [ email, setEmail ] = useState('');
  const [ realName, setRealName ] = useState('');
  const [ totalPageNum, setTotalPageNum ] = useState(0);
  const [ active, setActive ] = useState(1);

  useEffect(() => {
    getDefaultUserList();
  }, []);
  
  function getDefaultUserList() {

    axios
      .get("/api/users/list/1")
      .then((res) => {
        console.log(res.data);
        setTABLE_ROWS(res.data.list);
        setTotalPageNum(res.data.totalPageNum);
        console.log(res.data.totalPageNum);
      })
      .catch((err) => {
        console.log(err);
      });
  }


  function Pagination({ totalPageNum, active, setActive }) {
  
    const getItemProps = (index) =>
      ({
        variant: active === index ? "filled" : "text",
        color: active === index ? "blue" : "blue-gray",
        onClick: () => {
          setActive(index);
          getUserList(index); // 페이지 버튼을 클릭하면 해당 페이지로 데이터를 가져옴
        },
      });



    function getUserList(param) {

      axios
        .get("/api/users/list/" + param)
        .then((res) => {
          console.log(res.data);
          setTABLE_ROWS(res.data.list);
          console.log(param);
          setActive(param);
          console.log(active);
        })
        .catch((err) => {
          console.log(err);
        });
    }


    const paginationItems = Array.from({ length: totalPageNum }, (_, i) => (
      <IconButton key={i+1} {...getItemProps(i+1)} >{i+1}</IconButton>
    ))
    return (
      <div className="flex items-center gap-4">
        <div className="flex items-center gap-2">
          { paginationItems }
        </div>
      </div>
    );
  }


  return (
      <div className="flex flex-col flex-grow p-4 pl-4 pr-8">
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
            className="grid mb-4 h-28 place-items-center"
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
          <Card className="w-full h-full mt-4 overflow-y-auto">
      <table className="w-full text-left table-auto min-w-max">
        <thead>
          <tr>
            {TABLE_HEAD.map((head) => (
              <th key={head} className="p-4 border-b border-blue-gray-100 bg-blue-gray-50">
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
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-5 h-5" onClick={editModalOpen}>
                <path strokeLinecap="round" strokeLinejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                </svg>
                </Typography>
              </td>
            </tr>
          ))}
          <Dialog
        size="xs"
        open={editopen}
        handler={editModalOpen}
        className="bg-transparent shadow-none"
      >
        <Card className="mx-auto w-full max-w-[24rem]">
          <CardHeader
            variant="gradient"
            color="blue"
            className="grid mb-4 h-28 place-items-center"
          >
            <Typography variant="h3" color="white">
              교육생 정보 수정
            </Typography>
          </CardHeader>
          <CardBody className="flex flex-col gap-4">
            <Input label="비밀번호" value={password} size="lg" onChange={(e) => setPassword(e.target.value)}/>
            <Input label="이름" value={realName} size="lg" onChange={(e) => setRealName(e.target.value)}/>
            <Input label="휴대폰 번호" value={phoneNumber} size="lg" onChange={(e) => setPhoneNumber(e.target.value)} />
            <Input label="이메일" value={email} size="lg" onChange={(e) => setEmail(e.target.value)} />
          </CardBody>
          <CardFooter className="pt-0">
            <Button variant="gradient" onClick={() => {editModalOpen(); updateUser(password, realName, phoneNumber, email)}} fullWidth>
              등록
            </Button>
          </CardFooter>
        </Card>
      </Dialog>
        </tbody>
      </table>
    </Card>
    <div className="flex justify-center mt-4">
    <Pagination
          totalPageNum={totalPageNum}
          active={active}
          setActive={setActive}
        />
    </div>
      </div>
  )
}

function registerUser(username, password, realName, phoneNumber, email, getUserList) {
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
      getUserList()
  })
  .catch((error) => {
      console.log(error)
  })
}

function updateUser(password, realName, phoneNumber, email) {
  const accessToken = localStorage.getItem('accessToken')
  axios.patch('/api/users', {
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


