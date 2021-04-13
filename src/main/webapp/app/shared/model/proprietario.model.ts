export interface IProprietario {
  id?: number;
  nome?: string | null;
  telefone?: string | null;
  email?: string | null;
}

export class Proprietario implements IProprietario {
  constructor(public id?: number, public nome?: string | null, public telefone?: string | null, public email?: string | null) {}
}
