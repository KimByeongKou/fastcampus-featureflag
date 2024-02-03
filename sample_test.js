import http from "k6/http";
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

export const options = {
  vus: 50,
  duration: "30s"
};

var keySet = ["test", "service1", "service2", "service3", "service4"]

export default () => {
  var randomKeyIdx = randomIntBetween(0, 4)

  // 3/5 of the time, we want to use the first key
  if( randomKeyIdx === 0 || randomKeyIdx === 1  || randomKeyIdx === 2 ){
    randomKeyIdx = 0
  }

  var randomKeyInt = randomIntBetween(1, 10000)
  var randomKey = keySet[randomKeyIdx] + "-" + randomKeyInt
  const response = http.get("http://localhost:8080/api/v1/resolve/string/" + randomKey);
  // console.log(response);
};

