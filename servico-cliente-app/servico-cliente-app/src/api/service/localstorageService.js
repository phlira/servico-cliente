class LocalStorageService {
    static addItem(chave, valor) {
        localStorage.setItem(chave, JSON.stringify(valor));
    }

    static obterItem(chave) {
        const item = localStorage.getItem(chave);
        if (item !== 'undefined') {
            return JSON.parse(item);
        } else {
            return null;
        }
    }

    static removerItem(chave) {
        localStorage.removeItem(chave);
    }
}

export default LocalStorageService;