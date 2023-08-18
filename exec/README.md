# 싸버지 포팅 메뉴얼  

## 컨테이너 이미지  

https://hub.docker.com/repository/docker/me0s/busking/general  
https://hub.docker.com/repository/docker/me0s/busking-gps-mapper/general  


## 필수 환경 변수  

**Front-server**  

- `SPRING_DATASOURCE_URL`: MySQL 서버 주소  
- `SPRING_DATASOURCE_USERNAME`: MySQL 유저 계정명  
- `SPRING_DATASOURCE_PASSWORD`: MySQL 유저 비밀번호  
- `BUSKING_LOGIN_REDIS_HOST`: SessionDB로 사용할 redis 호스트  
- `BUSKING_LOGIN_REDIS_PORT`: SessionDB로 사용할 redis 포트  
- `BUSKING_GPS_REDIS_HOST`: DrivingDB로 사용할 redis 호스트  
- `BUSKING_GPS_REDIS_PORT`: DrivingDB로 사용할 redis 포트  

**GPS-mapper**  

- `BUSKING_GPS_REDIS_HOST`: DrivingDB로 사용할 redis 호스트  
- `BUSKING_GPS_REDIS_PORT`: DrivingDB로 사용할 redis 포트  

## OpenVidu 설정  

[> 공식 메뉴얼](https://docs.openvidu.io/en/2.28.0/deployment/ce/on-premises/)  

공식 메뉴얼을 따라 OpenVidu를 설정 및 실행한다.  

프로젝트 폴더 내의 `workspace/front-server/src/main/resources/application.properties` 스프링 부트 설정 파일에서 `OPENVIDU_URL`과 `OPENVIDU_SECRET` 값을 설정해준다.  

```
OPENVIDU_URL=?
OPENVIDU_SECRET=?
```


## [osrm-backend 설정](https://github.com/Project-OSRM/osrm-backend)  

[**> osrm-backend Github 주소**](https://github.com/Project-OSRM/osrm-backend#using-docker)  
[**> geofabrik 한국 데이터**](http://download.geofabrik.de/asia/south-korea.html)  

osrm-backend Github 페이지에서 Using Docker 메뉴얼을 따라 osrm-backend를 설정한다.  

프로젝트 폴더 내의 `workspace/front-server/src/main/resources/application.properties` 스프링 부트 설정 파일에서 `busking.osrm.url`값을 osrm-backend HTTP API 서버 주소로 설정해준다.  

``` application.properties
busking.osrm.url=http://?
```

## `busking-gps-mapper` 빌드 및 설정  

프로젝트 폴더 내의 `workspace/busking-gps-mapper`로 이동한다.  

아래 커맨드를 입력해 go 프로젝트를 빌드한다.  

``` sh
$ go build . -o busking-gps-mapper
```

생성된 실행 파일을 실행하기 전에, 필수 환경 변수를 설정한다.  

- `BUSKING_GPS_REDIS_HOST`: DrivingDB로 사용할 redis 호스트  
- `BUSKING_GPS_REDIS_PORT`: DrivingDB로 사용할 redis 포트  

프로젝트 폴더 내의 `workspace/front-server/src/main/resources/application.properties` 스프링 부트 설정 파일에서 `busking.osrm.url`값을 osrm-backend HTTP API 서버 주소로 설정해준다.  

HTTP 서버를 위해 TCP 50000번 포트를 사용하므로, 50000번 포트를 사용 중인 프로세스가 있다면 종료해주어야 한다.  

``` application.properties
busking.gps-mapper.url=http://?
```

## `busking-front-server` 빌드 및 설정  

프로젝트 폴더 내의 `workspace/busking-front-server`로 이동한다.  

`src/main/resources/application.properties` 스프링 부트 설정 파일에 아래 값들이 설정되었는지 확인한다.  

``` text
OPENVIDU_URL=?
OPENVIDU_SECRET=?
busking.osrm.url=?
busking.gps-mapper.url=?
```

아래 커맨드를 입력해 프로젝트를 빌드한다.  

```sh
$ ./gradlew
```

빌드된 실행 파일 (.jar)를 실행 전 아래 환경 변수가 설정되었는지 확인한다.  

- `SPRING_DATASOURCE_URL`: MySQL 서버 주소  
- `SPRING_DATASOURCE_USERNAME`: MySQL 유저 계정명  
- `SPRING_DATASOURCE_PASSWORD`: MySQL 유저 비밀번호  
- `BUSKING_LOGIN_REDIS_HOST`: SessionDB로 사용할 redis 호스트  
- `BUSKING_LOGIN_REDIS_PORT`: SessionDB로 사용할 redis 포트  
- `BUSKING_GPS_REDIS_HOST`: DrivingDB로 사용할 redis 호스트  
- `BUSKING_GPS_REDIS_PORT`: DrivingDB로 사용할 redis 포트  

## 프로젝트 종속성  

- MySQL: 8.1.0  
- redis: 7.2  
- JDK: 17.0.7  
- Gradle: 8.2.1  
- Spring Boot: 3.1.2  
    - Project Metadata  
        - Group: org.comfort42  
        - Artifact: busking  
        - Name: busking  
        - Package Name: org.comfort42.busking  
- Intelij: 2023.1.3  
- go: 1.21  
- gin: 1.9.1  
- fx: 1.2  
- openVidu: 2.28  
- Node.js: 18  
- React: 18.2.0  
