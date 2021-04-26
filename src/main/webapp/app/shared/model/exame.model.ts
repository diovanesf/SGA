import { IAmostra } from '@/shared/model/amostra.model';

export interface IExame {
  id?: number;
  nome?: string | null;
  tipo?: string | null;
  pesoMaterial?: string | null;
  estimativaVacinas?: string | null;
  resultado?: string | null;
  dataTeste?: Date | null;
  dataLeitura?: Date | null;
  preenchimentoEspelho?: string | null;
  observacoes?: string | null;
  valor?: number | null;
  amostra?: IAmostra | null;
}

export class Exame implements IExame {
  constructor(
    public id?: number,
    public nome?: string | null,
    public tipo?: string | null,
    public pesoMaterial?: string | null,
    public estimativaVacinas?: string | null,
    public resultado?: string | null,
    public dataTeste?: Date | null,
    public dataLeitura?: Date | null,
    public preenchimentoEspelho?: string | null,
    public observacoes?: string | null,
    public valor?: number | null,
    public amostra?: IAmostra | null
  ) {}
}
