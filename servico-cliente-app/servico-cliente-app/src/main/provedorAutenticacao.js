import React from "react";

import AuthService from "../api/service/authService";
import jwt from "jsonwebtoken";

export const AuthContext = React.createContext();
export const AuthConsumer = AuthContext.Consumer;
const AuthProvider = AuthContext.Provider;

class ProvedorAutenticacao extends React.Component {

    state = {
        usuarioAutenticado: null,
        isAutenticado: false,
        isLoading: true
    }

    iniciarSessao = (toeknDTO) => {
        const token = toeknDTO.token
        const clains = jwt.decode(token);
        const usuario = {
            id: clains.idUsuario,
            nome: clains.nome,
            login: clains.login
        }
        AuthService.logar(usuario, token);
        this.setState({isAutenticado: true, usuarioAutenticado: usuario});
    }

    encerrarSessao = () => {
        AuthService.removerItem();
        this.setState({isAutenticado: false, usuarioAutenticado: null });
    }

    async componentDidMount() {
        const isAutenticado = await AuthService.isUsuarioLogado();
        if (isAutenticado) {
            const usuario = await AuthService.refreshSession();
            this.setState({
                isAutenticado: true, usuarioAutenticado: usuario, isLoading: false
            })
            
        } else {
            this.setState(previousState => {
                return {
                    ...previousState,
                    isLoading: false
                }
            })
        }
    }

    render () {
        if (this.state.isLoading) {
            return null;
        }

        const contexto = {
            usuarioAutenticado: this.state.usuarioAutenticado,
            isAutenticado: this.state.isAutenticado,
            iniciarSessao: this.iniciarSessao,
            encerrarSessao: this.encerrarSessao
        }

        return (
            <AuthProvider value={contexto}>
                {this.props.children}
            </AuthProvider>
        )
    }

}

export default ProvedorAutenticacao;