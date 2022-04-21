import axios from "axios"

const httpClient = axios.create({
    baseURL: 'https://viacep.com.br/ws/'
})

class CepService {

    constructor(apiUulr) {
        this.apiUulr = apiUulr;
    }

    get(cep) {
        return httpClient.get(`/${cep}/json/`);
    }

}

export default CepService;