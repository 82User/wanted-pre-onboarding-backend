# wanted-pre-onboarding-backend

# 지원자의 성명
- 양진기

# 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
- AWS EC2 : 퍼블릭 IP
```
http://15.164.50.227:8081
```

### 애플리케이션 실행 방법
- Local
```
docker build
docker push
```
- AWS EC2
```
docker-compose up
```

<img src="https://github.com/82User/wanted-pre-onboarding-backend/assets/39162631/818bb910-5e2b-435b-8a75-731159690f29"  width="600">



### 엔드포인트 호출 방법
|method|endpoint|description|comments|
|------|---|---|---|
|POST|/user/signup|사용자 회원가입|
|POST|/user/login|사용자 로그인|
|POST|/boards|새로운 게시글을 생성|
|GET|/boards|게시글 목록을 조회|?sort=id,desc
|GET|/boards/{id}|특정 게시글을 조회|
|PUT|/boards/{id}|특정 게시글을 수정|
|DELETE|/boards/{id}|특정 게시글을 삭제|

# 데이터베이스 테이블 구조
- 다대일 관계 -> 사용자(1), 게시글(N)

<img src="https://github.com/82User/wanted-pre-onboarding-backend/assets/39162631/3c140ed0-83bd-42bd-af62-2d369186c8b0"  width="300">

# 구현한 API의 동작을 촬영한 데모 영상 링크
### 과제1~3

https://github.com/82User/wanted-pre-onboarding-backend/assets/39162631/2864fe2d-1c36-4e4d-bb35-82d56b57adb6

### 과제4~5

https://github.com/82User/wanted-pre-onboarding-backend/assets/39162631/83912498-6be6-447d-ad19-2b3d95080a53

### 과제6~7

https://github.com/82User/wanted-pre-onboarding-backend/assets/39162631/57035cc1-2c1f-44ef-a67e-cdf159e8f2c1

# 구현 방법 및 이유에 대한 간략한 설명

### 과제 1. 사용자 회원가입
- 이메일과 비밀번호에 대한 유효성 검사는 Bean Validation을 사용하였고
  - 예외 처리는 스프링의 @ExceptionHandler와 @RestControllerAdvice를 통해 구현하였습니다.
- 비밀번호는 스프링 시큐리티에서 제공하는 BcryptPasswordEncoder 클래스를 통해 암호화해서 데이터베이스에 저장하였습니다.

### 과제 2. 사용자 로그인
- 회원가입과 동일한 방법으로 이메일과 비밀번호에 대한 유효성 검사 기능을 구현하였습니다.
- 스프링 시큐리티를 통해 사용자가 인증되면 JWT access 토큰을 생성하여 사용자에게 반환합니다.

### 과제 3. 새로운 게시글을 생성
- JPA의 save 메서드를 사용했습니다.

### 과제 4. 게시글 목록을 조회
- JPA의 Pageable, Page 객체를 사용하여 페이지네이션 기능을 구현하였습니다.

### 과제 5. 특정 게시글을 조회
- 게시글 ID를 받아서 해당 게시글이 존재할 경우 조회할 수 있도록 구현하였습니다.

### 과제 6. 특정 게시글을 수정
- 작성자 ID를 함께 받아서 작성자만 수정할 수 있도록 구현하였습니다.

### 과제 7. 특정 게시글을 삭제
- 작성자 ID를 함께 받아서 작성자만 삭제할 수 있도록 구현하였습니다.

# API 명세(request/response 포함)

## 과제 1. 사용자 회원가입


### request
- POST ```/user/singup```
```
{
    "email": "이메일@1123111",
    "password": "12345678"
}
```

