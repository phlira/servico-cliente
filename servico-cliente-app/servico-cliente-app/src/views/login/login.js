import React from "react";
import Card from "../../components/card";
import FormGroup from "../../components/form-group";
import { withRouter } from 'react-router-dom'
import UsuarioService from "../../api/service/usuarioService";
import { mensagemErro } from '../../components/toastr'
import { AuthContext} from '../../main/provedorAutenticacao'

class Login extends React.Component {

    state = {
        login: '',
        senha: ''
    }

    constructor() {
        super();
        this.service = new UsuarioService();
    }

    entrar = () => {
        this.service.autenticar({
            login: this.state.login,
            senha: this.state.senha
        }).then( response => {
            this.context.iniciarSessao(response.data);
            this.props.history.push('/');
        } ).catch ( erro => {
            mensagemErro(erro.response.data)
        })
    }

    render() {
        return (
            <div className="row">
                <div className="col-md-6 offset-md-3">
                    <div className="bs-docs-section">
                        <Card title="Login">
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="bs-component">
                                        <fieldset>
                                            <FormGroup label="Login: *" htmlFor="exampleInputLogin1">
                                                <input type="email" className="form-control" 
                                                        id="exampleInputLogin1" aria-describedby="loginHelp" 
                                                        placeholder="Digite o Login" 
                                                        value={this.state.login} 
                                                        onChange={e => this.setState({login: e.target.value})} />
                                            </FormGroup>
                                            <FormGroup label="Senha: *" htmlFor="exampleInputPassword1">
                                                <input type="password" className="form-control" 
                                                        id="exampleInputPassword1" placeholder="Password" 
                                                        value={this.state.senha} 
                                                        onChange={e => this.setState({senha: e.target.value })} />
                                            </FormGroup>
                                            <button onClick={this.entrar} className="btn btn-success"><i className="pi pi-sign-in"></i>  Entrar</button>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </div>
                </div>
            </div>
        )
    }
}

Login.contextType = AuthContext

export default withRouter( Login );