import React from "react"
import { withRouter } from 'react-router-dom'
import NumberFormat from 'react-number-format'

import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';

import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import EmailTable from "./emailTable";
import TelefoneTable from "./telefoneTable";
import SelectMenu from '../../components/selectMenu';
import * as mensagens from '../../components/toastr'

import ClienteService from "../../api/service/clienteService";
import CepService from '../../api/service/cepService'

class CadastroCliente extends React.Component {

    constructor() {
        super();
        this.service = new ClienteService();
        this.cepService = new CepService();
    }

    componentDidMount() {
        const params = this.props.match.params;
        if (params.id) {
            this.buscarClientePorId(params.id);
        }
    }

    buscarClientePorId = (id) => {
            this.service.obterPorId(id)
                .then(response => {
                    this.setState({ 
                            ...response.data, 
                            atualizando: true,
                            telefones: response.data.telefone,
                            emails: response.data.email,
                            telefone: '',
                            email: '',
                            cepPesquisar: response.data.endereco.cep
                        })
                })
    }

    state = {
        id: null,
        nome: '',
        cpf: '',
        email : '',
        emails: [],
        telefone : '',
        tipoTelefone: '',
        telefones: [],
        endereco: {
            id : null,
            cep : '',
            logradouro : '',
            bairro : '',
            cidade : '',
            uf : '',
            complemento : '',
        },
        atualizando: false,
        usuario: null,
        showConfirmDialogTelefone: false,
        telefoneDeletar: null,
        showConfirmDialogEmail: false,
        emailDeletar: null,
        cepPesquisar: ''
    }
    
    handleChange = (evento) => {
        const value = evento.target.value;
        const name = evento.target.name;
        this.setState({[name] : value})
    }

    voltarParaClientes = () => {
        this.props.history.push('/consulta-cliente')
    }

    atualizar = () => {
        const cliente = {
            id : this.state.id,
            nome : this.state.nome,
            cpf : this.state.cpf, 
            endereco : {
                id : this.state.endereco.id,
                cep : this.state.endereco.cep,
                logradouro : this.state.endereco.logradouro,
                bairro : this.state.endereco.bairro,
                cidade : this.state.endereco.cidade,
                uf : this.state.endereco.uf,
                complemento : this.state.endereco.complemento,
            },
            telefone : this.state.telefones,
            email : this.state.emails,
            idUsuario : this.state.usuario.id
        }
        
        try {
            this.service.validar(cliente)
        } catch (erro) {
            const msgs = erro.mensagens;
            msgs.forEach(msg => mensagens.mensagemErro(msg));
            return false;
        }

        this.service
            .atualizar(cliente)
            .then(response => {
                mensagens.mensagemSucesso('Cliente atualizado com sucesso.');
            }).catch(error => {
                if (error.data) {
                    mensagens.mensagemErro(error.data);
                } else {
                    mensagens.mensagemAlert('Seu usuário não tem permissão para alterar cliente.')
                }
            })
    }

    submit = () => {
        const cliente = {
            nome : this.state.nome,
            cpf : this.state.cpf, 
            endereco : {
                cep : this.state.endereco.cep,
                logradouro : this.state.endereco.logradouro,
                bairro : this.state.endereco.bairro,
                cidade : this.state.endereco.cidade,
                uf : this.state.endereco.uf,
                complemento : this.state.endereco.complemento,
            },
            telefone : this.state.telefones,
            email : this.state.emails,
            idUsuario : 1
        }

        try {
            this.service.validar(cliente)
        } catch (erro) {
            const msgs = erro.mensagens;
            msgs.forEach(msg => mensagens.mensagemErro(msg));
            return false;
        }

        this.service
            .salvar(cliente)
            .then(response => {
                mensagens.mensagemSucesso('Cliente salvo com sucesso');
                this.props.history.push(`/cadastro-cliente/${response.data.id}`);
            }).catch (error => {
                if (error.data) {
                    mensagens.mensagemErro(error.data);
                } else {
                    mensagens.mensagemAlert('Seu usuário não tem permissão para criar cliente.')
                }
            });
    }

    adicionarEmail = () => {
        let aEmail = {
            id: null,
            email: this.state.email
        }

        this.setState({
            emails: this.state.emails.concat([aEmail]),
            email: ''
        });
    }

    adicionarTelefone = () => {
        let aTel = {
            id: null,
            numero: this.state.telefone,
            tipo: this.state.tipoTelefone
        }

        this.setState({
            telefones : this.state.telefones.concat([aTel]),
            telefone: '',
            tipoTelefone: ''
        });
    }

