# fastcampus-featureflag

swagger: https://localhost:8080/swagger-ui.html

Grafana JVM Dashboard ID: 4701

todo
- 시나리오 정의
- 테스트 지표 정의. 실험 정의. 가설 정의
- 시나리오 별 테스트 프로그램 만들기
  - 시나리오 별 실험 검증

성능이 좋다를 정의하기.
- key 갯수, 요청 부하 
- 평균 latency, heap 메모리 사용량. cpu/memory 사용량


## 시나리오 정의
변인: 키의 갯수, 초당 요청 갯수, 캐시 리소스, 캐시 정책

- 시나리오 1:
  - 상황: 완전 무작위 키로 featureflag 를 조회한다. (무작위 많은 키)
  - 가설: 
    - Eviction 많이 일어날 것. 정책 별 차이가 없을 것. LRU 가 더 좋을 것
    - 혹은 커스텀 정책(짧은 Expiry) 성능이 좋을 것 


- 시나리오 2:
  - 상황: 한정된 키로, 엄청나게 많은 featureflag 를 조회한다. (한정된 키)
  - 가설
    - Eviction 잘 일어나지 않는다. 캐시 정책 별 차이가 없을 것. 캐시 사이즈가 큰 영향을 미칠 것.


- 시나리오 3:
  - 상황: 특정 키워드로 시작하는 key 의 조회가 압도적으로 많다. (패턴을 가지는 많은 키)
  - 가설: 
    - Eviction 이 많이 일어날 것. 일반적인 LRU 방식이 좋을 것.
    - LRU 캐시보다, 커스텀 캐시(키 패턴 어드바이저) 가 더 성능이 좋을 것이다.
