# fastcampus-featureflag

fastcampus 에서 featureflag 를 구현하는 프로젝트입니다.
flagd engine 을 사용하고, 클라이언트에서 featureflag 를 조회하는 서비스를 구현합니다.

- swagger: https://localhost:8080/swagger-ui.html

## 실행방법
- flagd, featureflag 서버 통합 docker-compose 실행
  - `make docker`
- featureflag 서버 local 실행
  - `make run`