    buscarCep = () => {
        const cep = this.state.cepPesquisar.replaceAll('.','').replaceAll('-','')
        this
            .cepService
            .get(cep)
            .then(response => {
                if (response.data.erro) {
                    mensagens.mensagemAlert('Cep não encontrado na base.');
                } else {
                this.setState({endereco : { 
                                    id: this.state.endereco.id,
                                    cep: response.data.cep,
                                    cidade : response.data.localidade,
                                    logradouro : response.data.logradouro,
                                    bairro : response.data.bairro,
                                    uf : response.data.uf,
                                    complemento : response.data.complemento,

                              }})
                }
            }).catch(erro => {
                console.log(erro);
            })
    }

    abrirConfirmacaoDeletarTelefone = (telefone) => {
        this.setState({
            showConfirmDialogTelefone: true,
            telefoneDeletar: telefone
        });
    }

    abrirConfirmacaoDeletarEmail = (email) => {
        this.setState({
            showConfirmDialogEmail: true,
            emailDeletar: email
        });
    }

    deletarTelefoneAction = () => {
        let telefones = this.state.telefones;
        telefones.splice(this.state.telefoneDeletar ,1);
        this.setState({telefones: telefones, showConfirmDialogTelefone: false});
        if (this.state.atualizando) {
            mensagens.mensagemAlert('ATENÇÃO, necessário salvar o cliente para confirmar a exclusão do telefone.');
        }
    }

    cancelarDelecaoTelefone = () => {
        this.setState({showConfirmDialogTelefone: false, telefoneDeletar: {}})
    }

    deletarEmailAction = () => {
        let emails = this.state.emails;
        emails.splice(this.state.emailDeletar ,1);
        this.setState({emails: emails, showConfirmDialogEmail: false});
        if (this.state.atualizando) {
            mensagens.mensagemAlert('ATENÇÃO, necessário salvar o cliente para confirmar a exclusão do email.');
        }
    }

    cancelarDelecaoEmail = () => {
        this.setState({showConfirmDialogEmail: false, emailDeletar: {}})
    }

