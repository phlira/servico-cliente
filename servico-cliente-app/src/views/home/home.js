import React from "react";

class Home extends React.Component {

    render() {
        return (
            <div className="jumbotron">
                <h1 className="display-3">Bem vindo!</h1>
                <p className="lead">Esse é um sistema de cadastro de clientes. Temos 2 usuários pré-cadastrados conforme solicitado: Admin e o Comum.</p>
                <p className="lead">Este é um projeto desafio, solicitado pelo Eduardo da Surittec.</p>
                <p className="lead">Meu nome é: Pedro Henrique Soares Lira e estou no desafio para o cargo de Desenvolvedor Sênior.</p>
                <hr className="my-4" />
                <p>E essa é sua área administrativa, utilize um dos menus ou botões abaixo para navegar pelo sistema.</p>
                <p className="lead">
                    <a className="btn btn-primary btn-lg" href="/consulta-cliente" role="button"><i className="fa fa-users"></i>  Consulta Clientes</a>
                </p>
            </div>
        )
    }
}

export default Home;