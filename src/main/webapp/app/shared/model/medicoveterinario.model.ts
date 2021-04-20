export interface IMedicoveterinario {
  id?: number;
  nome?: string | null;
  telefone?: string | null;
  email?: string | null;
  CRMV?: string | null;
  enviarLaudo?: boolean | null;
}

export class Medicoveterinario implements IMedicoveterinario {
  constructor(
    public id?: number,
    public nome?: string | null,
    public telefone?: string | null,
    public email?: string | null,
    public CRMV?: string | null,
    public enviarLaudo?: boolean | null
  ) {
    this.enviarLaudo = this.enviarLaudo ?? false;
  }
}
