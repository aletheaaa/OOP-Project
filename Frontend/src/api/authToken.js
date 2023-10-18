import axios from "axios";

const AUTH_URL = "http://localhost:8080/api/v1/auth/authenticate";
const REGISTER_URL = "http://localhost:8080/api/v1/auth/register";

export async function authenticate(email, password) {
    try {
        const response = await axios.post(AUTH_URL, {
            email: email,
            password: password,
        })
        console.log(response.data);
        return {
            status: "success",
            data: response.data
        };
    } catch (error) {
        console.log("[authenticate] Authentication Error");
        console.log(error);
        return error;
    }
}

export async function register(email, password) {
    let token = null;
    axios
        .post(REGISTER_URL, {
            email: email,
            password: password,
        })
        .then((response) => {
            console.log("[register] response.data: " + response.data);
            token = response.data.token;
            return {token: token, status: response.data};
        })
        .catch((error) => {
            console.log("[register] error: " + error);
            return {token: token, status: error};
    });
}