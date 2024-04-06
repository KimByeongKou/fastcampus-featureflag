import http from "k6/http";
import {randomIntBetween} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
    vus: 50,
    duration: "30s"
};

var keySet = ["test", "service1", "service2", "service3", "service4"]
export default () => {

    // 시나리오 1
    // var randomKeyIdx = randomIntBetween(0, 4)
    // var randomKeyInt = randomIntBetween(1, 10000)
    //
    // var keyId= keySet[randomKeyIdx] + "-" + randomKeyInt
    // http.get("http://localhost:8080/api/v1/resolve/string/" + keyId);


    // 시나리오 2
    var randomKeyIdx = randomIntBetween(0, 4)
    var randomKeyInt = randomIntBetween(1, 10000)

    if (randomKeyIdx === 0 || randomKeyIdx === 1 || randomKeyIdx === 2) {
        randomKeyIdx = 0
    }

    var keyId= keySet[randomKeyIdx] + "-" + randomKeyInt
    http.get("http://localhost:8080/api/v1/resolve/string/" + keyId);
}

// 시나리오 1, LRU 캐시가 보통 더 유리할 것이다.
// lru, 10000: 19%, evict: 107,000  // avg=10.27ms min=241µs    med=9.23ms max=217.11ms p(90)=17.74ms p(95)=22.02ms
// lru, 20000: 36.9%, evict: 77,000 // avg=9.68ms  min=215µs    med=8.36ms max=218.7ms  p(90)=17.85ms p(95)=22.27ms

// custom, 10000: // avg=20.04ms min=339µs    med=15.95ms max=201.47ms p(90)=39.23ms p(95)=50.09ms
// -->> LRU 캐시가, 보통은 더 유리했다.

// 시나리오 2, 특정 패턴에서는 custom 캐시가 더 유리할 수 있다.
// lru, 10000: // 40%, 86000,  // 160808, avg=9.26ms  min=208µs    med=7.93ms max=185.07ms p(90)=17.42ms p(95)=22.32ms
// custom, 10000: // 38.7%, //  avg=18.01ms min=350µs   med=13.65ms max=223.24ms p(90)=36.84ms p(95)=48.34ms

// custom - policy, 10000: 52%,  // avg=15.71ms min=311µs    med=10.97ms max=211.42ms p(90)=33.53ms p(95)=44.69ms
// custom policy 가 적용된 custom cache 가 특정 케이스에서는 lru 보다 더 유리할 수 있다.
