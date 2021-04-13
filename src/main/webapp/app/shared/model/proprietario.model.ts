import { IEndereco } from '@/shared/model/endereco.model';
import { IPropriedade } from '@/shared/model/propriedade.model';

export interface IProprietario {
  id?: number;
  nome?: string | null;
  telefone?: string | null;
  email?: string | null;
  endereco?: IEndereco | null;
  propriedade?: IPropriedade | null;
}

export class Proprietario implements IProprietario {
  constructor(
    public id?: number,
    public nome?: string | null,
    public telefone?: string | null,
    public email?: string | null,
    public endereco?: IEndereco | null,
    public propriedade?: IPropriedade | null
  ) {}
}
