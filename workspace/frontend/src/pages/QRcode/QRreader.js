import React from 'react'
import { Card, CardBody, CardHeader } from '@material-tailwind/react'
import { QRcodeScanner } from '../../components/QRcode/QRcodeScanner'

export function QRreader() {
    return (
        <div className="h-screen overflow-y-auto bg-blue-50 flex flex-col items-center justify-center">
          <div className="flex header text-xl font-bold h-16 md:h-20 items-center justify-center">
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