### response
- 201 Created
```
{
    "createDate": "2023-08-16T17:05:30.7478796",
    "updateDate": "2023-08-16T17:05:30.7478796",
    "id": 2,
    "email": "이메일@1123111",
    "password": "{bcrypt}$2a$10$Mo9/OOC.fvBd6rTtEDJbGum4J4jZE3MorwjGEh0XhNH7J6kf0b7W6",
    "roles": null,
    "enabled": true,
    "authorities": [
        {
            "authority": "USER"
        }
    ],
    "credentialsNonExpired": true,
    "accountNonExpired": true,
    "accountNonLocked": true,
    "username": "이메일@1123111"
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "회원가입 시 비밀번호는 최소 8자 이상이어야 합니다."
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "회원가입 시 이메일에 @이 포함되어야 합니다."
}
```
- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "이미 존재하는 이메일입니다."
}
```

## 과제 2. 사용자 로그인
### request
- POST ```/user/login```
```
{
    "email": "이메일@1123111",
    "password": "12345678"
}
```
### response
- 200 OK
```
{
    "grantType": "Bearer",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLsnbTrqZTsnbxAMTEyMzExMSIsImF1dGgiOiJVU0VSIiwiZXhwIjoxNjkyMjU5ODM4fQ.U0to1TYoExhFnRCOWDK966NhKU7D8KX6P8m0LEw_WHs",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTIyNTk4Mzh9.V9S3TH-ty_Z5XLhdRqLbRTih4yOP_au3wWKIPp1OBr8"
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "로그인 비밀번호는 8자 이상이어야 합니다."
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "로그인 이메일은 @이 포함되어야 합니다."
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "이메일 혹은 비밀번호가 일치하지 않습니다."
}
```

## 과제 3. 새로운 게시글을 생성
### request
- POST ```/boards```
```
{
    "title": "제목입니다!",
    "content": "내용입니다!",
    "user": {
        "id": 1
    }
}
```
### response
- 200 OK
```
{
    "createDate": "2023-08-16T17:14:43.2769722",
    "updateDate": "2023-08-16T17:14:43.2769722",
    "id": 4,
    "title": "제목입니다!",
    "content": "내용입니다!",
    "user": {
        "createDate": null,
        "updateDate": null,
        "id": 1,
        "email": null,
        "password": null,
        "roles": null,
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true,
        "username": null
    }
}
```

## 과제 4. 게시글 목록을 조회
### request
- GET ```/boards```
```
http://15.164.50.227:8081/boards?sort=id,desc
```

### response
- 200 OK
```
{
    "content": [
        {
            "createDate": "2023-08-14T17:32:49.705594",
            "updateDate": "2023-08-14T17:32:49.705594",
            "id": 2,
            "title": "제목2",
            "content": "내용2",
            "user": {
                "createDate": "2023-08-14T17:32:49.705594",
                "updateDate": "2023-08-14T17:32:49.705594",
                "id": 1,
                "email": "tester1@wanted.com",
                "password": "12345678",
                "roles": "USER",
                "enabled": true,
                "authorities": [
                    {
                        "authority": "USER"
                    }
                ],
                "credentialsNonExpired": true,
                "accountNonExpired": true,
                "accountNonLocked": true,
                "username": "tester1@wanted.com"
            }
        },
        {
            "createDate": "2023-08-14T17:32:49.705594",
            "updateDate": "2023-08-14T17:32:49.705594",
            "id": 1,
            "title": "제목1",
            "content": "내용1",
            "user": {
                "createDate": "2023-08-14T17:32:49.705594",
                "updateDate": "2023-08-14T17:32:49.705594",
                "id": 1,
                "email": "tester1@wanted.com",
                "password": "12345678",
                "roles": "USER",
                "enabled": true,
                "authorities": [
                    {
                        "authority": "USER"
                    }
                ],
                "credentialsNonExpired": true,
                "accountNonExpired": true,
                "accountNonLocked": true,
                "username": "tester1@wanted.com"
            }
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "unsorted": false,
            "sorted": true
        },
        "offset": 0,
        "pageSize": 20,
        "pageNumber": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalElements": 2,
    "totalPages": 1,
    "first": true,
    "size": 20,
    "number": 0,
    "sort": {
        "empty": false,
        "unsorted": false,
        "sorted": true
    },
    "numberOfElements": 2,
    "empty": false
}
```

## 과제 5. 특정 게시글을 조회
### request
- GET ```/boards/{id}```

### response
- 200 OK
```
{
    "createDate": "2023-08-14T17:32:49.705594",
    "updateDate": "2023-08-14T17:32:49.705594",
    "id": 1,
    "title": "제목1",
    "content": "내용1",
    "user": {
        "createDate": "2023-08-14T17:32:49.705594",
        "updateDate": "2023-08-14T17:32:49.705594",
        "id": 1,
        "email": "tester1@wanted.com",
        "password": "12345678",
        "roles": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true,
        "username": "tester1@wanted.com"
    }
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "존재하지 않는 게시글입니다."
}
```

## 과제 6. 특정 게시글을 수정
### request
- PUT ```/boards/{id}```

```
{
    "title": "제목1 수정",
    "content": "내용1 수정",
    "user": {
        "id": 1
    }
}
```

### response
- 200 OK
```
{
    "createDate": "2023-08-14T17:32:49.705594",
    "updateDate": "2023-08-16T17:25:19.2542586",
    "id": 2,
    "title": "제목1 수정",
    "content": "제목1 수정",
    "user": {
        "createDate": "2023-08-14T17:32:49.705594",
        "updateDate": "2023-08-14T17:32:49.705594",
        "id": 1,
        "email": "tester1@wanted.com",
        "password": "12345678",
        "roles": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true,
        "username": "tester1@wanted.com"
    }
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "작성자만 수정할 수 있습니다."
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "존재하지 않는 게시글입니다."
}
```

## 과제 7. 특정 게시글을 삭제
### request
- DELETE ```/boards/{id}```
```
{
    "id": 1
}
```

### response
- 200 OK
```
{
    "createDate": "2023-08-14T17:32:49.705594",
    "updateDate": "2023-08-14T17:32:49.705594",
    "id": 1,
    "title": "제목1",
    "content": "내용1",
    "user": {
        "createDate": "2023-08-14T17:32:49.705594",
        "updateDate": "2023-08-14T17:32:49.705594",
        "id": 1,
        "email": "tester1@wanted.com",
        "password": "12345678",
        "roles": "USER",
        "enabled": true,
        "authorities": [
            {
                "authority": "USER"
            }
        ],
        "credentialsNonExpired": true,
        "accountNonExpired": true,
        "accountNonLocked": true,
        "username": "tester1@wanted.com"
    }
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "존재하지 않는 게시글입니다."
}
```

- 400 Bad Request
```
{
    "code": "400 BAD_REQUEST",
    "message": "작성자만 삭제할 수 있습니다."
}
```
