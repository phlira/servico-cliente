import React from "react"
import NumberFormat from 'react-number-format'

export default props => {
    const rows = props.telefones.map( (telefone, index) => {
        return (
            <tr key={index}>
            <td>
                {telefone.tipo === 'CELULAR' ? 
                    (
                        <NumberFormat  value={telefone.numero} displayType={'text'} format="(##) # ####-####"  />    
                    ) : (
                        <NumberFormat  value={telefone.numero} displayType={'text'} format="(##) ####-####"  />
                    )
                }
            </td>
            <td>{telefone.tipo}</td>
            <td>
                <button type="button" className="btn btn-danger" onClick={e => props.deletarTelefone(index)}  title="Deletar"><i className="pi pi-trash"></i></button>
            </td>
        </tr>
        )
    })

    return (
        <table className="table table-hover">
        <thead>
            <tr>
                <th scope="col">Numero</th>
                <th scope="col">Tipo</th>
                <th scope="col">Deletar</th>
            </tr>
        </thead>
        <tbody>
            {rows}
        </tbody>
    </table>
    )
}