    render() {
        const tipos = this.service.obterListaTipos();

        const confirmDialogFooterTelefone = (
            <div>
                <Button label="Confirmar" icon="pi pi-check" onClick={this.deletarTelefoneAction} />
                <Button label="Cancelar" icon="pi pi-times" onClick={this.cancelarDelecaoTelefone} />
            </div>
        );

        const confirmDialogFooterEmail = (
            <div>
                <Button label="Confirmar" icon="pi pi-check" onClick={this.deletarEmailAction} />
                <Button label="Cancelar" icon="pi pi-times" onClick={this.cancelarDelecaoEmail} />
            </div>
        );

        return (
            <Card title={this.state.atualizando ? 'Atualizando Cliente': 'Cadastro de Cliente'}>
                <div className='row'>
                    <div className='col-md-6'>
                        <FormGroup id='inputNome' label='Nome: *'>
                            <input id='inputNome' type='text' className='form-control' name='nome' value={this.state.nome} onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                    <div className='col-md-6'>
                        <FormGroup id='inputCPF' label="CPF: *">
                            <NumberFormat id='inputCPF' className='form-control'  value={this.state.cpf} displayType={'input'} name='cpf' format="###.###.###-##" onChange={this.handleChange} />
                        </FormGroup>
                    </div>
                </div>
                < br/>
                <fieldset className="form-group">
                    <legend>Endereço</legend>
                    <div className="row">
                        <div className="col-md-2">
                            <FormGroup id='inputCEP' label="CEP: *">
                                <NumberFormat id='inputCep' className='form-control'  value={this.state.cepPesquisar} displayType={'input'} name='cepPesquisar' format="##.###-###" onChange={e => this.setState({cepPesquisar : e.target.value }) } />
                            </FormGroup>
                        </div>
                        <div className="col-md-2">
                            <button onClick={this.buscarCep} className="btn btn-primary">
                                <i className='pi pi-check' />
                            </button>
                        </div>
                        <div className="col-md-4">
                            <FormGroup id='inputLogradouro' label="Logradouro: *">
                                <input id='inputLogradouro' type='text' name="logradouro" onChange={e => this.setState({endereco: { logradouro: e.target.value}}) } value={this.state.endereco.logradouro} className='form-control' />
                            </FormGroup>
                        </div>
                        <div className="col-md-4">
                            <FormGroup id='inputBairro' label="Bairro: *">
                                <input id='inputBairro' type='text' name="bairro" onChange={e => this.setState({endereco: {  bairro:  e.target.value }}) } value={this.state.endereco.bairro} className='form-control' />
                            </FormGroup>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <FormGroup id='inputCidade' label="Cidade: *">
                                <input id='inputCidade' type='text' name="cidade" onChange={e => this.setState({endereco: {  cidade:  e.target.value }}) } value={this.state.endereco.cidade} className='form-control' />
                            </FormGroup>
                        </div>
                        <div className="col-md-2">
                            <FormGroup id='inputUF' label="UF: *">
                                <input id='inputUF' type='text' name="uf" onChange={e => this.setState({endereco: {  uf:  e.target.value }}) } value={this.state.endereco.uf} className='form-control' />
                            </FormGroup>
                        </div>
                        <div className="col-md-6">
                            <FormGroup id='inputComplemento' label="Complemento: ">
                                <input id='inputComplemento' type='text' name="complemento" onChange={e => this.setState({endereco: {  complemento:  e.target.value }}) } value={this.state.endereco.complemento} className='form-control' />
                            </FormGroup>
                        </div>
                    </div>
                </fieldset>
                <br />
                <fieldset>
                    <div className="row">
                        <div className="col-md-6">
                            <legend>Emails</legend>
                            <div className="row">
                                <div className='col-md-10'>
                                    <FormGroup id='inputEmail' label='Informe o email: ' className='col-md-10'>
                                        <input id='inputEmail' type='email' name="email" onChange={e => this.setState({email: e.target.value }) } value={this.state.email} className='form-control' />
                                    </FormGroup>
                                </div>
                                <div className='col-md-2 form-group'>
                                    <button onClick={this.adicionarEmail} className="btn btn-primary">
                                                <i className='pi pi-check' />
                                            </button>
                                </div>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <legend>Telefones</legend>
                            <div className="row">
                                <div className="col-md-5">
                                    <FormGroup id='inputTipoTelefone' label='Informe o tipo: '>
                                        <SelectMenu id="inputTipoTelefone" lista={tipos}  className="form-control" name="tipoTelefone" onChange={this.handleChange} value={this.state.tipoTelefone} />    
                                    </FormGroup>
                                </div>
                                <div className='col-md-5'>
                                    <FormGroup id='inputTelefone' label='Informe o telefone: '>
                                    {this.state.tipoTelefone === 'CELULAR' ? 
                                        (
                                            <NumberFormat id='inputTelefone' className='form-control'  value={this.state.telefone} displayType={'input'} name='telefone' format="(##) # ####-####" onChange={this.handleChange} />    
                                        ) : (
                                            <NumberFormat id='inputTelefone' className='form-control'  value={this.state.telefone} displayType={'input'} name='telefone' format="(##) ####-####" onChange={this.handleChange} />
                                        )
                                    }
                                        
                                    </FormGroup>
                                </div>
                                <div className='col-md-2 form-group'>
                                    <button onClick={this.adicionarTelefone} className="btn btn-primary">
                                                <i className='pi pi-check' />
                                            </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <div className='row'>
                    
                </div>
                <div className="row col-md-12">
                    <div className="col-md-6">
                        {this.state.emails.length > 0 ? 
                            (
                                <EmailTable emails={this.state.emails} deletarEmail={this.abrirConfirmacaoDeletarEmail} />
                            ) :
                            (
                                <br />
                            )
                        }
                        
                    </div>
                    <div className="col-md-6">
                        {this.state.telefones.length > 0 ? 
                            (
                                <TelefoneTable telefones={this.state.telefones} deletarTelefone={this.abrirConfirmacaoDeletarTelefone} />    
                            ) :
                            (
                                <br />
                            )
                        }
                        
                    </div>
                    
                </div>
                <div className='row'>
                    <div className='col-md-6'>
                        {this.state.atualizando ?
                            (
                                <button onClick={this.atualizar} className="btn btn-success">
                                    <i className='pi pi-refresh' />
                                    Atualizar
                                </button>
                            )   : (
                                <button onClick={this.submit} className="btn btn-success">
                                    <i className='pi pi-save' />
                                    Salvar
                                </button>
                            ) 
                        }
                        <button onClick={this.voltarParaClientes} className="btn btn-danger">
                            <i className='pi pi-times' />
                            Cancelar
                        </button>
                    </div>
                </div>
                <div>
                    <Dialog header="Excluir Telefone" 
                            visible={this.state.showConfirmDialogTelefone} 
                            style={{ width: '50vw' }} 
                            modal={true}
                            footer={confirmDialogFooterTelefone}
                            onHide={() => this.setState({showConfirmDialogTelefone: false})}>
                        <p>Confirma a exclusão deste telefone? </p>
                        {this.state.atualizando ? ' Está excluindo apenas da lista. Precisa atualizar o cadastro para confirmar a exclusão.': ''} 
                    </Dialog>
                    <Dialog header="Excluir Email" 
                        visible={this.state.showConfirmDialogEmail} 
                        style={{ width: '50vw' }} 
                        modal={true}
                        footer={confirmDialogFooterEmail}
                        onHide={() => this.setState({showConfirmDialogEmail: false})}>
                    <p>Confirma a exclusão deste email? </p>
                    {this.state.atualizando ? ' Está excluindo apenas da lista. Precisa atualizar o cadastro para confirmar a exclusão.': ''}
                </Dialog>
                </div>
            </Card>
        )
    }

}

export default withRouter(CadastroCliente);