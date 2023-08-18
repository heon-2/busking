import React from 'react'
import { Card, CardBody, CardHeader } from '@material-tailwind/react'
import { QRcodeScanner } from '../../components/QRcode/QRcodeScanner'
import { useNavigate } from 'react-router-dom'
import { useUserStore } from '../../store.js';
import { useEffect } from 'react'


export function QRreader() {

  let navigate = useNavigate();
  const { user } = useUserStore();
  useEffect(() => { 
  if (user == null){
      navigate('/')
  }
  else if (user.role == 'DRIVER'){
      navigate('/knightselect');
  }
  }, [])


    return (
        <div className="flex flex-col items-center justify-center h-screen overflow-y-auto bg-blue-50">
          <div className="flex items-center justify-center h-16 text-xl font-bold header md:h-20">
          <p>탑승을 위한 QR 체크인</p>
          </div>
          <Card className="h-[80vh]">
          <CardHeader floated={false} className="w-[50vw] h-[80vh]">
          
          <QRcodeScanner />
          </CardHeader>
          <CardBody className="text-center">
    </CardBody>
  </Card>
</div>
    )
}