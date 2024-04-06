import http from "k6/http";

export default () => {

    const response = http.get("http://localhost:8080/api/v1/resolve/string/test-1");
    console.log(response);

    // exampleValue-6767e001-a7d6-4c87-8fd5-41e31cb25c6c
}