export interface IVacina {
  id?: number;
  status?: string | null;
  pesoMaterial?: string | null;
  estimativaVacinas?: string | null;
  dataConclusao?: Date | null;
  observacoes?: string | null;
}

export class Vacina implements IVacina {
  constructor(
    public id?: number,
    public status?: string | null,
    public pesoMaterial?: string | null,
    public estimativaVacinas?: string | null,
    public dataConclusao?: Date | null,
    public observacoes?: string | null
  ) {}
}
