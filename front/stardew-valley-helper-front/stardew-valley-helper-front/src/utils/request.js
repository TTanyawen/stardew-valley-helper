import axios from "axios"

const request=axios.create({

    baseURL:"/api",
    timeout:10000

})

request.interceptors.response.use(

response=>response.data,

error=>{
    console.log(error)
    return Promise.reject(error)
}

)

export default request