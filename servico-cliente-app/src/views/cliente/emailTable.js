import React from "react"

export default props => {
    const rows = props.emails.map( (email, index) => {
        return (
            <tr key={index}>
            <td>{email.email}</td>
            <td>
                <button type="button" className="btn btn-danger" onClick={e => props.deletarEmail(index)}  title="Deletar"><i className="pi pi-trash"></i></button>
            </td>
        </tr>
        )
    })

    return (
        <table className="table table-hover">
        <thead>
            <tr>
                <th scope="col">Email</th>
                <th scope="col">Deletar</th>
            </tr>
        </thead>
        <tbody>
            {rows}
        </tbody>
    </table>
    )
}