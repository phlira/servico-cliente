import LocalStorageService from "./localstorageService";
import jwt from "jsonwebtoken";
import ApiService from "../apiService";


export const USUARIO_LOGADO = '_usuario_logado';
export const TOKEN = 'access_token';

export default class AuthService {

    static isUsuarioLogado() {
        const token = LocalStorageService.obterItem(TOKEN);
        if (!token) {
            return false;
        }
        const decodedToken = jwt.decode(token);
        let expiration = 0;
        if (decodedToken) {
            expiration = decodedToken.exp;
        } else {
            return false;
        }
        
        const isTokenInvalido = Date.now() >= (expiration * 1000);
        return !isTokenInvalido;
    }

    static removerItem() {
        LocalStorageService.removerItem(USUARIO_LOGADO);
        LocalStorageService.addItem(TOKEN);
    }

    static logar (usuario, token) {
        LocalStorageService.addItem(USUARIO_LOGADO, usuario);
        LocalStorageService.addItem(TOKEN, token);
        ApiService.registrarToken(token);
    }

    static obterUsuarioAutenticado(){
        return LocalStorageService.obterItem(USUARIO_LOGADO);
    }

    static refreshSession(){
        const token = LocalStorageService.obterItem(TOKEN);
        const usuario = this.obterUsuarioAutenticado();
        this.logar(usuario, token);
        return usuario;
    }

}