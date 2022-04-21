import React from "react"
import Card from '../../components/card'
import FormGroup from '../../components/form-group'
import ClienteService from "../../api/service/clienteService"
import * as mensagens from '../../components/toastr'
import ClienteTable from "./clienteTable"

import NumberFormat from 'react-number-format'
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';

class ConcultaCliente extends React.Component {

    state = {
        nome: '',
        cpf: '',
        clientes: [],
        clienteDeletar : null,
        showConfirmDialog : false
    }

    constructor() {
        super();
        this.service = new ClienteService();
    }

    buscar = () => {
        const clienteFriltro = {
            nome: this.state.nome,
            cpf: this.state.cpf
        }
        this.service
            .consultar(clienteFriltro)
            .then( response => {
                const lista = response.data;
                if(lista.length < 1) {
                    mensagens.mensagemAlert('Nenhum resultado encontrado.');
                }
                this.setState({clientes: lista})
            }).catch(erro => {
                mensagens.mensagemErro(erro);
            })
    }

    editarAction = (id) => {
        this.props.history.push(`/cadastro-cliente/${id}`)
    }

    abrirConfirmacaoDeletar = (cliente) => {
        this.setState({showConfirmDialog: true, clienteDeletar: cliente})
    }

    deletarAction = () => {
        this.service
            .deletar(this.state.clienteDeletar.id)
            .then(response => {
                const clientes = this.state.clientes;
                const indexCliente = clientes.indexOf(this.state.clienteDeletar);
                clientes.splice(indexCliente, 1);
                this.setState({clientes: clientes, showConfirmDialog: false, clienteDeletar: {}});
                mensagens.mensagemSucesso('Cliente deletado com sucesso.');
            }).catch(erro => {
                if (erro.data) {
                    mensagens.mensagemErro(erro.data);
                } else {
                    mensagens.mensagemAlert('Seu usuário não tem permissão para excluir cliente.')
                }
            });
    }

    cancelarDelecao = () => {
        this.setState({showConfirmDialog: false, clienteDeletar: {}})
    }

    render() {
        const confirmDialogFooter = (
            <div>
                <Button label="Confirmar" icon="pi pi-check" onClick={this.deletarAction} />
                <Button label="Cancelar" icon="pi pi-times" onClick={this.cancelarDelecao} />
            </div>
        )

        return (
            <Card title="Consulta Clientes">
                <div className="row">
                    <div className="col-md-6">
                        <div className="bs-component">
                            <FormGroup label="Nome: " htmlFor="inputNome">
                                <input type="text" className="form-control" id="inputNome"
                                    value={this.state.nome}
                                    onChange={e => this.setState({nome: e.target.value})}
                                    placeholder="Digite o nome" />
                            </FormGroup>
                            <FormGroup label="CPF: " htmlFor="inputCpf">
                                <NumberFormat placeholder="Digite o CPF" id='inputCpf' className='form-control'  value={this.state.cpf} displayType={'input'} name='cpf' format="###.###.###-##" onChange={e => this.setState({cpf: e.target.value})} />
                            </FormGroup>

                            <button onClick={this.buscar} type="button" className="btn btn-success"><i className="pi pi-search"></i>  Buscar</button>
                            <button onClick={e => this.props.history.push('/cadastro-cliente')} type="button" className="btn btn-danger"><i className="pi pi-plus"></i> Cadastrar</button>
                        </div>
                    </div>
                </div>
                <br />
                <div className="row">
                    <div className="col-md-12">
                        <div className="bs-component">
                            {this.state.clientes.length > 0 ? 
                                (
                                    <ClienteTable clientes={this.state.clientes} deletarAction={this.abrirConfirmacaoDeletar} editarAction={this.editarAction} />
                                ) :
                                (
                                    <br />
                                )
                            }
                        </div>
                    </div>
                </div>
                <div>
                    <Dialog header="Excluir Cliente" 
                            visible={this.state.showConfirmDialog} 
                            style={{ width: '50vw' }} 
                            modal={true}
                            footer={confirmDialogFooter}
                            onHide={() => this.setState({showConfirmDialog: false})}>
                        <p>Confirma a exclusão deste cliente? </p>
                    </Dialog>
                </div>
            </ Card>
        )
    }

}

export default ConcultaCliente;