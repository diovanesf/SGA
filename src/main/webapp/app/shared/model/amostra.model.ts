export interface IAmostra {
  id?: number;
  protocolo?: string | null;
  formaEnvio?: string | null;
  numeroAmostras?: number | null;
  especie?: string | null;
  materialRecebido?: string | null;
  acondicionamento?: string | null;
  condicaoMaterial?: string | null;
  status?: string | null;
}

export class Amostra implements IAmostra {
  constructor(
    public id?: number,
    public protocolo?: string | null,
    public formaEnvio?: string | null,
    public numeroAmostras?: number | null,
    public especie?: string | null,
    public materialRecebido?: string | null,
    public acondicionamento?: string | null,
    public condicaoMaterial?: string | null,
    public status?: string | null
  ) {}
}
