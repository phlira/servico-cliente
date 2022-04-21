import ApiService from "../apiService"
import ErroValidacao from "../exception/ErroValidacao"

class ClienteService extends ApiService {
    constructor() {
        super('/api/clientes');
    }

    consultar(clienteFiltro) {
        let params = '';
        if (clienteFiltro.nome) {
            params = `?nome=${clienteFiltro.nome}`;
        }
        if (clienteFiltro.cpf && params.length > 1) {
            params = `${params}&cpf=${clienteFiltro.cpf}`;
        } else if (clienteFiltro.cpf && params.length < 1) {
            params = `?cpf=${clienteFiltro.cpf}`
        }

        return this.get(params);
    }

    obterPorId(id) {
        return this.get(`/${id}`);
    }

    salvar(cliente) {
        return this.post('/', cliente);
    }

    atualizar(cliente) {
        return this.put(`/${cliente.id}`, cliente);
    }

    deletar(id) {
        return this.delete(`/${id}`)
    }

    validar(cliente) {
        const erros = [];

        if(!cliente.nome) {
            erros.push("Informe o nome");
        }

        if(!cliente.cpf) {
            erros.push("Informe o cpf");
        }

        if(cliente.email.length < 1) {
            erros.push("Informe pelo menos um email");
        }

        if(cliente.telefone.length < 1) {
            erros.push("Informe pelo menos um telefone");
        }

        if (cliente.endereco.cep == null || cliente.endereco.cep.length < 8) {
            erros.push("Informe um Cep válido");
        }
        
        if (!cliente.endereco.cep || !cliente.endereco.logradouro || !cliente.endereco.bairro || !cliente.endereco.cidade || !cliente.endereco.uf ) {
            erros.push("Verifique os campos com (*) do endereço, pois são obrigatórios");
        }

        if (erros && erros.length > 0) {
            throw new ErroValidacao(erros);
        }
    }

    obterListaTipos() {
        return [
            {label: 'SELECIONE...', value: ''},
            {label: 'RESIDENCIAL', value: 'RESIDENCIAL'},
            {label: 'COMERCIAL', value: 'COMERCIAL'},
            {label: 'CELULAR', value: 'CELULAR'}
        ];
    }
}

export default ClienteService;