import React from "react"
import { Route, Switch, BrowserRouter, Redirect } from 'react-router-dom'

import Home from "../views/home/home"
import ConcultaCliente from "../views/cliente/consulta-cliente"
import CadastroCliente from "../views/cliente/cadastro-cliente"
import Login from "../views/login/login"

import { AuthConsumer } from "./provedorAutenticacao";

function RotasAutenticadas({component: Component, isUsuarioAutenticado,...props}) {
    return (
        <Route {...props} render={(componentProps) => {
            if (isUsuarioAutenticado){
                return (
                    <Component {...componentProps} />
                )
            } else {
                return (
                    <Redirect to={{pathname : '/login', state: {from : componentProps.location}}} /> 
                )
            }
        }} />
    )
}

function Rotas(props) {
    return (
        <BrowserRouter>
            <Switch>
                <RotasAutenticadas isUsuarioAutenticado={props.isUsuarioAutenticado} exact path="/" component={Home} />
                <RotasAutenticadas isUsuarioAutenticado={props.isUsuarioAutenticado} exact path="/consulta-cliente" component={ConcultaCliente} />
                <RotasAutenticadas isUsuarioAutenticado={props.isUsuarioAutenticado} exact path="/cadastro-cliente/:id?" component={CadastroCliente} />
                <Route exact path="/login" component={Login} />
            </Switch>
        </BrowserRouter>
    )
}

export default () => (
    <AuthConsumer>
        {(context) => (<Rotas isUsuarioAutenticado={context.isAutenticado} />)}
    </AuthConsumer>
)
