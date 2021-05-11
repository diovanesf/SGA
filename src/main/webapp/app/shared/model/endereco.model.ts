export interface IEndereco {
  id?: number;
  endereco?: string | null;
  cep?: string | null;
  cidade?: string | null;
  estado?: string | null;
  coordenadasGps?: string | null;
}

export class Endereco implements IEndereco {
  constructor(
    public id?: number,
    public endereco?: string | null,
    public cep?: string | null,
    public cidade?: string | null,
    public estado?: string | null,
    public coordenadasGps?: string | null
  ) {}
}
