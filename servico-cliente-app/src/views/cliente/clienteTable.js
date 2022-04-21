import React from "react"
import NumberFormat from 'react-number-format'

export default props => {
    const rows = props.clientes.map( cliente => {
        return (
            <tr key={cliente.id}>
            <td>{cliente.nome}</td>
            <td><NumberFormat value={cliente.cpf} displayType={'text'} format="###.###.###-##" /></td>
            <td><NumberFormat value={cliente.endereco.cep} displayType={'text'} format="##.###-###" /></td>
            <td>
                <button type="button" className="btn btn-primary" onClick={e => props.editarAction(cliente.id)} title="Editar"><i className="pi pi-pencil"></i></button>
                <button type="button" className="btn btn-danger" onClick={e => props.deletarAction(cliente)} title="Deletar"><i className="pi pi-trash"></i></button>
            </td>
        </tr>
        )
    })

    return (
        <table className="table table-hover">
        <thead>
            <tr>
                <th scope="col">Nome</th>
                <th scope="col">CPF</th>
                <th scope="col">CEP</th>
                <th scope="col">Ações</th>
            </tr>
        </thead>
        <tbody>
            {rows}
        </tbody>
    </table>
    )
}