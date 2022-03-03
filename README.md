# 당신 근처의 친구 프로젝트

### 프로젝트 시작 2022-02-21

#### git clone 프로젝트
```
git clone https://github.com/CarrotFriend/backend.git
```
#### redis-server 설치 및 실행
```
sudo apt-get install redis-serve    // redis-server 설치
sudo service redis-server start     // redis-server 실행, cli모드
sudo service redis-server stop      // redis-server 종료
redis-server                        // redis-server 실행
```
#### mariadb 실행
```
sudo service mariadb start    // mariadb 실행
sudo service mariadb stop     // mariadb 종료
```

|일정 |내용                      |
|-----------|-------------------------|
|2022-02-21|프로젝트 아이디어 구상<br/>API 명세서 작성 |
|2022-02-22|API 명세서 작성<br/> spring boot setting|
|2022-02-23|domain-user 작성 <br/> swagger 3.0 적용 <br/> response 작성|
|2022-02-26|jwt기반 security setting|
|2022-02-27|redis-server 적용|
|2022-03-03|mariadb 연동 <br/> user repository & service|
|2022-03-04|user controller 간단 구현 <br/> user-category n:m 매핑|
